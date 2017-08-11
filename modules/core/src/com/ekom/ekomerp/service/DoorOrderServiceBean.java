package com.ekom.ekomerp.service;

import com.ekom.ekomerp.entity.DoorOrderStatus;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import org.springframework.stereotype.Service;

import com.ekom.ekomerp.entity.DoorOrder;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.entity.Entity;
import com.ekom.ekomerp.entity.Gauging;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service(DoorOrderService.NAME)
public class DoorOrderServiceBean implements DoorOrderService {
    @Inject
    protected Persistence persistence;
    @Inject
    private DataManager dataManager;

    public List<DoorOrder> findOrderNumber(Gauging gauging) {
        LoadContext loadContext = LoadContext.create(DoorOrder.class).setQuery(LoadContext
                .createQuery("select g from ekomerp$DoorOrder g where g.number = :number ")
                .setParameter("number", gauging.getDoorOrderNumber())).setView("doorOrder-edit");

        return dataManager.loadList(loadContext);
    }


    public void createDoorOrderFromGauging(Gauging gauging){
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            DoorOrder doorOrder = findOrderNumber(gauging).get(0);
            doorOrder.setWidth(gauging.getWidth());
            doorOrder.setHeight(gauging.getHeight());
            doorOrder.setMaster(gauging.getMaster());
            doorOrder.setStatus(DoorOrderStatus.done);
            em.merge(doorOrder);
            tx.commit();
        }
    }

}