package com.stylefeng.guns.modular.system.model;


public enum DictEnum {
    YES(1, "yes"),
    NO(0, "no");

    int num;
    String name;

    DictEnum(int num, String name) {
        this.num = num;
        this.name = name;
    }

    public static String valueOf(Integer type) {
        if (type == null) {
            return null;
        } else {
            for (DictEnum s : DictEnum.values()) {
                if (s.num == type) {
                    return s.name;
                }
            }
            return null;
        }
    }

    public int value() {
        return this.num;
    }
}
