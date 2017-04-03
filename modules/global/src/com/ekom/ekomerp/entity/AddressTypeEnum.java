package com.ekom.ekomerp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum AddressTypeEnum implements EnumClass<Integer> {

    contact(1),
    invoice(2),
    delivery(3),
    other(4);

    private Integer id;

    AddressTypeEnum(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static AddressTypeEnum fromId(Integer id) {
        for (AddressTypeEnum at : AddressTypeEnum.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}