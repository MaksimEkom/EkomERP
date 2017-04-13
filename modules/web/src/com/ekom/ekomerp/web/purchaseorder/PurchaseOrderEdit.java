package com.ekom.ekomerp.web.purchaseorder;

import com.ekom.ekomerp.entity.Product;
import com.ekom.ekomerp.entity.PurchaseOrderLine;
import com.ekom.ekomerp.entity.PurchaseOrderState;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.*;
import com.ekom.ekomerp.entity.PurchaseOrder;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.bpm.gui.procactions.ProcActionsFrame;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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

    @Override
    public void init(Map<String, Object> params) {

        vendorField.removeAction(vendorField.getOpenAction());


        purchaseOrderLineDs.addItemPropertyChangeListener(e -> {
            PurchaseOrderLine item = purchaseOrderLineDs.getItem();
            if(e.getProperty() == "product"){
                if (item.getProduct().getPurchasePrice()!=null) {
                    item.setPrice(item.getProduct().getPurchasePrice());
                }else{
                    item.setPrice(0.0);
                }
            }

            if((e.getProperty() == "quantity" || e.getProperty() == "price") && purchaseOrderLineDs.getItem().getProduct()!=null){
                item.setSubtotal(calculateSubtotal());
                item.setTax(calculateTax());
                item.setTotal(calculateTotal());
                getItem().setAmountTax(calculateAmountTax());
                getItem().setAmountUntaxed(calculateAmountSubtotal());
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
                getItem().setAmountUntaxed(calculateAmountSubtotal());
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
        }else{
            purchaseOrderLabel.setValue("Запрос цен");
            confirmButton.setVisible(true);
            discardButton.setVisible(false);
        }
        super.postInit();
    }

    @Override
    protected boolean preCommit() {
        deleteEmptyRow();
        setNumberField();
        return super.preCommit();
    }

    private Double calculateTax() {
        return calculateSubtotal()*0.2;
    }

    private Double calculateTotal() {
        return calculateSubtotal()+calculateTax();
    }

    private Double calculateSubtotal() {
        return (purchaseOrderLineDs.getItem().getPrice()*purchaseOrderLineDs.getItem().getQuantity());
    }
    private Double calculateAmountTax() {
        Collection<PurchaseOrderLine> orderLines = purchaseOrderLineDs.getItems();
        double amountTax = 0.0;
        if(orderLines!=null) {
            for (PurchaseOrderLine line : orderLines) {
               amountTax+=line.getTax();
            }
        }
        return amountTax;
    }

    private Double calculateAmountTotal() {
        return calculateAmountSubtotal()+calculateAmountTax();
    }

    private Double calculateAmountSubtotal() {
        Collection<PurchaseOrderLine> orderLines = purchaseOrderLineDs.getItems();
        double amountSubtotal = 0.0;
        if(orderLines!=null) {
            for (PurchaseOrderLine line : orderLines) {
                amountSubtotal+=line.getSubtotal();
            }
        }
        return amountSubtotal;
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
    }

    public void onDiscardButtonClick() {
        getItem().setState(PurchaseOrderState.draft);
        purchaseOrderLabel.setValue("Заказ на поставку");
        confirmButton.setVisible(true);
        discardButton.setVisible(false);
        commit();
    }
}