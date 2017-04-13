package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.Set;
import javax.persistence.OneToMany;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|number")
@Table(name = "EKOMERP_PURCHASE_ORDER")
@Entity(name = "ekomerp$PurchaseOrder")
public class PurchaseOrder extends StandardEntity {
    private static final long serialVersionUID = -202084360980005165L;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VENDOR_ID")
    protected Partner vendor;

    @Column(name = "STATE")
    protected String state = "draft";

    @Lob
    @Column(name = "NOTES")
    protected String notes;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "purchaseOrder")
    protected Set<PurchaseOrderLine> purchaseOrderLine;

    @Column(name = "AMOUNT_UNTAXED")
    protected Double amountUntaxed;

    @Column(name = "AMOUNT_TAX")
    protected Double amountTax;

    @Column(name = "AMOUNT_WITH_TAX")
    protected Double amountWithTax;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_", nullable = false)
    protected Date date = new Date(System.currentTimeMillis());

    @Column(name = "NUMBER_", nullable = false)
    protected String number = "Новый";

    @Temporal(TemporalType.DATE)
    @Column(name = "DELIVERY_DATE")
    protected Date deliveryDate;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PAYMENT_CONDITION_ID")
    protected PaymentCondition paymentCondition;

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setPaymentCondition(PaymentCondition paymentCondition) {
        this.paymentCondition = paymentCondition;
    }

    public PaymentCondition getPaymentCondition() {
        return paymentCondition;
    }


    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }



    public void setAmountUntaxed(Double amountUntaxed) {
        this.amountUntaxed = amountUntaxed;
    }


    public void setAmountTax(Double amountTax) {
        this.amountTax = amountTax;
    }


    public void setAmountWithTax(Double amountWithTax) {
        this.amountWithTax = amountWithTax;
    }


    public Double getAmountUntaxed() {
        return amountUntaxed;
    }

    public Double getAmountTax() {
        return amountTax;
    }

    public Double getAmountWithTax() {
        return amountWithTax;
    }


    public void setPurchaseOrderLine(Set<PurchaseOrderLine> purchaseOrderLine) {
        this.purchaseOrderLine = purchaseOrderLine;
    }

    public Set<PurchaseOrderLine> getPurchaseOrderLine() {
        return purchaseOrderLine;
    }


    public void setState(PurchaseOrderState state) {
        this.state = state == null ? null : state.getId();
    }

    public PurchaseOrderState getState() {
        return state == null ? null : PurchaseOrderState.fromId(state);
    }


    public void setVendor(Partner vendor) {
        this.vendor = vendor;
    }

    public Partner getVendor() {
        return vendor;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }


}