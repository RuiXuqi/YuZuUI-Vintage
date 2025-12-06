package com.paulzzh.yuzu;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = YuZuUI.MOD_ID, version = Tags.VERSION, name = YuZuUI.MOD_NAME, acceptableRemoteVersions = "*",
    guiFactory = "com.paulzzh.yuzu.YuZuUIConfigGuiFactory", dependencies = "required-after:unimixins")
public class YuZuUI {

    public static final String MOD_NAME = "YuZuUI";
    public static final String MOD_ID = "yuzu";
    public static final Logger LOG = LogManager.getLogger(YuZuUI.MOD_NAME);

    @SidedProxy(clientSide = "com.paulzzh.yuzu.ClientProxy", serverSide = "com.paulzzh.yuzu.CommonProxy")
    public static CommonProxy proxy;
    /**
     * 是否已经展示过 UI。第一次等待时间较长。
     */
    public static boolean isShowed = false;
    /**
     * 是否在再次打开 UI 前进行了游戏。防止 UI 反复加载。
     */
    public static boolean inGamed = false;
    /**
     * 是否已经手动退出。退出之后就不会自动打开 UI 了。
     */
    public static boolean exit = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}
