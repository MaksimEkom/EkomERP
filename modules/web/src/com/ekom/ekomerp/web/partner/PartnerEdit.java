package com.ekom.ekomerp.web.partner;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.ekom.ekomerp.entity.Partner;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
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
    @Inject
    private Button createContactAdressButton;
    @Named("partnerFilteredByParentTable.create")
    private CreateAction partnerFilteredByParentTableCreate;
    @Named("partnerFilteredByParentTable.edit")
    private EditAction partnerFilteredByParentTableEdit;
    @Inject
    private Table<Partner> partnerFilteredByParentTable;
    @Inject
    private TextField unpField;
    @Inject
    private TextField fullNameField;

    @Override
    public void init(Map<String, Object> params) {
        regionPickerField.removeAction(regionPickerField.getOpenAction());
        countryPickerField.removeAction(countryPickerField.getOpenAction());
        partnerFilteredByParentTableCreate.setWindowId("ekomerp$PartnerContact.edit");
        partnerFilteredByParentTableCreate.setOpenType(WindowManager.OpenType.DIALOG);
        partnerFilteredByParentTableEdit.setWindowId("ekomerp$PartnerContact.edit");
        partnerFilteredByParentTableEdit.setOpenType(WindowManager.OpenType.DIALOG);
        partnerTypeGroup.addValueChangeListener(e -> {
            if(e.getValue().toString() == "company"){
                parentPickerField.setVisible(false);
                positionField.setVisible(false);
                unpField.setVisible(true);
                fullNameField.setInputPrompt("Полное наименование");
            }else if(e.getValue().toString() == "individual"){
                parentPickerField.setVisible(true);
                positionField.setVisible(true);
                unpField.setVisible(false);
                fullNameField.setInputPrompt("ФИО");
            }

        });
        parentPickerField.removeAction(parentPickerField.getOpenAction());
        partnerImageUpload.setUploadButtonCaption(null);
        partnerImageUpload.setClearButtonCaption(null);
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
        partnerImageUpload.addAfterValueClearListener(event -> {
            ResourceDataProvider dataProvider = new ResourceDataProvider(DEFAULT_PRODUCT_IMAGE_PATH);
            partnerImage.setSource(DEFAULT_PRODUCT_IMAGE_NAME, dataProvider);
            getItem().setImage(null);

        });

        super.init(params);
    }

    protected void postInit() {

        Partner partner = getItem();

        partnerFilteredByParentTableCreate.setEnabled(!PersistenceHelper.isNew(partner));
        partnerFilteredByParentTableCreate.setInitialValues(ParamsMap.of("parent",partner));
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