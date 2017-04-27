package com.ekom.ekomerp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum InvoiceStateEnum implements EnumClass<String> {

    open("open"),
    paid("paid");

    private String id;

    InvoiceStateEnum(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static InvoiceStateEnum fromId(String id) {
        for (InvoiceStateEnum at : InvoiceStateEnum.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}