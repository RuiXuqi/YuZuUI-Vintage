package com.paulzzh.yuzu.sound;

public enum Character {
    LENA("lena"),
    MAKO("mako"),
    MURASAME("murasame"),
    YOSHINO("yoshino"),
    KOHARU("koharu"),
    ROKA("roka"),
    RENTAROU("rentarou"),
    MIZUHA("mizuha"),
    YASUHARU("yasuharu"),
    GENJUROU("genjurou");

    private final String key;

    Character(String key) {
        this.key = key;
    }

    public String toString() {
        return this.key;
    }
}
