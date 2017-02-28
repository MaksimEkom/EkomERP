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
import com.haulmont.cuba.core.entity.annotation.Listeners;

@Listeners("ekomerp_StockMovementLineEntityListener")
@Table(name = "EKOMERP_STOCK_MOVEMENT_LINE")
@Entity(name = "ekomerp$StockMovementLine")
public class StockMovementLine extends StandardEntity {
    private static final long serialVersionUID = 8540208460866882433L;

    @Lookup(type = LookupType.SCREEN, actions = {"lookup", "open"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    @Column(name = "QUANTITY", nullable = false)
    protected Double quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_MOVEMENT_ID")
    protected StockMovement stockMovement;

    @Column(name = "QUANTITY_BEFORE")
    protected Double quantityBefore;

    @Column(name = "QUANTITY_AFTER")
    protected Double quantityAfter;

    public void setQuantityBefore(Double quantityBefore) {
        this.quantityBefore = quantityBefore;
    }

    public Double getQuantityBefore() {
        return quantityBefore;
    }

    public void setQuantityAfter(Double quantityAfter) {
        this.quantityAfter = quantityAfter;
    }

    public Double getQuantityAfter() {
        return quantityAfter;
    }


    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setStockMovement(StockMovement stockMovement) {
        this.stockMovement = stockMovement;
    }

    public StockMovement getStockMovement() {
        return stockMovement;
    }


}