package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "EKOMERP_UNIT")
@Entity(name = "ekomerp$Unit")
public class Unit extends StandardEntity {
    private static final long serialVersionUID = -7670127421961509397L;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "CODE")
    protected Integer code;

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}