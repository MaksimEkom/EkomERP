package com.ekom.ekomerp.web.product;

import com.ekom.ekomerp.entity.Laboriousness;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.ekom.ekomerp.entity.Product;
import com.haulmont.cuba.gui.components.Embedded;
import com.haulmont.cuba.gui.components.FileUploadField;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.export.FileDataProvider;
import com.haulmont.cuba.gui.export.ResourceDataProvider;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.data.Datasource;

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

    public static final String DEFAULT_PRODUCT_IMAGE_PATH = "com/ekom/ekomerp/web/product/default-photo.jpg";
    public static final String DEFAULT_PRODUCT_IMAGE_NAME = "default-photo.jpg";

    public void init(Map<String, Object> params) {
        laboriousnessDs.addCollectionChangeListener(e -> calculateTotalLaboriousness());



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


    public Component generateUnitField(Datasource datasource, String fieldId) {
		return null;
    }
}