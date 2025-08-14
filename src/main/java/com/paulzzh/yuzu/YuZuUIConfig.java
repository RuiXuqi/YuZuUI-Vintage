package com.paulzzh.yuzu;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = YuZuUI.MODID)
@Mod.EventBusSubscriber(modid = YuZuUI.MODID)
public class YuZuUIConfig {
    @Config.Comment({
        "启动时打印启动信息，留空以禁用。",
        "How shall I greet?"
    })
    @Config.Name("Greeting")
    public static String greeting = "Ciallo～(∠ · ω < )⌒★";

    @Config.Comment("背景音乐")
    @Config.Name("BGM")
    public static boolean bgm = true;

    @Config.Comment("“后日谈”按钮打开语言界面，而不是 Realms 界面。")
    @Config.Name("Replace Realms")
    public static boolean replaceRealms = true;

    @Config.Comment("语音")
    @Config.Name("Voice")
    public static boolean voice = true;

    @Config.Comment("直接退出游戏，而不是回到主菜单。")
    @Config.Name("Just Exit")
    public static boolean justExit = true;

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(YuZuUI.MODID)) {
            ConfigManager.sync(YuZuUI.MODID, Config.Type.INSTANCE);
        }
    }

}
