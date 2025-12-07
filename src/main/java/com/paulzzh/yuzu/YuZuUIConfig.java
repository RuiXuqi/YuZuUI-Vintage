package com.paulzzh.yuzu;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class YuZuUIConfig {
    public static String greeting = "Ciallo～(∠ · ω < )⌒★";
    public static boolean bgm = true;
    public static boolean tooltip = false;
    public static boolean replaceRealms = true;
    public static boolean justExit = true;
    /**
     * 不应该直接调用！使用 {@link com.paulzzh.yuzu.sound.SoundManager#getIsVoiceAvailable(Minecraft)}。
     */
    @Deprecated
    @SuppressWarnings("DeprecatedIsStillUsed")
    public static boolean voice = true;
    public static boolean preventMixingVoice = true;

    public static final VoiceList VoiceList = new VoiceList();

    public static class VoiceList {
        public boolean lena = false;
        public boolean mako = false;
        public boolean murasame = true;
        public boolean yoshino = false;
        public boolean koharu = false;
        public boolean roka = false;
        public boolean rentarou = false;
        public boolean mizuha = false;
        public boolean yasuharu = false;
        public boolean genjurou = false;
    }

    private static Configuration config;
    private static final String PREFIX = YuZuUI.MOD_ID + ".config.";
    private static final String VOICE_LIST_KEY = PREFIX + "voice_list";

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            config.load();
        }
        syncConfig();
    }

    public static void syncConfig() {
        greeting = config.get(
            Configuration.CATEGORY_GENERAL,
            "greeting",
            greeting,
            "启动时打印启动信息，留空以禁用。"
        ).setLanguageKey(PREFIX + "greeting").getString();

        bgm = config.get(
            Configuration.CATEGORY_GENERAL,
            "bgm",
            bgm,
            "是否播放背景音乐。"
        ).setLanguageKey(PREFIX + "bgm").getBoolean();

        tooltip = config.get(
            Configuration.CATEGORY_GENERAL,
            "tooltip",
            tooltip,
            "是否显示工具提示。"
        ).setLanguageKey(PREFIX + "tooltip").getBoolean();

        replaceRealms = config.get(
            Configuration.CATEGORY_GENERAL,
            "replaceRealms",
            replaceRealms,
            "“后日谈”按钮打开语言设置界面，而不是 Realms 界面。"
        ).setLanguageKey(PREFIX + "replace_realms").getBoolean();

        justExit = config.get(
            Configuration.CATEGORY_GENERAL,
            "justExit",
            justExit,
            "直接退出游戏，而不是回到标题界面。"
        ).setLanguageKey(PREFIX + "just_exit").getBoolean();

        voice = config.get(
            Configuration.CATEGORY_GENERAL,
            "voice",
            voice,
            "点击按钮时播放语音。"
        ).setLanguageKey(PREFIX + "voice").getBoolean();

        preventMixingVoice = config.get(
            Configuration.CATEGORY_GENERAL,
            "preventMixingVoice",
            preventMixingVoice,
            "在下段语音播放前停止正播放的语音。"
        ).setLanguageKey(PREFIX + "prevent_mixing_voice").getBoolean();

        final String VOICE_LIST_CATEGORY = Configuration.CATEGORY_GENERAL + Configuration.CATEGORY_SPLITTER + "voicelist";
        config.setCategoryLanguageKey(VOICE_LIST_CATEGORY, VOICE_LIST_KEY);
        config.setCategoryComment(VOICE_LIST_CATEGORY, "被启用的语音的列表。");

        VoiceList.lena = config.get(
            VOICE_LIST_CATEGORY,
            "lena",
            VoiceList.lena
        ).setLanguageKey(VOICE_LIST_KEY + ".lena").getBoolean();

        VoiceList.mako = config.get(
            VOICE_LIST_CATEGORY,
            "mako",
            VoiceList.mako
        ).setLanguageKey(VOICE_LIST_KEY + ".mako").getBoolean();

        VoiceList.murasame = config.get(
            VOICE_LIST_CATEGORY,
            "murasame",
            VoiceList.murasame
        ).setLanguageKey(VOICE_LIST_KEY + ".murasame").getBoolean();

        VoiceList.yoshino = config.get(
            VOICE_LIST_CATEGORY,
            "yoshino",
            VoiceList.yoshino
        ).setLanguageKey(VOICE_LIST_KEY + ".yoshino").getBoolean();

        VoiceList.koharu = config.get(
            VOICE_LIST_CATEGORY,
            "koharu",
            VoiceList.koharu
        ).setLanguageKey(VOICE_LIST_KEY + ".koharu").getBoolean();

        VoiceList.roka = config.get(
            VOICE_LIST_CATEGORY,
            "roka",
            VoiceList.roka
        ).setLanguageKey(VOICE_LIST_KEY + ".roka").getBoolean();

        VoiceList.rentarou = config.get(
            VOICE_LIST_CATEGORY,
            "rentarou",
            VoiceList.rentarou
        ).setLanguageKey(VOICE_LIST_KEY + ".rentarou").getBoolean();

        VoiceList.mizuha = config.get(
            VOICE_LIST_CATEGORY,
            "mizuha",
            VoiceList.mizuha
        ).setLanguageKey(VOICE_LIST_KEY + ".mizuha").getBoolean();

        VoiceList.yasuharu = config.get(
            VOICE_LIST_CATEGORY,
            "yasuharu",
            VoiceList.yasuharu
        ).setLanguageKey(VOICE_LIST_KEY + ".yasuharu").getBoolean();

        VoiceList.genjurou = config.get(
            VOICE_LIST_CATEGORY,
            "genjurou",
            VoiceList.genjurou
        ).setLanguageKey(VOICE_LIST_KEY + ".genjurou").getBoolean();

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static Configuration getConfig() {
        return config;
    }
}
