package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import javax.persistence.Column;

@Table(name = "EKOMERP_CALCULATION_MATERIAL_LINE")
@Entity(name = "ekomerp$CalculationMaterialLine")
public class CalculationMaterialLine extends StandardEntity {
    private static final long serialVersionUID = 1050085269862815468L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CALCULATION_ID")
    protected Calculation calculation;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATERIAL_ID")
    protected Product material;

    @Column(name = "QUANTITY")
    protected Double quantity;

    @Column(name = "PRICE")
    protected Double price;

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }


    public void setMaterial(Product material) {
        this.material = material;
    }

    public Product getMaterial() {
        return material;
    }

    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }

    public Calculation getCalculation() {
        return calculation;
    }


}