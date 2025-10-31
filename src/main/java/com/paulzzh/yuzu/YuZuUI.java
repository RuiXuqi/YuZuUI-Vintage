package com.paulzzh.yuzu;

import com.paulzzh.yuzu.integration.DWGIntegration;
import com.paulzzh.yuzu.sound.InitSounds;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MOD_ID, version = Tags.VERSION, name = Tags.MOD_NAME, clientSideOnly = true, acceptableRemoteVersions = "*",
    dependencies = "required-after:mixinbooter@[8.0,);after:" + DWGIntegration.DWG_MODID)
public class YuZuUI {

    public static final Logger LOG = LogManager.getLogger(Tags.MOD_NAME);
    /**
     * 是否已经展示过 UI。第一次等待时间较长。
     */
    public static boolean isShowed = false;
    /**
     * 是否在再次打开 UI 前进行了游戏。防止 UI 反复加载。
     */
    public static boolean inGamed = false;
    public static boolean exit = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (!YuZuUIConfig.greeting.isEmpty()) {
            YuZuUI.LOG.info(YuZuUIConfig.greeting);
            YuZuUI.LOG.info("I am YuZuUI at version " + Tags.VERSION);
        }
        MinecraftForge.EVENT_BUS.register(new YuZuUIEventHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        InitSounds.registerSounds();
    }
}
