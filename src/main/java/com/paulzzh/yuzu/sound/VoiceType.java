package com.paulzzh.yuzu.sound;

public enum VoiceType {
    SENREN("senren"),
    MOD_LIST("button_mod_list"),
    OPTIONS("button_options"),
    QUIT_GAME("button_quit_game"),
    REALMS("button_realms"),
    SELECT_WORLD("button_select_world");

    private final String key;

    VoiceType(String key) {
        this.key = key;
    }

    public String toString() {
        return this.key;
    }
}
