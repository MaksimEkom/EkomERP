package com.ekom.ekomerp.listener;

import com.ekom.ekomerp.entity.Inventory;
import com.google.common.collect.Maps;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeDeleteEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.ekom.ekomerp.entity.StockMovement;
import com.ekom.ekomerp.entity.StockMovementLine;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import com.ekom.ekomerp.service.InventoryWorker;

import javax.inject.Inject;
import java.util.Set;

@Component("ekomerp_StockMovementEntityListener")
public class StockMovementEntityListener implements BeforeDeleteEntityListener<StockMovement>, BeforeInsertEntityListener<StockMovement>, BeforeUpdateEntityListener<StockMovement> {
    @Inject
    protected InventoryWorker inventoryWorker;
    @Inject
    protected Persistence persistence;
    @Override
    public void onBeforeDelete(StockMovement entity, EntityManager entityManager) {

    }


    @Override
    public void onBeforeInsert(StockMovement entity, EntityManager entityManager) {
        addOrUpdateInventoryLines(entity,entityManager);
    }


    @Override
    public void onBeforeUpdate(StockMovement entity, EntityManager entityManager) {

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

}