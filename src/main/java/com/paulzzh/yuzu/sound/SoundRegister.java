package com.paulzzh.yuzu.sound;

import com.paulzzh.yuzu.Tags;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * 注册声音。可直接取到的声音事件都在这里。
 */
public class SoundRegister {
    public static MusicTicker.MusicType YUZU_TITLE_TYPE;

    public static SoundEvent YUZU_TITLE_MUSIC;
    public static SoundEvent YUZU_TITLE_BUTTON_CLICK;
    public static SoundEvent YUZU_TITLE_BUTTON_MULTIPLAYER;
    public static SoundEvent YUZU_TITLE_BUTTON_NEW_GAME;
    public static SoundEvent YUZU_TITLE_BUTTON_ON;
    public static SoundEvent YUZU_TITLE_BUTTON_SINGLEPLAYER;

    public static void registerSounds() {
        YUZU_TITLE_MUSIC = registerSound("music");
        YUZU_TITLE_TYPE = EnumHelper.addEnum(
            MusicTicker.MusicType.class,
            "YUZU_TITLE",
            new Class[]{SoundEvent.class, int.class, int.class},
            YUZU_TITLE_MUSIC, 0, 0
        );

        YUZU_TITLE_BUTTON_CLICK = registerSound("button_click");
        YUZU_TITLE_BUTTON_MULTIPLAYER = registerSound("button_multiplayer");
        YUZU_TITLE_BUTTON_NEW_GAME = registerSound("button_new_game");
        YUZU_TITLE_BUTTON_ON = registerSound("button_on");
        YUZU_TITLE_BUTTON_SINGLEPLAYER = registerSound("button_singleplayer");
        SoundManager.init();
    }

    /**
     * 注册语音。
     *
     * @param name 简写的名字。会自动添加 yuzu_title_ 前缀。
     */
    public static SoundEvent registerSound(String name) {
        ResourceLocation location = new ResourceLocation(Tags.MOD_ID, "yuzu_title_" + name);
        SoundEvent event = new SoundEvent(location).setRegistryName(location);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}
