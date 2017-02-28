package com.ekom.ekomerp.listener;

import com.ekom.ekomerp.entity.Inventory;
import com.google.common.collect.Maps;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.sun.javafx.collections.MappingChange;
import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeDeleteEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.ekom.ekomerp.entity.StockMovement;
import com.ekom.ekomerp.entity.StockMovementLine;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import com.ekom.ekomerp.service.InventoryWorker;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component("ekomerp_StockMovementEntityListener")
public class StockMovementEntityListener implements BeforeDeleteEntityListener<StockMovement>, BeforeInsertEntityListener<StockMovement>, BeforeUpdateEntityListener<StockMovement> {
    @Inject
    protected InventoryWorker inventoryWorker;
    @Inject
    protected Persistence persistence;
    @Override
    public void onBeforeDelete(StockMovement entity, EntityManager entityManager) {
        deleteOrUpdateInventoryLines(entity,entityManager);
    }


    @Override
    public void onBeforeInsert(StockMovement entity, EntityManager entityManager) {
        addOrUpdateInventoryLines(entity,entityManager);
    }


    @Override
    public void onBeforeUpdate(StockMovement entity, EntityManager entityManager) {
        reorderInventoryLine(entity,entityManager);
    }

   private void addOrUpdateInventoryLines(StockMovement entity, EntityManager entityManager) {
       Set<StockMovementLine> lines = entity.getStockMovementLine();
       Inventory inventoryLine;

       for (StockMovementLine line : lines) {
           inventoryLine = inventoryWorker.findInventoryLine(line.getProduct().getId(), entity.getLocation().getId());
           if (inventoryLine == null) {
               switch (entity.getStockMovementType()) {
                   case in:
                       inventoryWorker.addInventoryLine(line.getProduct(), entity.getLocation(), line.getQuantity());
                       break;
                   case out:
                       break;
               }
           } else {
               switch (entity.getStockMovementType()) {
                   case in:
                       inventoryWorker.incomeInventoryLine(inventoryLine, line.getQuantity());
                       break;
                   case out:
                       inventoryWorker.expenceInventoryLine(inventoryLine, line.getQuantity());
                        break;
               }

           }
       }
   }

    private void deleteOrUpdateInventoryLines(StockMovement entity, EntityManager entityManager) {
        Set<StockMovementLine> lines = entity.getStockMovementLine();
        Inventory inventoryLine;
        for (StockMovementLine line : lines) {
            inventoryLine = inventoryWorker.findInventoryLine(line.getProduct().getId(), entity.getLocation().getId());
            if (inventoryLine != null) {
                switch (entity.getStockMovementType()) {
                    case in:
                        inventoryWorker.expenceInventoryLine(inventoryLine, line.getQuantity());
                        break;
                    case out:
                        inventoryWorker.incomeInventoryLine(inventoryLine, line.getQuantity());
                        break;
                }
            }

        }
    }

    private void reorderInventoryLine(StockMovement entity, EntityManager entityManager){
        Set<StockMovementLine> newLines = entity.getStockMovementLine();
        StockMovementLine oldLine;
        Inventory inventoryLine;

        for (StockMovementLine line : newLines) {
            inventoryLine = inventoryWorker.findInventoryLine(line.getProduct().getId(), entity.getLocation().getId());
            try (Transaction tx = persistence.createTransaction()) {
                entityManager = persistence.getEntityManager();
                TypedQuery<StockMovementLine> balQuery = entityManager.createQuery(
                        "select s from ekomerp$StockMovementLine s where s.id = ?1",
                        StockMovementLine.class);
                balQuery.setParameter(1, line.getId());
                balQuery.setMaxResults(1);
                if (balQuery == null)
                    oldLine = null;
                else
                    oldLine = balQuery.getFirstResult();
            }
            if (inventoryLine != null) {
                switch (entity.getStockMovementType()) {
                    case in:
                        inventoryWorker.reorderInventoryLine(inventoryLine,oldLine.getQuantity(),line.getQuantity());
                        break;
                    case out:
                        inventoryWorker.reorderInventoryLine(inventoryLine,line.getQuantity(),oldLine.getQuantity());
                        break;
                }
            }

        }
    }

    public void clearStockMovement(StockMovement entity, EntityManager entityManager){
        List<StockMovementLine> lines;
        Inventory inventoryLine;
        try (Transaction tx = persistence.createTransaction()) {
            entityManager = persistence.getEntityManager();
            TypedQuery<StockMovementLine> balQuery = entityManager.createQuery(
                    "select s from ekomerp$StockMovementLine s where s.id = ?1",
                    StockMovementLine.class);
            balQuery.setParameter(1, entity.getId());
            if (balQuery == null)
                lines = null;
            else
                lines = balQuery.getResultList();
        }
        for (StockMovementLine line : lines) {
            inventoryLine = inventoryWorker.findInventoryLine(line.getProduct().getId(), entity.getLocation().getId());
            if (inventoryLine != null) {
                switch (entity.getStockMovementType()) {
                    case in:
                        inventoryWorker.expenceInventoryLine(inventoryLine, line.getQuantity());
                        break;
                    case out:
                        inventoryWorker.incomeInventoryLine(inventoryLine, line.getQuantity());
                        break;
                }
            }
        }
    }

}