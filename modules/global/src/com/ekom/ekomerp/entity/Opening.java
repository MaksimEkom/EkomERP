package com.ekom.ekomerp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum Opening implements EnumClass<Integer> {

    right(1),
    left(2);

    private Integer id;

    Opening(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static Opening fromId(Integer id) {
        for (Opening at : Opening.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}