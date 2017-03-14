package com.ekom.ekomerp.web.product;

import com.ekom.ekomerp.entity.Product;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.ButtonsPanel;
import com.haulmont.cuba.gui.components.Filter;
import com.haulmont.cuba.gui.data.GroupDatasource;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductBrowse extends AbstractLookup {

    @Inject
    private GroupDatasource<Product, UUID> productsDs;

    @Inject
    private DataManager dataManager;
    @Inject
    private Button deactivateButton;
    @Inject
    private Button activateButton;
    @Inject
    private ButtonsPanel buttonsPanel;
    @Inject
    private Filter productFilter;

    @Override
    public void init(Map<String, Object> params) {

    }

    public void onDeactivateButtonClick() {
        Product pr = productsDs.getItem();
        UUID id = pr.getId();
        LoadContext<Product> loadContext = LoadContext.create(Product.class)
                .setQuery(LoadContext.createQuery("select p from ekomerp$Product p where p.id = :id")
                        .setParameter("id", id))
                .setView(View.LOCAL);
        List<Product> products = dataManager.loadList(loadContext);
        if (pr == null){
            showNotification("Выберите изделие для деактивации.", NotificationType.HUMANIZED);
            return;
        }
        Product product = products.get(0);
        product.setActive(false);
        dataManager.commit(product);
        productsDs.refresh();



    }

    public void onActivateButtonClick() {

        Product pr = productsDs.getItem();
        UUID id = pr.getId();
        LoadContext<Product> loadContext = LoadContext.create(Product.class)
                .setQuery(LoadContext.createQuery("select p from ekomerp$Product p where p.id = :id")
                        .setParameter("id", id))
                .setView(View.LOCAL);
        List<Product> products = dataManager.loadList(loadContext);
        if (pr == null){
            showNotification("Выберите изделие для активации.", NotificationType.HUMANIZED);
            return;
        }
        Product product = products.get(0);
        product.setActive(true);
        dataManager.commit(product);
        productsDs.refresh();
    }
}