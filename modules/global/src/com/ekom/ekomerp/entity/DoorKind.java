package com.ekom.ekomerp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum DoorKind implements EnumClass<Integer> {

    fire(1),
    special(2),
    internal(3),
    external(4);

    private Integer id;

    DoorKind(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static DoorKind fromId(Integer id) {
        for (DoorKind at : DoorKind.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}