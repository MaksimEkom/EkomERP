package com.ekom.ekomerp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum InvoiceTypeEnum implements EnumClass<String> {

    in("in"),
    out("out");

    private String id;

    InvoiceTypeEnum(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static InvoiceTypeEnum fromId(String id) {
        for (InvoiceTypeEnum at : InvoiceTypeEnum.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}