package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ekom.ekomerp.FourDigitsScaleBigDecimal;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.StandardEntity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Table(name = "EKOMERP_PURCHASE_ORDER_LINE")
@Entity(name = "ekomerp$PurchaseOrderLine")
public class PurchaseOrderLine extends StandardEntity {
    private static final long serialVersionUID = -8643213507842123418L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    @MetaProperty(mandatory = true, datatype = FourDigitsScaleBigDecimal.NAME)
    @Column(name = "QUANTITY", nullable = false, precision = 19, scale = 4)
    protected BigDecimal quantity = new BigDecimal("1.0");
    

    @Column(name = "PRICE", nullable = false)
    protected BigDecimal price;

    @Column(name = "SUBTOTAL")
    protected BigDecimal subtotal;

    @Column(name = "TAX")
    protected BigDecimal tax;

    @Column(name = "TOTAL")
    protected BigDecimal total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PURCHASE_ORDER_ID")
    protected PurchaseOrder purchaseOrder;

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }


    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }


    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }


    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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








}