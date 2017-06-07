package com.ekom.ekomerp.listener;

import com.ekom.ekomerp.entity.Inventory;
import com.ekom.ekomerp.entity.StockMovement;
import com.ekom.ekomerp.service.InventoryWorker;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.PersistenceHelper;
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
        if(!(persistence.getTools().isDirty(entity.getStockMovement()))||
                PersistenceHelper.isNew(entity.getStockMovement())) {
            removeStockMovementLine(entity, entityManager);
        }
    }

    @Override
    public void onBeforeInsert(StockMovementLine entity, EntityManager entityManager) {
       if(!(persistence.getTools().isDirty(entity.getStockMovement()))||
               PersistenceHelper.isNew(entity.getStockMovement())){

            if (inventoryWorker.findInventoryLine(entity.getProduct(), entity.getStockMovement().getLocation())==null){
                entity.setQuantityBefore(0.0);
            }else {
                entity.setQuantityBefore(inventoryWorker.findInventoryLine(entity.getProduct(), entity.getStockMovement().getLocation()).getQuantity());
            }
            addStockMovementLine(entity,entityManager);
            entity.setQuantityAfter(inventoryWorker.findInventoryLine(entity.getProduct(), entity.getStockMovement().getLocation()).getQuantity());
       }
    }

    @Override
    public void onBeforeUpdate(StockMovementLine entity, EntityManager entityManager) {
        if((!persistence.getTools().isDirty(entity.getStockMovement()))||
                PersistenceHelper.isNew(entity.getStockMovement())) {
            if (persistence.getTools().getDirtyFields(entity).contains("product") ||
                    persistence.getTools().getDirtyFields(entity).contains("quantity")) {
                removeOldStockMovementLine(entity, entityManager);
                if (inventoryWorker.findInventoryLine(entity.getProduct(), entity.getStockMovement().getLocation()) == null) {
                    entity.setQuantityBefore(0.0);
                } else {
                    entity.setQuantityBefore(inventoryWorker.findInventoryLine(entity.getProduct(), entity.getStockMovement().getLocation()).getQuantity());
                }
                addStockMovementLine(entity, entityManager);
                entity.setQuantityAfter(inventoryWorker.findInventoryLine(entity.getProduct(), entity.getStockMovement().getLocation()).getQuantity());
            }
        }
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
                case adjustment:
                    inventoryWorker.insertInventoryLine(stockMovementLine.getProduct(),
                            stockMovementLine.getStockMovement().getLocation(), stockMovementLine.getQuantity());
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
                case adjustment:
                    inventoryWorker.increaseInventoryLine(inventoryLine, stockMovementLine.getQuantity());
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
                case adjustment:
                    inventoryWorker.reduceInventoryLine(inventoryLine, stockMovementLine.getQuantity());
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