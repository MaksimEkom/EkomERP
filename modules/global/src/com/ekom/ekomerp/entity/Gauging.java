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
    protected Integer width;

    @Column(name = "HEIGHT")
    protected Integer height;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }


    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }



    public void setDoorOrderNumber(String doorOrderNumber) {
        this.doorOrderNumber = doorOrderNumber;
    }

    public String getDoorOrderNumber() {
        return doorOrderNumber;
    }


}