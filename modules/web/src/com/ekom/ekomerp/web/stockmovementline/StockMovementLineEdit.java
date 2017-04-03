package com.ekom.ekomerp.web.stockmovementline;

import com.ekom.ekomerp.entity.Inventory;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.ekom.ekomerp.entity.StockMovementLine;
import com.haulmont.cuba.gui.components.FieldGroup;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.UUID;

public class StockMovementLineEdit extends AbstractEditor<StockMovementLine> {
    @Inject
    private TextField quantityTextField;
    @Inject
    private CollectionDatasource<Inventory, UUID> inventoriesDs;
    @Named("fieldGroup.product")
    private LookupPickerField productField;

    @Override
    public void init(Map<String, Object> params) {
        productField.addValueChangeListener(e -> {
            if (inventoriesDs.getItems() != null){
                quantityTextField.setValue(inventoriesDs.getItem().getQuantity());
            }else quantityTextField.setValue(0);
    });
        super.init(params);
    }
}