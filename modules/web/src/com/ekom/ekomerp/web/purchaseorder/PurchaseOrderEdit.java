package com.ekom.ekomerp.web.purchaseorder;

import com.ekom.ekomerp.entity.*;
import com.ekom.ekomerp.service.InvoiceService;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.bpm.gui.procactions.ProcActionsFrame;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class PurchaseOrderEdit extends AbstractEditor<PurchaseOrder> {

private static final String PROCESS_CODE = "purchase";

    @Inject
    private ProcActionsFrame procActionsFrame;

    @Named("fieldGroup.number")
    private TextField numberField;
    @Inject
    private UniqueNumbersService uniqueNumbersService;
    @Inject
    private LookupPickerField vendorField;
    @Inject
    private CollectionDatasource<PurchaseOrderLine, UUID> purchaseOrderLineDs;
    @Inject
    private Metadata metadata;
    @Inject
    private Table<PurchaseOrderLine> purchaseOrderLineTable;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private CollectionDatasource<Product, UUID> productsDs;
    @Inject
    private TextField amountTaxField;
    @Inject
    private TextField amountUntaxedField;
    @Inject
    private TextField amountTotalField;
    @Inject
    private Label purchaseOrderLabel;
    @Inject
    private Button confirmButton;
    @Inject
    private Button discardButton;
    @Inject
    private InvoiceService invoiceService;
    @Inject
    private FileUploadField invoiceUpload;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private DataSupplier dataSupplier;
    @Inject
    private DateField purchaseOrderDateField;
    @Inject
    private ResizableTextArea notesTextArea;
    @Inject
    private DateField deliveryDateField;
    @Inject
    private LookupPickerField paymentConditionPickerField;
    @Inject
    private DataManager dataManager;

    @Override
    public void init(Map<String, Object> params) {

        vendorField.removeAction(vendorField.getOpenAction());
        invoiceUpload.setUploadButtonCaption(null);
        invoiceUpload.setClearButtonCaption(null);
        invoiceUpload.addFileUploadSucceedListener(event -> {
            FileDescriptor fd = invoiceUpload.getFileDescriptor();
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


        purchaseOrderLineDs.addItemPropertyChangeListener(e -> {
            PurchaseOrderLine item = purchaseOrderLineDs.getItem();
            if(e.getProperty() == "product"){

                    item.setPrice(new BigDecimal("0.0"));
                purchaseOrderLineTable.repaint();
            }

            if((e.getProperty() == "quantity" || e.getProperty() == "price") && purchaseOrderLineDs.getItem().getProduct()!=null){
                item.setSubtotal(calculateSubtotal());
                item.setTax(calculateTax());
                item.setTotal(calculateTotal());
                getItem().setAmountTax(calculateAmountTax());
                getItem().setAmountUntaxed(calculateAmountUntaxed());
                getItem().setAmountWithTax(calculateAmountTotal());
            }
            if((e.getProperty() == "subtotal" ) && purchaseOrderLineDs.getItem().getProduct()!=null){
                item.setPrice(item.getSubtotal().divide(item.getQuantity(),4,RoundingMode.HALF_UP));
                item.setTax(calculateTax());
                item.setTotal(calculateTotal());
                getItem().setAmountTax(calculateAmountTax());
                getItem().setAmountUntaxed(calculateAmountUntaxed());
                getItem().setAmountWithTax(calculateAmountTotal());
            }

        });
        purchaseOrderLineTable.addGeneratedColumn("product", entity -> {
            LookupPickerField productLookUpPickerField = componentsFactory.createComponent(LookupPickerField.class);
            productLookUpPickerField.setOptionsDatasource(productsDs);
            productLookUpPickerField.setDatasource(purchaseOrderLineTable.getItemDatasource(entity),"product");
            productLookUpPickerField.addLookupAction();
            productLookUpPickerField.setWidth("100%");
            return productLookUpPickerField;
        });

        purchaseOrderLineDs.addCollectionChangeListener(e -> {
            if(e.getOperation() == CollectionDatasource.Operation.REMOVE) {
                getItem().setAmountTax(calculateAmountTax());
                getItem().setAmountUntaxed(calculateAmountUntaxed());
                getItem().setAmountWithTax(calculateAmountTotal());
            }
        });
        super.init(params);
    }

    @Override
    protected void postInit() {
        if(getItem().getState()==PurchaseOrderState.purchase){
            purchaseOrderLabel.setValue("Заказ на поставку");
            discardButton.setVisible(true);
            confirmButton.setVisible(false);
            blockOrder(true);
        }else{
            purchaseOrderLabel.setValue("Запрос цен");
            confirmButton.setVisible(true);
            discardButton.setVisible(false);
            blockOrder(false);
        }
        super.postInit();
    }

    @Override
    protected boolean preCommit() {
        deleteEmptyRow();
        setNumberField();
        return super.preCommit();
    }

    private BigDecimal calculateTax() {
        return calculateSubtotal().multiply(new BigDecimal("0.2"));
    }

    private BigDecimal calculateTotal() {
        return calculateSubtotal().add(calculateTax());
    }

    private BigDecimal calculateSubtotal() {
        return (purchaseOrderLineDs.getItem().getPrice().multiply(purchaseOrderLineDs.getItem().getQuantity()));
    }
    private BigDecimal calculateAmountTax() {
        Collection<PurchaseOrderLine> orderLines = purchaseOrderLineDs.getItems();
        BigDecimal amountTax = new BigDecimal("0.0");
        if(orderLines!=null) {
            for (PurchaseOrderLine line : orderLines) {
               amountTax = amountTax.add(line.getTax());
            }
        }
        return amountTax;
    }

    private BigDecimal calculateAmountTotal() {
        return calculateAmountUntaxed().add(calculateAmountTax());
    }

    private BigDecimal calculateAmountUntaxed() {
        Collection<PurchaseOrderLine> orderLines = purchaseOrderLineDs.getItems();
        BigDecimal amountUntaxed = new BigDecimal("0.0");
        if(orderLines!=null) {
            for (PurchaseOrderLine line : orderLines) {
                amountUntaxed = amountUntaxed.add(line.getSubtotal());
            }
        }
        return amountUntaxed;
    }

    public void onCreateButtonClick() {
        Collection<PurchaseOrderLine> orderLines = purchaseOrderLineDs.getItems();
        boolean hasEmptyLine = false;
        if(orderLines!=null) {
            for (PurchaseOrderLine line : orderLines) {
                if (line.getProduct()==null){
                    hasEmptyLine = true;
                    break;
                }
            }
            if(hasEmptyLine==false){
                PurchaseOrderLine line = metadata.create(PurchaseOrderLine.class);
                line.setPurchaseOrder(getItem());
                purchaseOrderLineDs.addItem(line);

            }
        }else {
            PurchaseOrderLine line = metadata.create(PurchaseOrderLine.class);
            line.setPurchaseOrder(getItem());
            purchaseOrderLineDs.addItem(line);
        }
        
    }
    private void setNumberField(){
        if (getItem().getNumber().equals("Новый")){
            String number = "ЗП-";
            long longNumber=getNextValue();
            for (int i = (6-(int)(Math.log10(longNumber)+1)); i>0;i--){
                number+="0";
            }
            number+=longNumber;
            getItem().setNumber(number);
        }
    }
    private void deleteEmptyRow() {
        Collection<PurchaseOrderLine> orderLines = purchaseOrderLineDs.getItems();

        if (orderLines != null) {
            for (PurchaseOrderLine line : orderLines) {
                if (line.getProduct() == null) {
                    purchaseOrderLineDs.removeItem(line);
                }
            }
        }
    }
    private long getNextValue() {
        return uniqueNumbersService.getNextNumber("PurchaseOrderNumber");
    }
    

    public void onConfirmButtonClick() {
        getItem().setState(PurchaseOrderState.purchase);
        purchaseOrderLabel.setValue("Заказ на поставку");
        confirmButton.setVisible(false);
        discardButton.setVisible(true);
        commit();
        invoiceService.createInvoiceFromPurcheseOrder(getItem());
        blockOrder(true);
    }

    public void onDiscardButtonClick() {
        if(findInvoiceByOrigin(getItem()).isEmpty()) {
            getItem().setState(PurchaseOrderState.draft);
            purchaseOrderLabel.setValue("Зарос цен");
            confirmButton.setVisible(true);
            discardButton.setVisible(false);
            commit();
            blockOrder(false);
        }else{
            showNotification("Невозможно отменить заказ. Счет на оплату заказа утвержден.",NotificationType.ERROR);
        }

    }

    public void blockOrder(boolean block){
        vendorField.setEditable(!block);
        purchaseOrderDateField.setEditable(!block);
        purchaseOrderLineTable.setEditable(!block);
        notesTextArea.setEditable(!block);
        deliveryDateField.setEditable(!block);
        paymentConditionPickerField.setEditable(!block);
        invoiceUpload.setEditable(!block);
        purchaseOrderLineTable.getButtonsPanel().setEnabled(!block);
        if(block){
        purchaseOrderLineTable.removeGeneratedColumn("product");
        }else{
            purchaseOrderLineTable.addGeneratedColumn("product", entity -> {
                LookupPickerField productLookUpPickerField = componentsFactory.createComponent(LookupPickerField.class);
                productLookUpPickerField.setOptionsDatasource(productsDs);
                productLookUpPickerField.setDatasource(purchaseOrderLineTable.getItemDatasource(entity),"product");
                productLookUpPickerField.addLookupAction();
                productLookUpPickerField.setWidth("100%");
                return productLookUpPickerField;
            });
        }
    }

    public List<Invoice> findInvoiceByOrigin(PurchaseOrder purchaseOrder){
        LoadContext loadContext = LoadContext.create(Invoice.class).setQuery(LoadContext
                .createQuery("select i from ekomerp$Invoice i where i.origin = :purchaseOrderNumber and i.state <> :state ")
                .setParameter("purchaseOrderNumber",purchaseOrder.getNumber())
                .setParameter("state",InvoiceStateEnum.open))
                .setView("invoice-view");

        return dataManager.loadList(loadContext);
    }
}