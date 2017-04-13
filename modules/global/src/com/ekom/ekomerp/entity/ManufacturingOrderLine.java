package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Table(name = "EKOMERP_MANUFACTURING_ORDER_LINE")
@Entity(name = "ekomerp$ManufacturingOrderLine")
public class ManufacturingOrderLine extends StandardEntity {
    private static final long serialVersionUID = -5487764399009866306L;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    @Column(name = "QUANTITY", nullable = false)
    protected Double quantity;

    @Column(name = "QUANTITY_PRODUCED")
    protected Double quantityProduced;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANUFACTURING_ORDER_ID")
    protected ManufacturingOrder manufacturingOrder;

    public void setQuantityProduced(Double quantityProduced) {
        this.quantityProduced = quantityProduced;
    }

    public Double getQuantityProduced() {
        return quantityProduced;
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

    public void setManufacturingOrder(ManufacturingOrder manufacturingOrder) {
        this.manufacturingOrder = manufacturingOrder;
    }

    public ManufacturingOrder getManufacturingOrder() {
        return manufacturingOrder;
    }


}