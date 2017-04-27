package com.ekom.ekomerp.web.invoice;

import com.ekom.ekomerp.entity.InvoiceLine;
import com.ekom.ekomerp.entity.Product;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.ekom.ekomerp.entity.Invoice;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
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

    @Override
    public void init(Map<String, Object> params) {
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
                if (item.getProduct().getPurchasePrice()!=null) {
                    item.setPrice(item.getProduct().getPurchasePrice());
                }else{
                    item.setPrice(0.0);
                }
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
    private Double calculateTax() {
        return calculateSubtotal()*0.2;
    }

    private Double calculateTotal() {
        return calculateSubtotal()+calculateTax();
    }

    private Double calculateSubtotal() {
        return (invoiceLineDs.getItem().getPrice()*invoiceLineDs.getItem().getQuantity());
    }
    private Double calculateAmountTax() {
        Collection<InvoiceLine> invoiceLines = invoiceLineDs.getItems();
        double amountTax = 0.0;
        if(invoiceLines!=null) {
            for (InvoiceLine line : invoiceLines) {
                amountTax+=line.getTax();
            }
        }
        return amountTax;
    }

    private Double calculateAmountTotal() {
        return calculateAmountUntaxed()+calculateAmountTax();
    }

    private Double calculateAmountUntaxed() {
        Collection<InvoiceLine> orderLines = invoiceLineDs.getItems();
        double amountUntaxed = 0.0;
        if(orderLines!=null) {
            for (InvoiceLine line : orderLines) {
                amountUntaxed+=line.getSubtotal();
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
}