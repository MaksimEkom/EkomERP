package com.ekom.ekomerp.listener;

import com.ekom.ekomerp.entity.StockMovement;
import org.springframework.stereotype.Component;
import com.haulmont.cuba.core.listener.BeforeDeleteEntityListener;
import com.haulmont.cuba.core.EntityManager;
import com.ekom.ekomerp.entity.StockMovementLine;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;

import javax.inject.Inject;

@Component("ekomerp_StockMovementLineEntityListener")
public class StockMovementLineEntityListener implements BeforeDeleteEntityListener<StockMovementLine>, BeforeInsertEntityListener<StockMovementLine>, BeforeUpdateEntityListener<StockMovementLine> {

    @Inject
    protected StockMovementEntityListener stockMovementEntityListener;
    @Override
    public void onBeforeDelete(StockMovementLine entity, EntityManager entityManager) {
        stockMovementEntityListener.onBeforeUpdate(entity.getStockMovement(),entityManager);
    }


    @Override
    public void onBeforeInsert(StockMovementLine entity, EntityManager entityManager) {
        stockMovementEntityListener.onBeforeUpdate(entity.getStockMovement(),entityManager);
    }


    @Override
    public void onBeforeUpdate(StockMovementLine entity, EntityManager entityManager) {
        stockMovementEntityListener.onBeforeUpdate(entity.getStockMovement(),entityManager);
    }


}