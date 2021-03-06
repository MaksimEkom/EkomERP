package com.ekom.ekomerp.listener;

import com.ekom.ekomerp.entity.Inventory;
import com.ekom.ekomerp.entity.StockMovement;
import com.ekom.ekomerp.service.InventoryWorker;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeDeleteEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.ekom.ekomerp.entity.StockMovementLine;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import javax.inject.Inject;
import java.util.Set;

@Component("ekomerp_StockMovementLineEntityListener")
public class StockMovementLineEntityListener implements BeforeDeleteEntityListener<StockMovementLine>, BeforeInsertEntityListener<StockMovementLine>, BeforeUpdateEntityListener<StockMovementLine> {
    @Inject
    protected InventoryWorker inventoryWorker;
    @Inject
    protected StockMovementEntityListener stockMovementEntityListener;
    @Inject
    protected Persistence persistence;

    @Override
    public void onBeforeDelete(StockMovementLine entity, EntityManager entityManager) {
        removeStockMovementLine(entity,entityManager);
    }

    @Override
    public void onBeforeInsert(StockMovementLine entity, EntityManager entityManager) {
        addStockMovementLine(entity,entityManager);
    }

    @Override
    public void onBeforeUpdate(StockMovementLine entity, EntityManager entityManager) {
        removeOldStockMovementLine(entity,entityManager);
        addStockMovementLine(entity,entityManager);
    }
    private void addStockMovementLine(StockMovementLine stockMovementLine, EntityManager entityManager){
        Inventory inventoryLine = inventoryWorker.findInventoryLine(stockMovementLine.getProduct(), stockMovementLine.getStockMovement().getLocation());
        if (inventoryLine == null) {
            switch (stockMovementLine.getStockMovement().getStockMovementType()) {
                case in:
                    inventoryWorker.insertInventoryLine(stockMovementLine.getProduct(),
                            stockMovementLine.getStockMovement().getLocation(), stockMovementLine.getQuantity());
                    break;
                case out:
                    inventoryWorker.insertInventoryLine(stockMovementLine.getProduct(),
                            stockMovementLine.getStockMovement().getLocation(), stockMovementLine.getQuantity()*(-1));
                    break;
            }
        }else{
            switch (stockMovementLine.getStockMovement().getStockMovementType()) {
                case in:
                    inventoryWorker.increaseInventoryLine(inventoryLine, stockMovementLine.getQuantity());
                    break;
                case out:
                    inventoryWorker.reduceInventoryLine(inventoryLine, stockMovementLine.getQuantity());
                    break;
            }
        }
    }
    private void removeStockMovementLine(StockMovementLine stockMovementLine, EntityManager entityManager) {
        Inventory inventoryLine;
        inventoryLine = inventoryWorker.findInventoryLine(stockMovementLine.getProduct(), stockMovementLine.getStockMovement().getLocation());
        if (inventoryLine != null) {
            switch (stockMovementLine.getStockMovement().getStockMovementType()) {
                case in:
                    inventoryWorker.reduceInventoryLine(inventoryLine, stockMovementLine.getQuantity());
                    break;
                case out:
                    inventoryWorker.increaseInventoryLine(inventoryLine, stockMovementLine.getQuantity());
                    break;
            }
        }
    }
    private void removeOldStockMovementLine(StockMovementLine stockMovementLine, EntityManager entityManager) {
        try (Transaction tx = persistence.createTransaction()) {
            entityManager = persistence.getEntityManager();
            stockMovementLine = entityManager.find(StockMovementLine.class, stockMovementLine.getId(), "stockMovementLine-edit");
            tx.commit();
        }
        if (stockMovementLine != null) {
            removeStockMovementLine(stockMovementLine,entityManager);
        }
    }

}