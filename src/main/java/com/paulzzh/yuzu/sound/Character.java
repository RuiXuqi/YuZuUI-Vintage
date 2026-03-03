package com.paulzzh.yuzu.sound;

import com.paulzzh.yuzu.config.util.ConfigBuilder;

import javax.annotation.Nonnull;
import java.util.Locale;

public enum Character {
    LENA,
    MAKO,
    MURASAME(true),
    YOSHINO,
    KOHARU,
    ROKA,
    RENTAROU,
    MIZUHA,
    YASUHARU,
    GENJUROU;

    private boolean enabled = false;

    Character(boolean enabled) {
        this.enabled = enabled;
    }

    Character() {
    }

    public String getKey() {
        return this.name().toLowerCase(Locale.ENGLISH);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public static void buildConfig(@Nonnull ConfigBuilder builder) {
        for (Character character : Character.values()) {
            character.enabled = builder.get(character.name().toLowerCase(Locale.ENGLISH), character.enabled);
        }
    }
}
