package com.paulzzh.yuzu;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = YuZuUI.MODID)
@Mod.EventBusSubscriber(modid = YuZuUI.MODID)
public class YuZuUIConfig {
    @Config.Comment("How shall I greet?")
    @Config.Name("Greeting")
    public static String greeting = "Ciallo～(∠ · ω < )⌒★";

    @Config.Comment("背景音乐")
    @Config.Name("BGM")
    public static boolean bgm = true;

    @Config.Comment("将后日谈按钮打开的界面改为语言切换界面")
    @Config.Name("Replace Realms")
    public static boolean replaceRealms = true;

    @Config.Comment("语音")
    @Config.Name("Voice")
    public static boolean voice = true;

    @Config.Comment("直接退出游戏？(否则返回到原主菜单)")
    @Config.Name("Just Exit")
    public static boolean justExit = false;

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(YuZuUI.MODID)) {
            ConfigManager.sync(YuZuUI.MODID, Config.Type.INSTANCE);
        }
    }

}
