package com.ekom.ekomerp.web.product;

import com.ekom.ekomerp.entity.Product;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class Copyfromanalog extends AbstractWindow {
    @Inject
    private CollectionDatasource<Product, UUID> productsDs;
    @Inject
    private TextField codeTextField;
    @Inject
    private LookupPickerField productLookupPickerField;

    @Override
    public void init(Map<String, Object> params) {
        productLookupPickerField.removeAction(productLookupPickerField.getOpenAction());
        productLookupPickerField.addValueChangeListener(e -> {
            codeTextField.setValue(productsDs.getItem().getCode());
        });
        super.init(params);
    }
}