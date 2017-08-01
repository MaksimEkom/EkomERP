package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.Lob;

@NamePattern("%s|name")
@Table(name = "EKOMERP_PARTNER")
@Entity(name = "ekomerp$Partner")
public class Partner extends StandardEntity {
    private static final long serialVersionUID = -6811009431462172286L;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "PARTNER_TYPE")
    protected Integer partnerType = 1;

    @Column(name = "CUSTOMER")
    protected Boolean customer;

    @Column(name = "VENDOR")
    protected Boolean vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMAGE_ID")
    protected FileDescriptor image;

    @Column(name = "EMAIL")
    protected String email;

    @Column(name = "WEBSITE")
    protected String website;

    @Column(name = "ACTIVE")
    protected Boolean active = true;

    @Column(name = "POSITION_")
    protected String position;

    @Column(name = "PHONE")
    protected String phone;

    @Column(name = "MOBILE")
    protected String mobile;

    @Column(name = "FAX")
    protected String fax;

    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    protected Partner parent;

    @Column(name = "STREET")
    protected String street;

    @Column(name = "CITY")
    protected String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGION_ID")
    protected Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID")
    protected Country country;

    @Column(name = "ZIP")
    protected String zip;




    @Column(name = "ADDRESS_TYPE")
    protected Integer addressType = 1;


    @Lob
    @Column(name = "NOTES")
    protected String notes;

    @Column(name = "UNP")
    protected String unp;

    @Column(name = "FULL_NAME")
    protected String fullName;

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }


    public void setUnp(String unp) {
        this.unp = unp;
    }

    public String getUnp() {
        return unp;
    }


    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }


    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }


    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }


    public AddressTypeEnum getAddressType() {
        return addressType == null ? null : AddressTypeEnum.fromId(addressType);
    }

    public void setAddressType(AddressTypeEnum addressType) {
        this.addressType = addressType == null ? null : addressType.getId();
    }


    public PartnerTypeEnum getPartnerType() {
        return partnerType == null ? null : PartnerTypeEnum.fromId(partnerType);
    }

    public void setPartnerType(PartnerTypeEnum partnerType) {
        this.partnerType = partnerType == null ? null : partnerType.getId();
    }






    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFax() {
        return fax;
    }

    public void setParent(Partner parent) {
        this.parent = parent;
    }

    public Partner getParent() {
        return parent;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }


    public FileDescriptor getImage() {
        return image;
    }

    public void setImage(FileDescriptor image) {
        this.image = image;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCustomer(Boolean customer) {
        this.customer = customer;
    }

    public Boolean getCustomer() {
        return customer;
    }

    public void setVendor(Boolean vendor) {
        this.vendor = vendor;
    }

    public Boolean getVendor() {
        return vendor;
    }


}