package com.ekom.ekomerp.service;

import org.springframework.stereotype.Service;

import com.ekom.ekomerp.entity.Gauging;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Transaction;
import org.springframework.stereotype.Service;
import com.haulmont.cuba.core.entity.Entity;
import com.ekom.ekomerp.entity.DoorOrder;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Service(GaugingService.NAME)
public class GaugingServiceBean implements GaugingService {
    @Inject
    protected Persistence persistence;
    public void createGaugingFromDoorOrder(DoorOrder doorOrder){
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            Gauging gauging = new Gauging();
            gauging.setDoorOrderNumber(doorOrder.getNumber());
            em.persist(gauging);
            tx.commit();

        }
    }

}