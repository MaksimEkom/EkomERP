package com.ekom.ekomerp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum Master implements EnumClass<Integer> {

    miholap(1),
    klevko(2),
    glinski(3),
    bezruchenko(4),
    baran(5);

    private Integer id;

    Master(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static Master fromId(Integer id) {
        for (Master at : Master.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}