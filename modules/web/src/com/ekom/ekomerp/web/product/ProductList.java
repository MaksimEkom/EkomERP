package com.ekom.ekomerp.web.product;

import com.ekom.ekomerp.entity.Product;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class ProductList extends AbstractWindow {

    @Inject
    private CollectionDatasource<Product, UUID> productsDs;

    @Override
    public void init(Map<String, Object> params) {
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