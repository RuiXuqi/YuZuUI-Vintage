package com.paulzzh.yuzu;

import com.paulzzh.yuzu.gui.screen.SenrenBankaTitleScreen;
import com.paulzzh.yuzu.sound.SoundManager;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Tags.MOD_ID)
@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class YuZuUIConfig {
    private static final String PREFIX = Tags.MOD_ID + ".config.";

    @Config.Comment("启动时打印启动信息，留空以禁用。")
    @Config.LangKey(PREFIX + "greeting")
    public static String greeting = "Ciallo～(∠ · ω < )⌒★";

    @Config.Comment("是否播放背景音乐。")
    @Config.LangKey(PREFIX + "bgm")
    public static boolean bgm = true;

    @Config.Comment("是否显示工具提示。")
    @Config.LangKey(PREFIX + "tooltip")
    public static boolean tooltip = false;

    @Config.Comment("“后日谈”按钮打开语言设置界面，而不是 Realms 界面。")
    @Config.LangKey(PREFIX + "replace_realms")
    public static boolean replaceRealms = true;

    @Config.Comment("直接退出游戏，而不是回到标题界面。")
    @Config.LangKey(PREFIX + "just_exit")
    public static boolean justExit = true;

    /**
     * 不应该直接调用！使用 SoundManager.getIsVoiceAvailable() 。
     */
    @Deprecated
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Config.Comment("点击按钮时播放语音。")
    @Config.LangKey(PREFIX + "voice")
    public static boolean voice = true;

    @Config.Comment("在下段语音播放前停止正播放的语音。")
    @Config.LangKey(PREFIX + "prevent_mixing_voice")
    public static boolean preventMixingVoice = true;

    private static final String VOICE_LIST_KEY = PREFIX + "voice_list";
    @Config.LangKey(VOICE_LIST_KEY)
    public static final VoiceList VoiceList = new VoiceList();

    public static class VoiceList {
        @Config.LangKey(VOICE_LIST_KEY + ".lena")
        public boolean lena = false;
        @Config.LangKey(VOICE_LIST_KEY + ".mako")
        public boolean mako = false;
        @Config.LangKey(VOICE_LIST_KEY + ".murasame")
        public boolean murasame = true;
        @Config.LangKey(VOICE_LIST_KEY + ".yoshino")
        public boolean yoshino = false;
        @Config.LangKey(VOICE_LIST_KEY + ".koharu")
        public boolean koharu = false;
        @Config.LangKey(VOICE_LIST_KEY + ".roka")
        public boolean roka = false;
        @Config.LangKey(VOICE_LIST_KEY + ".rentarou")
        public boolean rentarou = false;
        @Config.LangKey(VOICE_LIST_KEY + ".mizuha")
        public boolean mizuha = false;
        @Config.LangKey(VOICE_LIST_KEY + ".yasuharu")
        public boolean yasuharu = false;
        @Config.LangKey(VOICE_LIST_KEY + ".genjurou")
        public boolean genjurou = false;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Tags.MOD_ID)) {
            ConfigManager.sync(Tags.MOD_ID, Config.Type.INSTANCE);
            SoundManager.updateCharacterStatus();
            if (!bgm) {
                SenrenBankaTitleScreen.stopBGM();
            }
        }
    }
}
