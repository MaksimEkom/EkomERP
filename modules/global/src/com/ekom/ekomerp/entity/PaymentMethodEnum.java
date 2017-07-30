package com.ekom.ekomerp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum PaymentMethodEnum implements EnumClass<String> {

    Bank("bank"),
    Cash("cash");

    private String id;

    PaymentMethodEnum(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PaymentMethodEnum fromId(String id) {
        for (PaymentMethodEnum at : PaymentMethodEnum.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}