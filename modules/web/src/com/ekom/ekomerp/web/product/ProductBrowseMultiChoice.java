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

public class ProductBrowseMultiChoice extends AbstractLookup {

    @Inject
    private GroupDatasource<Product, UUID> productsDs;

    @Inject
    private DataManager dataManager;
    @Inject
    private ButtonsPanel buttonsPanel;
    @Inject
    private Filter productFilter;
    @Inject
    private DataGrid<Product> productsDataGrid;

    @Inject
    private GroupDatasource<Product, UUID> activeProductsDs;
    @Inject
    private ComponentsFactory componentsFactory;


    
    @Override
    public void init(Map<String, Object> params) {

    }
}