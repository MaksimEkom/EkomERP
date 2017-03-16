package com.ekom.ekomerp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum AddressEnum implements EnumClass<Integer> {

    contact(1),
    invoice(2),
    delivery(3),
    other(4);

    private Integer id;

    AddressEnum(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static AddressEnum fromId(Integer id) {
        for (AddressEnum at : AddressEnum.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}