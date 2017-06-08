package com.ekom.ekomerp.listener;

import com.ekom.ekomerp.entity.*;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.PersistenceHelper;
import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeDeleteEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import com.ekom.ekomerp.service.InventoryWorker;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Component("ekomerp_StockMovementEntityListener")
public class StockMovementEntityListener implements BeforeUpdateEntityListener<StockMovement> {
    @Inject
    protected InventoryWorker inventoryWorker;
    @Inject
    protected Persistence persistence;


    @Override
    public void onBeforeUpdate(StockMovement entity, EntityManager entityManager) {
            removeOldStockMovementLines(entity, entityManager);
            addOldStockMovementLines(entity, entityManager);
    }
    private void addOldStockMovementLines(StockMovement stockMovement, EntityManager entityManager){
        StockMovement stockMovementOld;
        try (Transaction tx = persistence.createTransaction()) {
            entityManager = persistence.getEntityManager();
            stockMovementOld = entityManager.find(StockMovement.class, stockMovement.getId(), "stockMovement-edit");
            tx.commit();
        }
        Set<StockMovementLine> stockMovementLines = stockMovementOld.getStockMovementLine();
        for (StockMovementLine line : stockMovementLines) {
            Inventory inventoryLine = inventoryWorker.findInventoryLine(line.getProduct(), stockMovement.getLocation());
            if (inventoryLine == null) {
                switch (stockMovement.getStockMovementType()) {
                    case in:
                        line.setQuantityBefore(0.0);
                        inventoryWorker.insertInventoryLine(line.getProduct(), stockMovement.getLocation(), line.getQuantity());
                        line.setQuantityAfter(line.getQuantity());
                        break;
                    case out:
                        line.setQuantityBefore(0.0);
                        inventoryWorker.insertInventoryLine(line.getProduct(), stockMovement.getLocation(), line.getQuantity()*(-1));
                        line.setQuantityAfter(line.getQuantity()*(-1));
                        break;
                    case adjustment:
                        line.setQuantityBefore(0.0);
                        inventoryWorker.insertInventoryLine(line.getProduct(), stockMovement.getLocation(), line.getQuantity());
                        line.setQuantityAfter(line.getQuantity());
                        break;
                }
            }else{
                switch (stockMovement.getStockMovementType()) {
                    case in:
                        line.setQuantityBefore(inventoryLine.getQuantity());
                        inventoryWorker.increaseInventoryLine(inventoryLine, line.getQuantity());
                        line.setQuantityAfter(inventoryLine.getQuantity());
                        break;
                    case out:
                        line.setQuantityBefore(inventoryLine.getQuantity());
                        inventoryWorker.reduceInventoryLine(inventoryLine, line.getQuantity());
                        line.setQuantityAfter(inventoryLine.getQuantity());
                        break;
                    case adjustment:
                        line.setQuantityBefore(inventoryLine.getQuantity());
                        inventoryWorker.increaseInventoryLine(inventoryLine, line.getQuantity());
                        line.setQuantityAfter(inventoryLine.getQuantity());
                        break;
                }
            }
        }
    }
    private void removeStockMovement(StockMovement stockMovement, EntityManager entityManager) {
        Set<StockMovementLine> lines = stockMovement.getStockMovementLine();
        Inventory inventoryLine;
        StockmovementTypeEnum typeEnum;
        Location location;
        if(persistence.getTools().getDirtyFields(stockMovement).contains("stockMovementType")) {
            typeEnum = (StockmovementTypeEnum) persistence.getTools().getOldValue(stockMovement, "stockMovementType");
        }
        else {
            typeEnum = stockMovement.getStockMovementType();
        }
        if(persistence.getTools().getDirtyFields(stockMovement).contains("location")){
            location = (Location) persistence.getTools().getOldValue(stockMovement, "location");
        }else{
            location = stockMovement.getLocation();
        }

        for (StockMovementLine line : lines) {
            inventoryLine = inventoryWorker.findInventoryLine(line.getProduct(), location);
            if (inventoryLine != null) {
                switch (typeEnum) {
                    case in:
                        inventoryWorker.reduceInventoryLine(inventoryLine, line.getQuantity());
                        break;
                    case out:
                        inventoryWorker.increaseInventoryLine(inventoryLine, line.getQuantity());
                        break;
                    case adjustment:
                        inventoryWorker.reduceInventoryLine(inventoryLine, line.getQuantity());
                        break;
                }
            }
        }
    }
    private void removeOldStockMovementLines(StockMovement stockMovement, EntityManager entityManager) {
        try (Transaction tx = persistence.createTransaction()) {
            entityManager = persistence.getEntityManager();
            stockMovement = entityManager.find(StockMovement.class, stockMovement.getId(), "stockMovement-edit");
            tx.commit();
        }
        if (stockMovement != null) {
            removeStockMovement(stockMovement,entityManager);
        }
    }

}