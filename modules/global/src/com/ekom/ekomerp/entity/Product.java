package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.StandardEntity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.Set;
import javax.persistence.OneToMany;
import com.haulmont.cuba.core.entity.FileDescriptor;

@NamePattern("%s|name")
@Table(name = "EKOMERP_PRODUCT")
@Entity(name = "ekomerp$Product")
public class Product extends StandardEntity {
    private static final long serialVersionUID = -5315324208159873998L;

    @Column(name = "CODE", nullable = false)
    protected String code;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIT_ID")
    protected Unit unit;

    @Column(name = "FULL_NAME")
    protected String fullName;

    @Column(name = "DOC_NAME")
    protected String docName;

    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    @Column(name = "HEIGHT")
    protected Integer height;

    @Column(name = "WIDTH")
    protected Integer width;

    @Column(name = "DEPTH")
    protected Integer depth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    protected Category category;

    @Column(name = "ACTIVE")
    protected Boolean active = true;

    @Column(name = "ANALOG")
    protected String analog;

    @Column(name = "WEIGHT")
    protected Double weight;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "product")
    protected Set<Laboriousness> laboriousness;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "product")
    protected Set<Consumption> consumption;

    @Column(name = "FOR_SALE")
    protected Boolean for_sale;

    @Column(name = "FOR_PURCHASE")
    protected Boolean for_purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMAGE_ID")
    protected FileDescriptor image;

    @Lob
    @Column(name = "NOTES")
    protected String notes;

    @Column(name = "PURCHASE_PRICE")
    protected Double purchasePrice = 0.0;

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }


    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    public void setImage(FileDescriptor image) {
        this.image = image;
    }

    public FileDescriptor getImage() {
        return image;
    }


    public void setFor_sale(Boolean for_sale) {
        this.for_sale = for_sale;
    }

    public Boolean getFor_sale() {
        return for_sale;
    }

    public void setFor_purchase(Boolean for_purchase) {
        this.for_purchase = for_purchase;
    }

    public Boolean getFor_purchase() {
        return for_purchase;
    }


    public void setConsumption(Set<Consumption> consumption) {
        this.consumption = consumption;
    }

    public Set<Consumption> getConsumption() {
        return consumption;
    }


    public void setLaboriousness(Set<Laboriousness> laboriousness) {
        this.laboriousness = laboriousness;
    }

    public Set<Laboriousness> getLaboriousness() {
        return laboriousness;
    }


    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocName() {
        return docName;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getHeight() {
        return height;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getWidth() {
        return width;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setAnalog(String analog) {
        this.analog = analog;
    }

    public String getAnalog() {
        return analog;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }


}