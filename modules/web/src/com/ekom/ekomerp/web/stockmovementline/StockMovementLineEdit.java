package com.ekom.ekomerp.web.stockmovementline;

import com.ekom.ekomerp.entity.Inventory;
import com.ekom.ekomerp.entity.Location;
import com.ekom.ekomerp.entity.Product;
import com.ekom.ekomerp.service.InventoryService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.ekom.ekomerp.entity.StockMovementLine;
import com.haulmont.cuba.gui.components.FieldGroup;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.LookupPickerField;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

public class StockMovementLineEdit extends AbstractEditor<StockMovementLine> {
    @Inject
    private Label quantityInStockLabel;
    @Named("fieldGroup.product")
    private LookupPickerField productField;
    @Inject
    private InventoryService inventoryService;
    @Inject
    private DataManager dataManager;

    @Override
    public void init(Map<String, Object> params) {
        productField.addValueChangeListener(e -> {
            if (getItem().getStockMovement().getLocation()!=null) {
                List<Inventory> inventories = findInventoryByProductAndLocation(productField.getValue(), getItem().getStockMovement().getLocation());
                if (productField.getValue() != null) {
                    if (inventories.size() != 0) {
                        quantityInStockLabel.setValue(inventories.get(0).getQuantity());
                    }else {
                        quantityInStockLabel.setValue("0");
                    }
                }else {
                    quantityInStockLabel.setValue("0");
                }
            }else{
                quantityInStockLabel.setValue("Выберите место хранения!");
            }
        });
        super.init(params);
    }

    private List<Inventory> findInventoryByProductAndLocation(Product product, Location location){
        LoadContext loadContext = LoadContext.create(Inventory.class).setQuery(LoadContext
                .createQuery("select i from ekomerp$Inventory i where i.product.id = :productId and i.location.id = :locationId")
                .setParameter("productId",product.getId())
                .setParameter("locationId",location.getId()))
                .setView("inventory-view");
        return dataManager.loadList(loadContext);

    }
}