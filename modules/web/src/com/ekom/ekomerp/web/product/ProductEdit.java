package com.ekom.ekomerp.web.product;

import com.ekom.ekomerp.entity.Consumption;
import com.ekom.ekomerp.entity.Laboriousness;
import com.ekom.ekomerp.entity.StockMovementLine;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.ekom.ekomerp.entity.Product;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.export.FileDataProvider;
import com.haulmont.cuba.gui.export.ResourceDataProvider;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


public class ProductEdit extends AbstractEditor<Product> {


    @Inject
    private CollectionDatasource<Laboriousness, UUID> laboriousnessDs;
    @Inject
    private Label label1;
    @Inject
    private Embedded productImage;
    @Inject
    private FileUploadField productImageUpload;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private DataSupplier dataSupplier;
    @Inject
    private Table<StockMovementLine> stockMovementLinesTable;
    @Inject
    private CollectionDatasource<StockMovementLine, UUID> stockMovementLinesDs;
//    @Named("stockMovementLinesTable.edit")
//    private EditAction stockMovementLinesTableEdit;

    public static final String DEFAULT_PRODUCT_IMAGE_PATH = "com/ekom/ekomerp/web/product/default-photo.jpg";
    public static final String DEFAULT_PRODUCT_IMAGE_NAME = "default-photo.jpg";

    public void init(Map<String, Object> params) {
        laboriousnessDs.addCollectionChangeListener(e -> calculateTotalLaboriousness());
//        stockMovementLinesTableEdit.setWindowId("ekomerp$StockMovement.edit");
        productImageUpload.setUploadButtonCaption(null);
        productImageUpload.setClearButtonCaption(null);
        productImageUpload.addFileUploadSucceedListener(event -> {
            FileDescriptor fd = productImageUpload.getFileDescriptor();
            try {
                fileUploadingAPI.putFileIntoStorage(productImageUpload.getFileId(), fd);
            } catch (FileStorageException e) {
                throw new RuntimeException("Error saving file to FileStorage", e);
            }

            FileDescriptor committedImage = dataSupplier.commit(fd);
            getItem().setImage(committedImage);

            FileDataProvider dataProvider = new FileDataProvider(committedImage);
            productImage.setSource(committedImage.getId() + "." + committedImage.getExtension(), dataProvider);

            showNotification(formatMessage(getMessage("uploadSuccessMessage"), productImageUpload.getFileName()),
                    NotificationType.HUMANIZED);
        });

        productImageUpload.addAfterValueClearListener(event -> {
            ResourceDataProvider dataProvider = new ResourceDataProvider(DEFAULT_PRODUCT_IMAGE_PATH);
            productImage.setSource(DEFAULT_PRODUCT_IMAGE_NAME, dataProvider);
            getItem().setImage(null);

        });
        super.init(params);
    }
    @Override
    protected void postInit() {
        super.postInit();

        FileDescriptor productImageFile = getItem().getImage();

        if (productImageFile == null) {
            ResourceDataProvider dataProvider = new ResourceDataProvider(DEFAULT_PRODUCT_IMAGE_PATH);
            productImage.setSource(DEFAULT_PRODUCT_IMAGE_NAME, dataProvider);
        } else {
            FileDataProvider dataProvider = new FileDataProvider(productImageFile);
            productImage.setSource(productImageFile.getId() + "." + productImageFile.getExtension(), dataProvider);
        }
    }
    private void calculateTotalLaboriousness() {
        double totalLaboriousness = 0;
        for (Laboriousness lab : laboriousnessDs.getItems()){
            totalLaboriousness += lab.getValue();
        }
        label1.setValue(totalLaboriousness);
    }

    public void onCopyButtonClick() {
        // Open the products list screen
        ProductList window = (ProductList) openWindow("product-list", WindowManager.OpenType.DIALOG);
        // Add a listener that will be notified when the screen is closed by action with Window.COMMIT_ACTION_ID
        window.addCloseWithCommitListener(() -> {
            // Get a selected entity from the invoked screen and use it
            Set<Consumption> consumptionSet = window.getSelectedProduct().getConsumption();
            getItem().setConsumption(consumptionSet);
        });
    }
}