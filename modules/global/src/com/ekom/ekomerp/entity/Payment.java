package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.Lob;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@NamePattern("%s|number")
@Table(name = "EKOMERP_PAYMENT")
@Entity(name = "ekomerp$Payment")
public class Payment extends StandardEntity {
    private static final long serialVersionUID = 4494133011794964916L;

    @Column(name = "NUMBER_")
    protected String number;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_")
    protected Date date = new Date(System.currentTimeMillis());

    @Column(name = "PAYMENT_METHOD")
    protected String paymentMethod;

    @Lob
    @Column(name = "NOTES")
    protected String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_ID")
    protected Invoice invoice;

    @Column(name = "PAYMENT_TYPE")
    protected String paymentType;

    @Column(name = "AMOUNT", nullable = false)
    protected BigDecimal amount = new BigDecimal("0.0");

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }


    public void setPaymentType(PaymentTypeEnum paymentType) {
        this.paymentType = paymentType == null ? null : paymentType.getId();
    }

    public PaymentTypeEnum getPaymentType() {
        return paymentType == null ? null : PaymentTypeEnum.fromId(paymentType);
    }


    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Invoice getInvoice() {
        return invoice;
    }


    public void setPaymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod == null ? null : paymentMethod.getId();
    }

    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod == null ? null : PaymentMethodEnum.fromId(paymentMethod);
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
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


}