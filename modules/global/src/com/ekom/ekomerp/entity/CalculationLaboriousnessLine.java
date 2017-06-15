package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.Column;

@Table(name = "EKOMERP_CALCULATION_LABORIOUSNESS_LINE")
@Entity(name = "ekomerp$CalculationLaboriousnessLine")
public class CalculationLaboriousnessLine extends StandardEntity {
    private static final long serialVersionUID = -6245280534671131071L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CALCULATION_ID")
    protected Calculation calculation;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATION_ID")
    protected Operation operation;

    @Column(name = "VALUE_")
    protected Double value;

    @Column(name = "PRICE")
    protected Double price;

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }


    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }

    public Calculation getCalculation() {
        return calculation;
    }


}