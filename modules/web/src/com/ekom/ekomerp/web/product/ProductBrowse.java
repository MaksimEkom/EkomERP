package com.ekom.ekomerp.web.product;

import com.ekom.ekomerp.entity.Product;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.Datasource;
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
    @Inject
    private DataGrid<Product> productsDataGrid;
    @Inject
    private CheckBox deacivatedCheckBox;
    @Inject
    private GroupDatasource<Product, UUID> activeProductsDs;

    @Override
    public void init(Map<String, Object> params) {
        deacivatedCheckBox.setValue(false);
        productsDataGrid.getColumn("active").setCollapsed(true);
        activateButton.setVisible(false);
        deactivateButton.setVisible(false);
        deacivatedCheckBox.addValueChangeListener(e -> {
            if (deacivatedCheckBox.getValue()==true){
                productsDataGrid.setDatasource(productsDs);
                productsDataGrid.getColumn("active").setCollapsed(false);
                activateButton.setVisible(true);
                deactivateButton.setVisible(true);
            }else{
                productsDataGrid.setDatasource(activeProductsDs);
                productsDataGrid.getColumn("active").setCollapsed(true);
                activateButton.setVisible(false);
                deactivateButton.setVisible(false);
            }
        });
    }

    public void onDeactivateButtonClick() {

        Product product = productsDataGrid.getSingleSelected();
        if (product == null){
            showNotification("Выберите изделие для деактивации.", NotificationType.HUMANIZED);
            return;
        }else {
            product.setActive(false);
            dataManager.commit(product);
            productsDs.refresh();
        }
    }

    public void onActivateButtonClick() {

        Product product = productsDataGrid.getSingleSelected();
        if (product == null){
            showNotification("Выберите изделие для активации.", NotificationType.HUMANIZED);
            return;
        }else {
            product.setActive(true);
            dataManager.commit(product);
            productsDs.refresh();
        }
    }

}