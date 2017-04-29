package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.StandardEntity;
import javax.persistence.Lob;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.Set;
import javax.persistence.OneToMany;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NamePattern("%s|number")
@Table(name = "EKOMERP_INVOICE")
@Entity(name = "ekomerp$Invoice")
public class Invoice extends StandardEntity {
    private static final long serialVersionUID = -6064777013898527202L;

    @Column(name = "NUMBER_", nullable = false)
    protected String number = "Новый";

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_", nullable = false)
    protected Date date = new Date(System.currentTimeMillis());

    @Temporal(TemporalType.DATE)
    @Column(name = "DUE_DATE")
    protected Date dueDate;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTNER_ID")
    protected Partner partner;

    @Column(name = "STATE")
    protected String state = "open";

    @Column(name = "TYPE_")
    protected String type;

    @Column(name = "ORIGIN")
    protected String origin;

    @Column(name = "AMOUNT_UNTAXED")
    protected Double amountUntaxed;

    @Column(name = "AMOUNT_TAX")
    protected Double amountTax;

    @Column(name = "AMOUNT_TOTAL")
    protected Double amountTotal;

    @Lob
    @Column(name = "NOTES")
    protected String notes;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "invoice")
    protected Set<InvoiceLine> invoiceLine;

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }


    public void setInvoiceLine(Set<InvoiceLine> invoiceLine) {
        this.invoiceLine = invoiceLine;
    }

    public Set<InvoiceLine> getInvoiceLine() {
        return invoiceLine;
    }


    public void setState(InvoiceStateEnum state) {
        this.state = state == null ? null : state.getId();
    }

    public InvoiceStateEnum getState() {
        return state == null ? null : InvoiceStateEnum.fromId(state);
    }

    public void setType(InvoiceTypeEnum type) {
        this.type = type == null ? null : type.getId();
    }

    public InvoiceTypeEnum getType() {
        return type == null ? null : InvoiceTypeEnum.fromId(type);
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOrigin() {
        return origin;
    }

    public void setAmountUntaxed(Double amountUntaxed) {
        this.amountUntaxed = amountUntaxed;
    }

    public Double getAmountUntaxed() {
        return amountUntaxed;
    }

    public void setAmountTax(Double amountTax) {
        this.amountTax = amountTax;
    }

    public Double getAmountTax() {
        return amountTax;
    }

    public void setAmountTotal(Double amountTotal) {
        this.amountTotal = amountTotal;
    }

    public Double getAmountTotal() {
        return amountTotal;
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

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }


}