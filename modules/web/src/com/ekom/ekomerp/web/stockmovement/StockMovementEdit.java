package com.ekom.ekomerp.web.stockmovement;

import com.ekom.ekomerp.entity.Inventory;
import com.ekom.ekomerp.entity.StockMovementLine;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.ekom.ekomerp.entity.StockMovement;
import com.haulmont.cuba.gui.data.CollectionDatasource;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class StockMovementEdit extends AbstractEditor<StockMovement> {
    @Inject
    private CollectionDatasource<StockMovementLine, UUID> stockMovementLineDs;


    @Override
    public void init(Map<String, Object> params) {

        stockMovementLineDs.addCollectionChangeListener(e -> checkNegativeInventory(stockMovementLineDs.getItem().getStockMovement()));
    }

    private void checkNegativeInventory(StockMovement stockMovement) {
        Inventory inventory = new Inventory();

        switch(stockMovement.getStockMovementType()){
            case in:
                if(inventory.getProduct()){

                }
                break;
            case out:
                break;
        }

    }
}