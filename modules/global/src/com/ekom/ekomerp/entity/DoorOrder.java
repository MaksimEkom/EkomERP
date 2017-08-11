package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.StandardEntity;
import java.math.BigDecimal;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Lob;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s %s|number,date")
@Table(name = "EKOMERP_DOOR_ORDER")
@Entity(name = "ekomerp$DoorOrder")
public class DoorOrder extends StandardEntity {
    private static final long serialVersionUID = -6794341817989190533L;

    @Column(name = "NUMBER_", nullable = false, length = 20)
    protected String number;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_")
    protected Date date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTNER_ID")
    protected Partner partner;

    @Temporal(TemporalType.DATE)
    @Column(name = "MANUFACTURE_DATE")
    protected Date manufactureDate;

    @Column(name = "PLACEMENT_CATEGORY", nullable = false, precision = 19, scale = 0)
    protected BigDecimal placementCategory;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DOOR_ID")
    protected Door door;

    @Column(name = "PRICE")
    protected BigDecimal price;

    @Column(name = "HEIGHT")
    protected BigDecimal height;

    @Column(name = "WIDTH")
    protected BigDecimal width;

    @Column(name = "OPENING", nullable = false)
    protected Integer opening;

    @Lob
    @Column(name = "NOTES")
    protected String notes;

    @Column(name = "ISOLATION_")
    protected Boolean isolation;

    @Column(name = "PEEPHOLE")
    protected Boolean peephole;

    @Column(name = "TRIM_")
    protected Boolean trim;

    @Column(name = "DISMANTILING")
    protected Boolean dismantiling;

    @Column(name = "LOOP_NUMBER", nullable = false)
    protected String loopNumber;

    @Column(name = "TOP_LOCK")
    protected String topLock;

    @Column(name = "BUTTOMLOCK")
    protected String buttomlock;

    @Column(name = "TOP_ONLAY")
    protected String topOnlay;

    @Column(name = "BUTTOM_ONLAY")
    protected String buttomOnlay;

    @Column(name = "HANDLE")
    protected String handle;

    @Column(name = "CYLINDER")
    protected String cylinder;

    @Lob
    @Column(name = "INTERNAL_DECORATION")
    protected String internalDecoration;

    @Lob
    @Column(name = "EXTERNAL_DECORATION")
    protected String externalDecoration;

    @Column(name = "STATUS", nullable = false)
    protected Integer status = DoorOrderStatus.open.getId();

    @Column(name = "MASTER")
    protected Integer master;

    @Column(name = "PAYED")
    protected Boolean payed;

    public void setDoor(Door door) {
        this.door = door;
    }

    public Door getDoor() {
        return door;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setOpening(Opening opening) {
        this.opening = opening == null ? null : opening.getId();
    }

    public Opening getOpening() {
        return opening == null ? null : Opening.fromId(opening);
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setIsolation(Boolean isolation) {
        this.isolation = isolation;
    }

    public Boolean getIsolation() {
        return isolation;
    }

    public void setPeephole(Boolean peephole) {
        this.peephole = peephole;
    }

    public Boolean getPeephole() {
        return peephole;
    }

    public void setTrim(Boolean trim) {
        this.trim = trim;
    }

    public Boolean getTrim() {
        return trim;
    }

    public void setDismantiling(Boolean dismantiling) {
        this.dismantiling = dismantiling;
    }

    public Boolean getDismantiling() {
        return dismantiling;
    }

    public void setLoopNumber(String loopNumber) {
        this.loopNumber = loopNumber;
    }

    public String getLoopNumber() {
        return loopNumber;
    }

    public void setTopLock(String topLock) {
        this.topLock = topLock;
    }

    public String getTopLock() {
        return topLock;
    }

    public void setButtomlock(String buttomlock) {
        this.buttomlock = buttomlock;
    }

    public String getButtomlock() {
        return buttomlock;
    }

    public void setTopOnlay(String topOnlay) {
        this.topOnlay = topOnlay;
    }

    public String getTopOnlay() {
        return topOnlay;
    }

    public void setButtomOnlay(String buttomOnlay) {
        this.buttomOnlay = buttomOnlay;
    }

    public String getButtomOnlay() {
        return buttomOnlay;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getHandle() {
        return handle;
    }

    public void setCylinder(String cylinder) {
        this.cylinder = cylinder;
    }

    public String getCylinder() {
        return cylinder;
    }

    public void setInternalDecoration(String internalDecoration) {
        this.internalDecoration = internalDecoration;
    }

    public String getInternalDecoration() {
        return internalDecoration;
    }

    public void setExternalDecoration(String externalDecoration) {
        this.externalDecoration = externalDecoration;
    }

    public String getExternalDecoration() {
        return externalDecoration;
    }

    public void setStatus(DoorOrderStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public DoorOrderStatus getStatus() {
        return status == null ? null : DoorOrderStatus.fromId(status);
    }

    public void setMaster(Master master) {
        this.master = master == null ? null : master.getId();
    }

    public Master getMaster() {
        return master == null ? null : Master.fromId(master);
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }

    public Boolean getPayed() {
        return payed;
    }


    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public Date getManufactureDate() {
        return manufactureDate;
    }

    public void setPlacementCategory(BigDecimal placementCategory) {
        this.placementCategory = placementCategory;
    }

    public BigDecimal getPlacementCategory() {
        return placementCategory;
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