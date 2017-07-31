package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ekom.ekomerp.FourDigitsScaleBigDecimal;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Table(name = "EKOMERP_INVOICE_LINE")
@Entity(name = "ekomerp$InvoiceLine")
public class InvoiceLine extends StandardEntity {
    private static final long serialVersionUID = -5082623466617086879L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    @Column(name = "QUANTITY", nullable = false, precision = 19, scale = 4)
    protected BigDecimal quantity = new BigDecimal("1.0");

    @MetaProperty(mandatory = true, datatype = FourDigitsScaleBigDecimal.NAME)
    @Column(name = "PRICE", nullable = false, precision = 19, scale = 4)
    protected BigDecimal price;

    @Column(name = "SUBTOTAL")
    protected BigDecimal subtotal;

    @Column(name = "TAX")
    protected BigDecimal tax;

    @Column(name = "TOTAL")
    protected BigDecimal total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_ID")
    protected Invoice invoice;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }


    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }


    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }


    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Invoice getInvoice() {
        return invoice;
    }


    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }






}