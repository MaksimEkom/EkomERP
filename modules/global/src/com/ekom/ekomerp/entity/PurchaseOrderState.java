package com.ekom.ekomerp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum PurchaseOrderState implements EnumClass<String> {

    draft("draft"),
    purchase("purchase");

    private String id;

    PurchaseOrderState(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PurchaseOrderState fromId(String id) {
        for (PurchaseOrderState at : PurchaseOrderState.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}