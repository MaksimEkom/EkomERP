package com.ekom.ekomerp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum PartnerTypeEnum implements EnumClass<Integer> {

    company(1),
    individual(2);

    private Integer id;

    PartnerTypeEnum(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static PartnerTypeEnum fromId(Integer id) {
        for (PartnerTypeEnum at : PartnerTypeEnum.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}