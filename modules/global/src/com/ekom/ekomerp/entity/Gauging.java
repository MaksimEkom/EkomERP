package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import java.math.BigDecimal;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Table(name = "EKOMERP_GAUGING")
@Entity(name = "ekomerp$Gauging")
public class Gauging extends StandardEntity {
    private static final long serialVersionUID = -2228317881082339183L;

    @Column(name = "DOOR_ORDER_NUMBER", length = 20)
    protected String doorOrderNumber;

    @Column(name = "WIDTH")
    protected BigDecimal width;

    @Column(name = "HEIGHT")
    protected BigDecimal height;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARTNER_ID")
    protected Partner partner;

    @Column(name = "NOTES")
    protected String notes;

    @Column(name = "MASTER")
    protected Integer master;

    public void setMaster(Master master) {
        this.master = master == null ? null : master.getId();
    }

    public Master getMaster() {
        return master == null ? null : Master.fromId(master);
    }


    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }


    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }


    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }


    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }






    public void setDoorOrderNumber(String doorOrderNumber) {
        this.doorOrderNumber = doorOrderNumber;
    }

    public String getDoorOrderNumber() {
        return doorOrderNumber;
    }


}