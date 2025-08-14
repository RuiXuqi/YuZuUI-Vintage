package com.paulzzh.yuzu.init;

import com.paulzzh.yuzu.YuZuUI;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class InitSounds {
    public static SoundEvent YUZU_TITLE_MUSIC;
    public static SoundEvent YUZU_TITLE_BUTTON_CLICK;
    public static SoundEvent YUZU_TITLE_BUTTON_NEW_GAME;
    public static SoundEvent YUZU_TITLE_BUTTON_SELECT_WORLD;
    public static SoundEvent YUZU_TITLE_BUTTON_ON;
    public static SoundEvent YUZU_TITLE_BUTTON_OPTIONS;
    public static SoundEvent YUZU_TITLE_BUTTON_QUIT_GAME;
    public static SoundEvent YUZU_TITLE_SENREN;
    public static SoundEvent YUZU_TITLE_BUTTON_REALMS;
    public static SoundEvent YUZU_TITLE_BUTTON_MOD_LIST;
    public static SoundEvent YUZU_TITLE_BUTTON_SINGLEPLAYER;
    public static SoundEvent YUZU_TITLE_BUTTON_MUTIPLAYER;

    public static void registerSounds() {
        YUZU_TITLE_MUSIC = registerSound("yuzu_title_music");
        YUZU_TITLE_BUTTON_CLICK = registerSound("yuzu_title_button_click");
        YUZU_TITLE_BUTTON_NEW_GAME = registerSound("yuzu_title_button_new_game");
        YUZU_TITLE_BUTTON_SELECT_WORLD = registerSound("yuzu_title_button_select_world");
        YUZU_TITLE_BUTTON_ON = registerSound("yuzu_title_button_on");
        YUZU_TITLE_BUTTON_OPTIONS = registerSound("yuzu_title_button_options");
        YUZU_TITLE_BUTTON_QUIT_GAME = registerSound("yuzu_title_button_quit_game");
        YUZU_TITLE_SENREN = registerSound("yuzu_title_senren");
        YUZU_TITLE_BUTTON_REALMS = registerSound("yuzu_title_button_realms");
        YUZU_TITLE_BUTTON_MOD_LIST = registerSound("yuzu_title_button_mod_list");
        YUZU_TITLE_BUTTON_SINGLEPLAYER = registerSound("yuzu_title_button_singleplayer");
        YUZU_TITLE_BUTTON_MUTIPLAYER = registerSound("yuzu_title_button_mutiplayer");
    }

    private static SoundEvent registerSound(String name) {
        ResourceLocation location = new ResourceLocation(YuZuUI.MODID, name);
        SoundEvent event = new SoundEvent(location).setRegistryName(location);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }

}
