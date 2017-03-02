package com.ekom.ekomerp.service;

import com.ekom.ekomerp.entity.Location;
import com.ekom.ekomerp.entity.Product;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Component;
import javax.inject.Inject;
import javax.sql.DataSource;

import com.ekom.ekomerp.entity.Inventory;

import java.util.UUID;

/**
 * Created by Sergey on 24.02.2017.
 */

@Component(InventoryWorker.NAME)
public class InventoryWorker {

    public static final String NAME = "ekomerp_InventoryWorker";

    @Inject
    protected Persistence persistence;

    /**
     * Поиск строки по коду места хранения и коду продукта.
     */
    public Inventory findInventoryLine(Product product, Location location) {
            EntityManager em = persistence.getEntityManager();
            TypedQuery<Inventory> invQuery = em.createQuery("select i from ekomerp$Inventory i where i.product.id = ?1 and i.location.id = ?2",
                    Inventory.class);
            invQuery.setParameter(1, product.getId());
            invQuery.setParameter(2, location.getId());
            invQuery.setMaxResults(1);
            Inventory inventoryLine = invQuery.getFirstResult();
            return inventoryLine;
    }
    /**
     * Вставка новой строки в Inventory.
     */
    public void insertInventoryLine(Product product, Location location, double quantity){
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            Inventory inv = new Inventory();
            inv.setLocation(location);
            inv.setProduct(product);
            inv.setQuantity(quantity);
            em.persist(inv);
            tx.commit();
        }
    }

    /**
     * Увеличение значения количества
     */
    public void increaseInventoryLine(Inventory inventoryLine, double quantity){
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            inventoryLine.setQuantity(inventoryLine.getQuantity()+quantity);
            em.merge(inventoryLine);
            tx.commit();
        }
    }

    /**
     * Уменьшение значения количества
     */
    public void reduceInventoryLine(Inventory inventoryLine, double quantity){
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            inventoryLine.setQuantity(inventoryLine.getQuantity()-quantity);
            em.merge(inventoryLine);
            tx.commit();
        }
    }


}
