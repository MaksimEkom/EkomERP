package com.ekom.ekomerp.web.purchaseorder;

import com.ekom.ekomerp.entity.Product;
import com.ekom.ekomerp.entity.PurchaseOrderLine;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.ekom.ekomerp.entity.PurchaseOrder;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.UUID;

public class PurchaseOrderEdit extends AbstractEditor<PurchaseOrder> {

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

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        vendorField.removeAction(vendorField.getOpenAction());

        purchaseOrderLineDs.addItemPropertyChangeListener(e -> {
            PurchaseOrderLine item = purchaseOrderLineDs.getItem();
            if(e.getProperty() == "product"){
                if (item.getProduct().getPurchasePrice()!=null) {
                    item.setPrice(item.getProduct().getPurchasePrice());
                }else{
                    item.setPrice(1.0);
                }
            }

            if((e.getProperty() == "quantity" || e.getProperty() == "price") && purchaseOrderLineDs.getItem().getProduct()!=null){
                item.setSubtotal(calculateSubtotal());
                item.setTax(calculateTax());
                item.setTotal(calculateTotal());
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
    }

    @Override
    protected boolean preCommit() {
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


    public void onCreateButtonClick() {
        purchaseOrderLineDs.addItem(metadata.create(PurchaseOrderLine.class));
        
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
    private long getNextValue() {
        return uniqueNumbersService.getNextNumber("PurchaseOrderNumber");
    }
}