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
public class StockMovementEntityListener implements BeforeDeleteEntityListener<StockMovement>, BeforeInsertEntityListener<StockMovement>, BeforeUpdateEntityListener<StockMovement> {
    @Inject
    protected InventoryWorker inventoryWorker;
    @Inject
    protected Persistence persistence;
    @Override
    public void onBeforeDelete(StockMovement entity, EntityManager entityManager) {
        removeStockMovement(entity,entityManager);
    }

    @Override
    public void onBeforeInsert(StockMovement entity, EntityManager entityManager) {
       addStockMovement(entity,entityManager);
    }

    @Override
    public void onBeforeUpdate(StockMovement entity, EntityManager entityManager) {
       removeOldStockMovementLines(entity,entityManager);
       //addStockMovement(entity,entityManager);
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
            EntityManager em = persistence.getEntityManager();
            stockMovement = em.find(StockMovement.class, stockMovement.getId(), "stockMovement-edit");
            tx.commit();
        }
        if (stockMovement != null) {
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
    }
//    private void reorderInventoryLine(StockMovement entity, EntityManager entityManager){
//        Set<StockMovementLine> newLines = entity.getStockMovementLine();
//        List<StockMovementLine> linesList;
//        Inventory inventoryLine;
//        entityManager = persistence.getEntityManager();
//        TypedQuery<StockMovementLine> balQuery = entityManager.createQuery(
//                "select s from ekomerp$StockMovementLine s where s.stockMovement.id = ?1",
//                StockMovementLine.class);
//        balQuery.setParameter(1, entity.getId());
//        if (balQuery == null)
//            linesList = null;
//        else
//            linesList = balQuery.getResultList();
//        Set<StockMovementLine> oldLines = new HashSet<>(linesList);
//        for (StockMovementLine line : linesList) {
//            inventoryLine = inventoryWorker.findInventoryLine(line.getProduct(), entity.getLocation());
//            if (inventoryLine != null) {
//                switch (entity.getStockMovementType()) {
//                    case in:
//                        inventoryWorker.reduceInventoryLine(inventoryLine,line.getQuantity());
//                        break;
//                    case out:
//                        inventoryWorker.increaseInventoryLine(inventoryLine,line.getQuantity());
//                        break;
//                }
//            }
//        }
//        for (StockMovementLine line : newLines) {
//            inventoryLine = inventoryWorker.findInventoryLine(line.getProduct(), entity.getLocation());
//            if (inventoryLine == null) {
//                switch (entity.getStockMovementType()) {
//                    case in:
//                        inventoryWorker.insertInventoryLine(line.getProduct(), entity.getLocation(), line.getQuantity());
//                        break;
//                    case out:
//                        break;
//                }
//            } else {
//                switch (entity.getStockMovementType()) {
//                    case in:
//                        inventoryWorker.increaseInventoryLine(inventoryLine, line.getQuantity());
//                        break;
//                    case out:
//                        inventoryWorker.reduceInventoryLine(inventoryLine, line.getQuantity());
//                        break;
//                }
//
//            }
//        }
//    }


    }