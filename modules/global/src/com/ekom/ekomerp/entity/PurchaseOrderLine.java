package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.StandardEntity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Table(name = "EKOMERP_PURCHASE_ORDER_LINE")
@Entity(name = "ekomerp$PurchaseOrderLine")
public class PurchaseOrderLine extends StandardEntity {
    private static final long serialVersionUID = -8643213507842123418L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    @Column(name = "QUANTITY", nullable = false)
    protected Double quantity = 1.0;

    @Column(name = "PRICE", nullable = false)
    protected Double price;

    @Column(name = "SUBTOTAL")
    protected Double subtotal;

    @Column(name = "TAX")
    protected Double tax;

    @Column(name = "TOTAL")
    protected Double total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PURCHASE_ORDER_ID")
    protected PurchaseOrder purchaseOrder;

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTax() {
        return tax;
    }


    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }


    public void setTotal(Double total) {
        this.total = total;
    }


    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
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

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public Double getTotal() {
        return total;
    }


}