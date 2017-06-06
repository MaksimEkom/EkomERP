package com.ekom.ekomerp.web.product;

import com.ekom.ekomerp.entity.Product;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class ProductList extends AbstractWindow {

    @Inject
    private CollectionDatasource<Product, UUID> productsDs;
    @Inject
    private LookupPickerField productLookupPickerField;
    @Inject
    private TextField codeTextField;

    @Override
    public void init(Map<String, Object> params) {
        productLookupPickerField.addValueChangeListener(e -> {
            codeTextField.setValue(productsDs.getItem().getCode());
        });
        productsDs.refresh();
    }
    public void select(Component source) {
        close(Window.COMMIT_ACTION_ID);
    }

    public void cancel(Component source) {
        close(Window.CLOSE_ACTION_ID);
    }

    public Product getSelectedProduct() {
        return productsDs.getItem();
    }

}