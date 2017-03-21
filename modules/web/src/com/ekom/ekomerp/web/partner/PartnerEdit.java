package com.ekom.ekomerp.web.partner;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.components.*;
import com.ekom.ekomerp.entity.Partner;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.export.FileDataProvider;
import com.haulmont.cuba.gui.export.ResourceDataProvider;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

public class PartnerEdit extends AbstractEditor<Partner> {
    public static final String DEFAULT_PRODUCT_IMAGE_PATH = "com/ekom/ekomerp/web/default-photo.jpg";
    public static final String DEFAULT_PRODUCT_IMAGE_NAME = "default-photo.jpg";

    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private DataSupplier dataSupplier;
    @Inject
    private Embedded partnerImage;
    @Inject
    private FileUploadField partnerImageUpload;
    @Inject
    private LookupPickerField parentPickerField;
    @Inject
    private OptionsGroup partnerTypeGroup;
    @Inject
    private Datasource<Partner> partnerDs;
    @Named("partnerFieldGroup.position")
    private TextField positionField;
    @Inject
    private LookupPickerField regionPickerField;
    @Inject
    private LookupPickerField countryPickerField;

    @Override
    public void init(Map<String, Object> params) {
        regionPickerField.removeAction(regionPickerField.getOpenAction());
        countryPickerField.removeAction(countryPickerField.getOpenAction());
        partnerTypeGroup.addValueChangeListener(e -> {
            if(e.getValue().toString() == "company"){
                parentPickerField.setVisible(false);
                positionField.setVisible(false);
            }else if(e.getValue().toString() == "individual"){
                parentPickerField.setVisible(true);
                positionField.setVisible(true);
            }

        });
        parentPickerField.removeAction(parentPickerField.getOpenAction());

        partnerImageUpload.addFileUploadSucceedListener(event -> {
            FileDescriptor fd = partnerImageUpload.getFileDescriptor();
            try {
                fileUploadingAPI.putFileIntoStorage(partnerImageUpload.getFileId(), fd);
            } catch (FileStorageException e) {
                throw new RuntimeException("Error saving file to FileStorage", e);
            }

            FileDescriptor committedImage = dataSupplier.commit(fd);
            getItem().setImage(committedImage);

            FileDataProvider dataProvider = new FileDataProvider(committedImage);
            partnerImage.setSource(committedImage.getId() + "." + committedImage.getExtension(), dataProvider);

            showNotification(formatMessage(getMessage("uploadSuccessMessage"), partnerImageUpload.getFileName()),
                    NotificationType.HUMANIZED);
        });


        super.init(params);
    }

    protected void postInit() {
        super.postInit();

        FileDescriptor productImageFile = getItem().getImage();

        if (productImageFile == null) {
            ResourceDataProvider dataProvider = new ResourceDataProvider(DEFAULT_PRODUCT_IMAGE_PATH);
            partnerImage.setSource(DEFAULT_PRODUCT_IMAGE_NAME, dataProvider);
        } else {
            FileDataProvider dataProvider = new FileDataProvider(productImageFile);
            partnerImage.setSource(productImageFile.getId() + "." + productImageFile.getExtension(), dataProvider);
        }
    }
}