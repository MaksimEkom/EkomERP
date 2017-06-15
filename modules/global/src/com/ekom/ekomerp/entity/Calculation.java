package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.List;
import javax.persistence.OneToMany;

@NamePattern("%s|name")
@Table(name = "EKOMERP_CALCULATION")
@Entity(name = "ekomerp$Calculation")
public class Calculation extends StandardEntity {
    private static final long serialVersionUID = -1586564265695484585L;

    @Column(name = "NUMBER_", nullable = false)
    protected String number;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "calculation")
    protected List<CalculationMaterialLine> consumptionLine;

    @Column(name = "SELLING_PRICE_TOTAL")
    protected Double sellingPriceTotal;

    @Column(name = "AMOUNT_TAX")
    protected Double amountTax;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "calculation")
    protected List<CalculationLaboriousnessLine> laboriousLine;

    @Column(name = "SELLING_PRICE_UNTAXED")
    protected Double sellingPriceUntaxed;

    @Column(name = "PROFIT")
    protected Double profit;

    @Column(name = "COST_PRICE")
    protected Double costPrice;

    public void setAmountTax(Double amountTax) {
        this.amountTax = amountTax;
    }

    public Double getAmountTax() {
        return amountTax;
    }

    public void setLaboriousLine(List<CalculationLaboriousnessLine> laboriousLine) {
        this.laboriousLine = laboriousLine;
    }

    public List<CalculationLaboriousnessLine> getLaboriousLine() {
        return laboriousLine;
    }

    public void setSellingPriceUntaxed(Double sellingPriceUntaxed) {
        this.sellingPriceUntaxed = sellingPriceUntaxed;
    }

    public Double getSellingPriceUntaxed() {
        return sellingPriceUntaxed;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Double getProfit() {
        return profit;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getCostPrice() {
        return costPrice;
    }


    public void setSellingPriceTotal(Double sellingPriceTotal) {
        this.sellingPriceTotal = sellingPriceTotal;
    }

    public Double getSellingPriceTotal() {
        return sellingPriceTotal;
    }


    public void setConsumptionLine(List<CalculationMaterialLine> consumptionLine) {
        this.consumptionLine = consumptionLine;
    }

    public List<CalculationMaterialLine> getConsumptionLine() {
        return consumptionLine;
    }


    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }


}