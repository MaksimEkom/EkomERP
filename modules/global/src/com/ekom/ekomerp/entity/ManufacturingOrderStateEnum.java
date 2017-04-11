package com.ekom.ekomerp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ManufacturingOrderStateEnum implements EnumClass<Integer> {

    open(1),
    closed(2);

    private Integer id;

    ManufacturingOrderStateEnum(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static ManufacturingOrderStateEnum fromId(Integer id) {
        for (ManufacturingOrderStateEnum at : ManufacturingOrderStateEnum.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}