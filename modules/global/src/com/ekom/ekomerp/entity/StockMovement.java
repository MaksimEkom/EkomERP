package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import sun.util.calendar.BaseCalendar;

import java.util.Set;
import javax.persistence.OneToMany;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import javax.persistence.Lob;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|number")
@Listeners("ekomerp_StockMovementEntityListener")
@Table(name = "EKOMERP_STOCK_MOVEMENT")
@Entity(name = "ekomerp$StockMovement")
public class StockMovement extends StandardEntity {
    private static final long serialVersionUID = 5822693417666664062L;

    @Column(name = "CONSIGNMENT")
    protected String consignment;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_", nullable = false)
    protected Date date = new Date(System.currentTimeMillis());

    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(optional = false)
    @JoinColumn(name = "LOCATION_ID")
    protected Location location;

    @Column(name = "STOCK_MOVEMENT_TYPE", nullable = false)
    protected Integer stockMovementType;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "stockMovement")
    protected Set<StockMovementLine> stockMovementLine;

    @Lob
    @Column(name = "NOTES")
    protected String notes;


    @Column(name = "NUMBER_")
    protected String number = "Новый";

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }


    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }


    public void setStockMovementLine(Set<StockMovementLine> stockMovementLine) {
        this.stockMovementLine = stockMovementLine;
    }

    public Set<StockMovementLine> getStockMovementLine() {
        return stockMovementLine;
    }


    public void setConsignment(String consignment) {
        this.consignment = consignment;
    }

    public String getConsignment() {
        return consignment;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setStockMovementType(StockmovementTypeEnum stockMovementType) {
        this.stockMovementType = stockMovementType == null ? null : stockMovementType.getId();
    }

    public StockmovementTypeEnum getStockMovementType() {
        return stockMovementType == null ? null : StockmovementTypeEnum.fromId(stockMovementType);
    }


}