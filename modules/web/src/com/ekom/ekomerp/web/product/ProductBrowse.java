package com.ekom.ekomerp.web.product;

import com.ekom.ekomerp.entity.Product;
import com.ekom.ekomerp.entity.ProductPrice;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.web.gui.components.renderers.WebComponentRenderer;

import javax.inject.Inject;
import java.util.Collection;
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
    @Inject
    private ComponentsFactory componentsFactory;

    @Override
    public void init(Map<String, Object> params) {
        deacivatedCheckBox.setValue(false);
        productsDataGrid.getColumn("active").setCollapsed(true);
        activateButton.setVisible(false);
        deactivateButton.setVisible(false);
        deacivatedCheckBox.addValueChangeListener(e -> {
            if (deacivatedCheckBox.getValue() == true) {
                productsDataGrid.setDatasource(productsDs);
                productsDataGrid.getColumn("active").setCollapsed(false);
                if (productsDataGrid.getSingleSelected() != null) {
                    activateButton.setVisible(true);
                    deactivateButton.setVisible(true);
                }

            } else {
                productsDataGrid.setDatasource(activeProductsDs);
                productsDataGrid.getColumn("active").setCollapsed(true);
                activateButton.setVisible(false);
                deactivateButton.setVisible(false);
            }
        });
        productsDataGrid.addSelectionListener(event -> {
            if (deacivatedCheckBox.getValue() == true) {
                if (productsDataGrid.getSingleSelected().getActive() == true) {
                    activateButton.setVisible(false);
                    deactivateButton.setVisible(true);
                } else {
                    activateButton.setVisible(true);
                    deactivateButton.setVisible(false);
                }
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
            activateButton.setVisible(true);
            deactivateButton.setVisible(false);
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
            activateButton.setVisible(false);
            deactivateButton.setVisible(true);
        }
    }

}