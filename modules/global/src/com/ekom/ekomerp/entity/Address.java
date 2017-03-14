package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import javax.persistence.Lob;

@Table(name = "EKOMERP_ADDRESS")
@Entity(name = "ekomerp$Address")
public class Address extends StandardEntity {
    private static final long serialVersionUID = 7634960569842558601L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "TYPE_", nullable = false)
    protected Integer type;

    @Column(name = "POSITION_")
    protected String position;

    @Column(name = "EMAIL")
    protected String email;

    @Column(name = "PHONE")
    protected String phone;

    @Column(name = "MOBILE")
    protected String mobile;

    @Lob
    @Column(name = "NOTES")
    protected String notes;

    @Column(name = "STREET")
    protected String street;

    @Column(name = "CITY")
    protected String city;

    @Column(name = "REGION")
    protected String region;

    @Column(name = "ZIP")
    protected String zip;

    @Column(name = "COUNTRY")
    protected String country;

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
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

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
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

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }


    public AddressEnum getType() {
        return type == null ? null : AddressEnum.fromId(type);
    }

    public void setType(AddressEnum type) {
        this.type = type == null ? null : type.getId();
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}