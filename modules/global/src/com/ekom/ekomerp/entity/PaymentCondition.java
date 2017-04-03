package com.ekom.ekomerp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|condition")
@Table(name = "EKOMERP_PAYMENT_CONDITION")
@Entity(name = "ekomerp$PaymentCondition")
public class PaymentCondition extends StandardEntity {
    private static final long serialVersionUID = -7512948264212754430L;

    @Column(name = "CONDITION_", length = 400)
    protected String condition;

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }


}