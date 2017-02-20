package com.ekom.ekomerp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum StockmovementTypeEnum implements EnumClass<Integer> {

    in(1),
    out(2),
    relocation(3);

    private Integer id;

    StockmovementTypeEnum(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static StockmovementTypeEnum fromId(Integer id) {
        for (StockmovementTypeEnum at : StockmovementTypeEnum.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}