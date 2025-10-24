package com.paulzzh.yuzu.sound;

import com.paulzzh.yuzu.YuZuUIConfig;

import java.util.function.BooleanSupplier;

public enum Character {
    LENA("lena", () -> YuZuUIConfig.VoiceList.lena),
    MAKO("mako", () -> YuZuUIConfig.VoiceList.mako),
    MURASAME("murasame", () -> YuZuUIConfig.VoiceList.murasame),
    YOSHINO("yoshino", () -> YuZuUIConfig.VoiceList.yoshino),
    KOHARU("koharu", () -> YuZuUIConfig.VoiceList.koharu),
    ROKA("roka", () -> YuZuUIConfig.VoiceList.roka),
    RENTAROU("rentarou", () -> YuZuUIConfig.VoiceList.rentarou),
    MIZUHA("mizuha", () -> YuZuUIConfig.VoiceList.mizuha),
    YASUHARU("yasuharu", () -> YuZuUIConfig.VoiceList.yasuharu),
    GENJUROU("genjurou", () -> YuZuUIConfig.VoiceList.genjurou);

    private final String key;
    private final BooleanSupplier configSupplier;

    Character(String key, BooleanSupplier configSupplier) {
        this.key = key;
        this.configSupplier = configSupplier;
    }

    @Override
    public String toString() {
        return this.key;
    }

    public BooleanSupplier getConfigSupplier() {
        return this.configSupplier;
    }
}
