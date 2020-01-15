package com.stylefeng.guns.modular.system.model;


public enum RoleID {
    PROXY(8);

    int id;

    RoleID(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
