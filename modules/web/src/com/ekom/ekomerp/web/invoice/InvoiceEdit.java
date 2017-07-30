package com.ekom.ekomerp.web.invoice;

import com.ekom.ekomerp.entity.*;
import com.ekom.ekomerp.web.payment.PaymentEdit;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class InvoiceEdit extends AbstractEditor<Invoice> {
    @Inject
    private CollectionDatasource<InvoiceLine, UUID> invoiceLineDs;
    @Inject
    private Metadata metadata;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private CollectionDatasource<Product, UUID> productsDs;
    @Inject
    private Table<InvoiceLine> invoiceLineTable;
    @Inject
    private UniqueNumbersService uniqueNumbersService;
    @Inject
    private FileUploadField invoiceUpload;
    @Inject
    private DataSupplier dataSupplier;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private Label invoiceLabel;
    @Inject
    private Button approveButton;
    @Inject
    private Button discardButton;
    @Inject
    private TextField originField;
    @Inject
    private DateField invoiceDateField;
    @Inject
    private DateField dueDateField;
    @Inject
    private LookupPickerField partnerPickerField;
    @Inject
    private ResizableTextArea notesTextArea;
    @Inject
    private CollectionDatasource<Payment, UUID> paymentsDs;

    @Override
    public void init(Map<String, Object> params) {

        invoiceUpload.addFileUploadSucceedListener(event -> {
            FileDescriptor fd = invoiceUpload.getFileDescriptor();

            invoiceUpload.setUploadButtonCaption(null);
            invoiceUpload.setClearButtonCaption(null);
            try {
                // save file to FileStorage
                fileUploadingAPI.putFileIntoStorage(invoiceUpload.getFileId(), fd);
            } catch (FileStorageException e) {
                throw new RuntimeException("Ошибка сохранения файла в хранилище", e);
            }
            // save file descriptor to database

            FileDescriptor commitedFile =  dataSupplier.commit(fd);
            getItem().setInvoiceFile(commitedFile);
            showNotification("Загруженный файл: " + invoiceUpload.getFileName(), NotificationType.HUMANIZED);
        });

        invoiceUpload.addFileUploadErrorListener(event ->
                showNotification("Ошибка загрузки файла", NotificationType.HUMANIZED));

        invoiceLineTable.addGeneratedColumn("product", entity -> {
            LookupPickerField productLookUpPickerField = componentsFactory.createComponent(LookupPickerField.class);
            productLookUpPickerField.setOptionsDatasource(productsDs);
            productLookUpPickerField.setDatasource(invoiceLineTable.getItemDatasource(entity),"product");
            productLookUpPickerField.addLookupAction();
            productLookUpPickerField.setWidth("100%");
            return productLookUpPickerField;
        });

        invoiceLineDs.addItemPropertyChangeListener(e -> {
            InvoiceLine item = invoiceLineDs.getItem();
            if(e.getProperty() == "product"){

                    item.setPrice(new BigDecimal("0.0"));
                invoiceLineTable.repaint();
            }

            if((e.getProperty() == "quantity" || e.getProperty() == "price") && invoiceLineDs.getItem().getProduct()!=null){
                item.setSubtotal(calculateSubtotal());
                item.setTax(calculateTax());
                item.setTotal(calculateTotal());
                getItem().setAmountTax(calculateAmountTax());
                getItem().setAmountUntaxed(calculateAmountUntaxed());
                getItem().setAmountTotal(calculateAmountTotal());
            }
        });
        invoiceLineDs.addCollectionChangeListener(e -> {
            if(e.getOperation() == CollectionDatasource.Operation.REMOVE) {
                getItem().setAmountTax(calculateAmountTax());
                getItem().setAmountUntaxed(calculateAmountUntaxed());
                getItem().setAmountTotal(calculateAmountTotal());
            }
        });
        super.init(params);
    }

    @Override
    protected void postInit() {
        if(getItem().getState()==InvoiceStateEnum.approved){
            discardButton.setVisible(true);
            approveButton.setVisible(false);

        }else{
            approveButton.setVisible(true);
            discardButton.setVisible(false);

        }
        super.postInit();
    }

    public void onCreateButtonClick() {
        Collection<InvoiceLine> invoiceLines = invoiceLineDs.getItems();
        boolean hasEmptyLine = false;
        if(invoiceLines!=null) {
            for (InvoiceLine line : invoiceLines) {
                if (line.getProduct()==null){
                    hasEmptyLine = true;
                    break;
                }
            }
            if(hasEmptyLine==false){
                InvoiceLine line = metadata.create(InvoiceLine.class);
                line.setInvoice(getItem());
                invoiceLineDs.addItem(line);

            }
        }else {
            InvoiceLine line = metadata.create(InvoiceLine.class);
            line.setInvoice(getItem());
            invoiceLineDs.addItem(line);
        }
    }
    private BigDecimal calculateTax() {
        return calculateSubtotal().multiply(new BigDecimal("0.2"));
    }

    private BigDecimal calculateTotal() {
        return calculateSubtotal().add(calculateTax());
    }

    private BigDecimal calculateSubtotal() {
        return (invoiceLineDs.getItem().getPrice().multiply(invoiceLineDs.getItem().getQuantity()));
    }
    private BigDecimal calculateAmountTax() {
        Collection<InvoiceLine> invoiceLines = invoiceLineDs.getItems();
        BigDecimal amountTax = new BigDecimal("0.0");
        if(invoiceLines!=null) {
            for (InvoiceLine line : invoiceLines) {
                amountTax = amountTax.add(line.getTax());
            }
        }
        return amountTax;
    }

    private BigDecimal calculateAmountTotal() {
        return calculateAmountUntaxed().add(calculateAmountTax());
    }

    private  BigDecimal calculateAmountUntaxed() {
        Collection<InvoiceLine> orderLines = invoiceLineDs.getItems();
        BigDecimal amountUntaxed = new  BigDecimal("0.0");
        if(orderLines!=null) {
            for (InvoiceLine line : orderLines) {
                amountUntaxed= amountUntaxed.add(line.getSubtotal());
            }
        }
        return amountUntaxed;
    }

    @Override
    protected boolean preCommit() {
        deleteEmptyRow();
        setNumberField();
        return super.preCommit();
    }
    private long getNextValue() {
        return uniqueNumbersService.getNextNumber("InvoiceNumber");
    }

    private void setNumberField(){
        if (getItem().getNumber().equals("Новый")){
            String number = "СЧ-";
            long longNumber=getNextValue();
            for (int i = (6-(int)(Math.log10(longNumber)+1)); i>0;i--){
                number+="0";
            }
            number+=longNumber;
            getItem().setNumber(number);
        }
    }

    private void deleteEmptyRow() {
        Collection<InvoiceLine> orderLines = invoiceLineDs.getItems();

        if (orderLines != null) {
            for (InvoiceLine line : orderLines) {
                if (line.getProduct() == null) {
                    invoiceLineDs.removeItem(line);
                }
            }
        }
    }
    
    public void onApproveButtonClick() {
        getItem().setState(InvoiceStateEnum.approved);
        approveButton.setVisible(false);
        discardButton.setVisible(true);
        blockInvoice(true);
        commit();
    }

    public void onDiscardButtonClick() {
        getItem().setState(InvoiceStateEnum.open);
        approveButton.setVisible(true);
        discardButton.setVisible(false);
        blockInvoice(false);
        commit();
    }
    public void blockInvoice(boolean block) {
        partnerPickerField.setEditable(!block);
        dueDateField.setEditable(!block);
        invoiceDateField.setEditable(!block);
        invoiceUpload.setEditable(!block);
        notesTextArea.setEditable(!block);
        originField.setEditable(!block);
        invoiceLineTable.getButtonsPanel().setEnabled(!block);
        if (block) {
            invoiceLineTable.removeGeneratedColumn("product");
        } else {
            invoiceLineTable.addGeneratedColumn("product", entity -> {
                LookupPickerField productLookUpPickerField = componentsFactory.createComponent(LookupPickerField.class);
                productLookUpPickerField.setOptionsDatasource(productsDs);
                productLookUpPickerField.setDatasource(invoiceLineTable.getItemDatasource(entity), "product");
                productLookUpPickerField.addLookupAction();
                productLookUpPickerField.setWidth("100%");
                return productLookUpPickerField;
            });
        }
    }

}