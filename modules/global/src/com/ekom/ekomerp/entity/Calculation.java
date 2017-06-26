package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ekom.ekomerp.FourDigitsScaleBigDecimal;
import com.haulmont.chile.core.annotations.MetaProperty;
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
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;

@NamePattern("%s|name")
@Table(name = "EKOMERP_CALCULATION")
@Entity(name = "ekomerp$Calculation")
public class Calculation extends StandardEntity {
    private static final long serialVersionUID = -1586564265695484585L;

    @Column(name = "NUMBER_", nullable = false)
    protected String number = "Новый";

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @NotNull
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "open"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "calculation")
    protected List<CalculationMaterialLine> consumptionLine;

    @Column(name = "SELLING_PRICE_TOTAL")
    protected BigDecimal sellingPriceTotal;

    @Column(name = "AMOUNT_TAX")
    protected BigDecimal amountTax;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "calculation")
    protected List<CalculationLaboriousnessLine> laboriousLine;

    @Column(name = "SELLING_PRICE_UNTAXED")
    protected BigDecimal sellingPriceUntaxed;

    @Column(name = "PROFIT")
    protected BigDecimal profit;

    @Column(name = "COST_PRICE")
    protected BigDecimal costPrice;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_")
    protected Date date = new Date(System.currentTimeMillis());

    @Column(name = "FSZN_RATE")
    protected BigDecimal fsznRate = BigDecimal.valueOf(34.0);

    @Column(name = "BGS_RATE")
    protected BigDecimal bgsRate = BigDecimal.valueOf(0.47);

    @Column(name = "PRODUCTION_EXPENSES_RATE")
    protected BigDecimal productionExpensesRate = BigDecimal.valueOf(500.0);

    @Column(name = "COMMERCIAL_EXPENSES_RATE")
    protected BigDecimal commercialExpensesRate = BigDecimal.valueOf(0.0);

    @Column(name = "PROFIT_RATE")
    protected BigDecimal profitRate = BigDecimal.valueOf(20.0);

    @MetaProperty(datatype = FourDigitsScaleBigDecimal.NAME)
    @Column(name = "FSZN", precision = 19, scale = 4)
    protected BigDecimal fszn;

    @MetaProperty(datatype = FourDigitsScaleBigDecimal.NAME)
    @Column(name = "BGS", precision = 19, scale = 4)
    protected BigDecimal bgs;

    @Column(name = "PRODUCTION_EXPENSES")
    protected BigDecimal productionExpenses;


    @Column(name = "COMMERCIAL_EXPENSES")
    protected BigDecimal commercialExpenses;

    @MetaProperty(datatype = FourDigitsScaleBigDecimal.NAME)
    @Column(name = "MATERIAL_SUM")
    protected BigDecimal materialSum;

    @MetaProperty(datatype = FourDigitsScaleBigDecimal.NAME)
    @Column(name = "LABOR_SUM", precision = 19, scale = 3)
    protected BigDecimal laborSum;

    public BigDecimal getSellingPriceTotal() {
        return sellingPriceTotal;
    }

    public void setSellingPriceTotal(BigDecimal sellingPriceTotal) {
        this.sellingPriceTotal = sellingPriceTotal;
    }


    public BigDecimal getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(BigDecimal amountTax) {
        this.amountTax = amountTax;
    }


    public BigDecimal getSellingPriceUntaxed() {
        return sellingPriceUntaxed;
    }

    public void setSellingPriceUntaxed(BigDecimal sellingPriceUntaxed) {
        this.sellingPriceUntaxed = sellingPriceUntaxed;
    }


    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }


    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }


    public BigDecimal getFsznRate() {
        return fsznRate;
    }

    public void setFsznRate(BigDecimal fsznRate) {
        this.fsznRate = fsznRate;
    }


    public BigDecimal getBgsRate() {
        return bgsRate;
    }

    public void setBgsRate(BigDecimal bgsRate) {
        this.bgsRate = bgsRate;
    }


    public BigDecimal getProductionExpensesRate() {
        return productionExpensesRate;
    }

    public void setProductionExpensesRate(BigDecimal productionExpensesRate) {
        this.productionExpensesRate = productionExpensesRate;
    }


    public BigDecimal getCommercialExpensesRate() {
        return commercialExpensesRate;
    }

    public void setCommercialExpensesRate(BigDecimal commercialExpensesRate) {
        this.commercialExpensesRate = commercialExpensesRate;
    }


    public BigDecimal getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(BigDecimal profitRate) {
        this.profitRate = profitRate;
    }


    public BigDecimal getBgs() {
        return bgs;
    }

    public void setBgs(BigDecimal bgs) {
        this.bgs = bgs;
    }


    public BigDecimal getProductionExpenses() {
        return productionExpenses;
    }

    public void setProductionExpenses(BigDecimal productionExpenses) {
        this.productionExpenses = productionExpenses;
    }


    public BigDecimal getCommercialExpenses() {
        return commercialExpenses;
    }

    public void setCommercialExpenses(BigDecimal commercialExpenses) {
        this.commercialExpenses = commercialExpenses;
    }


    public BigDecimal getMaterialSum() {
        return materialSum;
    }

    public void setMaterialSum(BigDecimal materialSum) {
        this.materialSum = materialSum;
    }


    public BigDecimal getLaborSum() {
        return laborSum;
    }

    public void setLaborSum(BigDecimal laborSum) {
        this.laborSum = laborSum;
    }


    public BigDecimal getFszn() {
        return fszn;
    }

    public void setFszn(BigDecimal fszn) {
        this.fszn = fszn;
    }




    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }


    public void setLaboriousLine(List<CalculationLaboriousnessLine> laboriousLine) {
        this.laboriousLine = laboriousLine;
    }

    public List<CalculationLaboriousnessLine> getLaboriousLine() {
        return laboriousLine;
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