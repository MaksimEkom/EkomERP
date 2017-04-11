package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.StandardEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.Lob;

@NamePattern("%s|number")
@Table(name = "EKOMERP_MANUFACTURING_ORDER")
@Entity(name = "ekomerp$ManufacturingOrder")
public class ManufacturingOrder extends StandardEntity {
    private static final long serialVersionUID = -5100986359573988715L;

    @Column(name = "NUMBER_", nullable = false)
    protected String number;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_", nullable = false)
    protected Date date  = new Date(System.currentTimeMillis());

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CUSTOMER_ID")
    protected Partner customer;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OF_MANUFACTURE")
    protected Date dateOfManufacture;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "manufacturingOrder")
    protected Set<ManufacturingOrderLine> manufacturingOrderLine;

    @Lob
    @Column(name = "NOTES")
    protected String notes;

    @Column(name = "STATE", nullable = false)
    protected Integer state = 1;

    public void setState(ManufacturingOrderStateEnum state) {
        this.state = state == null ? null : state.getId();
    }

    public ManufacturingOrderStateEnum getState() {
        return state == null ? null : ManufacturingOrderStateEnum.fromId(state);
    }


    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }


    public void setManufacturingOrderLine(Set<ManufacturingOrderLine> manufacturingOrderLine) {
        this.manufacturingOrderLine = manufacturingOrderLine;
    }

    public Set<ManufacturingOrderLine> getManufacturingOrderLine() {
        return manufacturingOrderLine;
    }


    public void setCustomer(Partner customer) {
        this.customer = customer;
    }

    public Partner getCustomer() {
        return customer;
    }

    public void setDateOfManufacture(Date dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }

    public Date getDateOfManufacture() {
        return dateOfManufacture;
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