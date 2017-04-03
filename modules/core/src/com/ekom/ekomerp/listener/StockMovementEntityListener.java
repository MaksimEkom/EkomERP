package com.ekom.ekomerp.listener;

import com.ekom.ekomerp.entity.Inventory;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeDeleteEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.ekom.ekomerp.entity.StockMovement;
import com.ekom.ekomerp.entity.StockMovementLine;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import com.ekom.ekomerp.service.InventoryWorker;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component("ekomerp_StockMovementEntityListener")
public class StockMovementEntityListener implements BeforeUpdateEntityListener<StockMovement> {
    @Inject
    protected InventoryWorker inventoryWorker;
    @Inject
    protected Persistence persistence;
    @Override
    public void onBeforeUpdate(StockMovement entity, EntityManager entityManager) {
       removeOldStockMovementLines(entity,entityManager);
       addStockMovement(entity,entityManager);
    }
    private void addStockMovement(StockMovement stockMovement, EntityManager entityManager){
        Set<StockMovementLine> stockMovementLines = stockMovement.getStockMovementLine();
        for (StockMovementLine line : stockMovementLines) {
            Inventory inventoryLine = inventoryWorker.findInventoryLine(line.getProduct(), stockMovement.getLocation());
            if (inventoryLine == null) {
                switch (stockMovement.getStockMovementType()) {
                    case in:
                        inventoryWorker.insertInventoryLine(line.getProduct(), stockMovement.getLocation(), line.getQuantity());
                        break;
                    case out:
                        break;
                }
            }else{
                switch (stockMovement.getStockMovementType()) {
                    case in:
                        inventoryWorker.increaseInventoryLine(inventoryLine, line.getQuantity());
                        break;
                    case out:
                        inventoryWorker.reduceInventoryLine(inventoryLine, line.getQuantity());
                        break;
                }
            }
        }
    }
    private void removeStockMovement(StockMovement stockMovement, EntityManager entityManager) {
        Set<StockMovementLine> lines = stockMovement.getStockMovementLine();
        Inventory inventoryLine;
        for (StockMovementLine line : lines) {
            inventoryLine = inventoryWorker.findInventoryLine(line.getProduct(), stockMovement.getLocation());
            if (inventoryLine != null) {
                switch (stockMovement.getStockMovementType()) {
                    case in:
                        inventoryWorker.reduceInventoryLine(inventoryLine, line.getQuantity());
                        break;
                    case out:
                        inventoryWorker.increaseInventoryLine(inventoryLine, line.getQuantity());
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