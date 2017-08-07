package com.ekom.ekomerp.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum DoorOrderStatus implements EnumClass<Integer> {

    open(1),
    measurement(2),
    done(3),
    production(4),
    manufactured(5),
    installation(6),
    finished(7);

    private Integer id;

    DoorOrderStatus(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static DoorOrderStatus fromId(Integer id) {
        for (DoorOrderStatus at : DoorOrderStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}