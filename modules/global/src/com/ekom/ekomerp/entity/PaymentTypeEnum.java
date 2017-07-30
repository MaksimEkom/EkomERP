package com.ekom.ekomerp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum PaymentTypeEnum implements EnumClass<String> {

    Inbound("inbound"),
    Outbound("outbound");

    private String id;

    PaymentTypeEnum(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PaymentTypeEnum fromId(String id) {
        for (PaymentTypeEnum at : PaymentTypeEnum.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}