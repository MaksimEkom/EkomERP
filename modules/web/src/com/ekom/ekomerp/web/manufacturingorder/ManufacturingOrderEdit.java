package com.ekom.ekomerp.web.manufacturingorder;

import com.ekom.ekomerp.entity.ManufacturingOrderLine;
import com.ekom.ekomerp.entity.Product;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.ekom.ekomerp.entity.ManufacturingOrder;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class ManufacturingOrderEdit extends AbstractEditor<ManufacturingOrder> {
    @Inject
    private Table<ManufacturingOrderLine> manufacturingOrderLineTable;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private CollectionDatasource<Product, UUID> productsDs;
    @Inject
    private CollectionDatasource<ManufacturingOrderLine, UUID> manufacturingOrderLineDs;
    @Inject
    private Metadata metadata;

    @Override
    public void init(Map<String, Object> params) {
        manufacturingOrderLineTable.addGeneratedColumn("product", entity -> {
            LookupPickerField productLookUpPickerField = componentsFactory.createComponent(LookupPickerField.class);
            productLookUpPickerField.setOptionsDatasource(productsDs);
            productLookUpPickerField.setDatasource(manufacturingOrderLineTable.getItemDatasource(entity),"product");
            productLookUpPickerField.addLookupAction();
            productLookUpPickerField.setWidth("100%");
            return productLookUpPickerField;
        });

        manufacturingOrderLineDs.addItemPropertyChangeListener(e -> {
            ManufacturingOrderLine item = manufacturingOrderLineDs.getItem();
            if(e.getProperty() == "product"){
                if (item.getQuantity()==null) {
                    item.setQuantity(0.0);
                }
                if (item.getQuantityProduced()==null) {
                    item.setQuantityProduced(0.0);
                }
            }
            manufacturingOrderLineTable.repaint();
        });
        super.init(params);
    }

    @Override
    protected boolean preCommit() {
        deleteEmptyRow();
        return super.preCommit();
    }

    public void onCreateButtonClick() {
        Collection<ManufacturingOrderLine> orderLines = manufacturingOrderLineDs.getItems();
        boolean hasEmptyLine = false;
        if(orderLines!=null) {
            for (ManufacturingOrderLine line : orderLines) {
                if (line.getProduct()==null){
                    hasEmptyLine = true;
                    break;
                }
            }
            if(hasEmptyLine==false){
                ManufacturingOrderLine line = metadata.create(ManufacturingOrderLine.class);
                line.setManufacturingOrder(getItem());
                manufacturingOrderLineDs.addItem(line);

            }
        }else {
            ManufacturingOrderLine line = metadata.create(ManufacturingOrderLine.class);
            line.setManufacturingOrder(getItem());
            manufacturingOrderLineDs.addItem(line);

        }
    }
    private void deleteEmptyRow() {
        Collection<ManufacturingOrderLine> orderLines = manufacturingOrderLineDs.getItems();

        if (orderLines != null) {
            for (ManufacturingOrderLine line : orderLines) {
                if (line.getProduct() == null) {
                    manufacturingOrderLineDs.removeItem(line);
                }
            }
        }
    }
}