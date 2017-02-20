package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "EKOMERP_CONSUMPTION")
@Entity(name = "ekomerp$Consumption")
public class Consumption extends StandardEntity {
    private static final long serialVersionUID = 8482106517592679738L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup", "open"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONSUMABLE_PRODUCT_ID")
    protected Product consumableProduct;

    @Column(name = "QUANTITY", nullable = false)
    protected Double quantity;

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setConsumableProduct(Product consumableProduct) {
        this.consumableProduct = consumableProduct;
    }

    public Product getConsumableProduct() {
        return consumableProduct;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getQuantity() {
        return quantity;
    }


}