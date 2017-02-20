package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "EKOMERP_OPERATION")
@Entity(name = "ekomerp$Operation")
public class Operation extends StandardEntity {
    private static final long serialVersionUID = -8388686207172188758L;

    @Column(name = "CODE")
    protected String code;

    @Column(name = "NAME", nullable = false)
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