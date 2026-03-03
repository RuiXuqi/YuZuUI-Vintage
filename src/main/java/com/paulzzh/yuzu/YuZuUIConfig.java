package com.paulzzh.yuzu;

import com.paulzzh.yuzu.config.util.ConfigBuilder;
import com.paulzzh.yuzu.sound.Character;
import com.paulzzh.yuzu.sound.SoundManager;

import javax.annotation.Nonnull;

public class YuZuUIConfig {
    public static boolean bgm = true;
    public static boolean tooltip = false;
    public static boolean replaceRealms = true;
    public static boolean justExit = true;
    /// @deprecated 不应该直接调用！使用 {@link SoundManager#isVoiceAvailable()}。
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public static boolean voice = true;
    public static boolean preventMixingVoice = true;

    public static void build(@Nonnull ConfigBuilder builder) {
        bgm = builder.get(
                "bgm",
                bgm,
                "是否播放背景音乐。"
        );

        tooltip = builder.get(
                "tooltip",
                tooltip,
                "是否显示工具提示。"
        );

        replaceRealms = builder.get(
                "replaceRealms",
                replaceRealms,
                "“后日谈”按钮打开语言设置界面，而不是 Realms 界面。"
        );

        justExit = builder.get(
                "justExit",
                justExit,
                "直接退出游戏，而不是回到标题界面。"
        );

        voice = builder.get(
                "voice",
                voice,
                "点击按钮时播放语音。"
        );

        preventMixingVoice = builder.get(
                "preventMixingVoice",
                preventMixingVoice,
                "在下段语音播放前停止正播放的语音。"
        );

        builder.pushCategory("VoiceList", "被启用的语音的列表。");
        Character.buildConfig(builder);
        builder.popCategory();
    }
}
