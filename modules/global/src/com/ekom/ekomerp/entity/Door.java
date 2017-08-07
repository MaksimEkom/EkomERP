package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Lob;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s %s|cd,designation")
@Table(name = "EKOMERP_DOOR")
@Entity(name = "ekomerp$Door")
public class Door extends StandardEntity {
    private static final long serialVersionUID = -3865661075869641131L;

    @Column(name = "DOOR_KIND", nullable = false)
    protected Integer doorKind;

    @Column(name = "CD", nullable = false, length = 50)
    protected String cd;

    @Column(name = "DESIGNATION")
    protected String designation;

    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    public void setDoorKind(DoorKind doorKind) {
        this.doorKind = doorKind == null ? null : doorKind.getId();
    }

    public DoorKind getDoorKind() {
        return doorKind == null ? null : DoorKind.fromId(doorKind);
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getCd() {
        return cd;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}