package com.paulzzh.yuzu;

import com.paulzzh.yuzu.sound.InitSounds;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MOD_ID, version = Tags.VERSION, name = Tags.MOD_NAME, dependencies = "required-after:mixinbooter", clientSideOnly = true, acceptableRemoteVersions = "*")
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
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {
        InitSounds.registerSounds();
    }

    @SubscribeEvent
    public void onClientConnectedToServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        inGamed = true;
        exit = false;
    }

}
