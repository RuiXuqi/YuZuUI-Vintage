package com.paulzzh.yuzu.sound;

import com.paulzzh.yuzu.YuZuUI;
import net.minecraft.util.ResourceLocation;

public class SoundRegister {
    public static ResourceLocation YUZU_TITLE_MUSIC;
    public static ResourceLocation YUZU_TITLE_BUTTON_CLICK;
    public static ResourceLocation YUZU_TITLE_BUTTON_MULTIPLAYER;
    public static ResourceLocation YUZU_TITLE_BUTTON_NEW_GAME;
    public static ResourceLocation YUZU_TITLE_BUTTON_ON;
    public static ResourceLocation YUZU_TITLE_BUTTON_SINGLEPLAYER;

    public static void init() {
        YUZU_TITLE_MUSIC = getSoundLocation("music");
        YUZU_TITLE_BUTTON_CLICK = getSoundLocation("button_click");
        YUZU_TITLE_BUTTON_MULTIPLAYER = getSoundLocation("button_multiplayer");
        YUZU_TITLE_BUTTON_NEW_GAME = getSoundLocation("button_new_game");
        YUZU_TITLE_BUTTON_ON = getSoundLocation("button_on");
        YUZU_TITLE_BUTTON_SINGLEPLAYER = getSoundLocation("button_singleplayer");
        SoundManager.init();
    }

    /**
     * @param name 简写的名字。会自动添加 yuzu_title_ 前缀。
     */
    public static ResourceLocation getSoundLocation(String name) {
        return new ResourceLocation(YuZuUI.MOD_ID, "yuzu_title_" + name);
    }
}
