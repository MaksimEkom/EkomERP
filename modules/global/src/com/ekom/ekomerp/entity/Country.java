package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "EKOMERP_COUNTRY")
@Entity(name = "ekomerp$Country")
public class Country extends StandardEntity {
    private static final long serialVersionUID = -4657576374565189548L;

    @Column(name = "CODE", nullable = false)
    protected String code;

    @Column(name = "NAME")
    protected String name;

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


}