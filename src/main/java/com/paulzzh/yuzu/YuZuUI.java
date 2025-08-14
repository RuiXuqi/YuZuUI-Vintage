package com.paulzzh.yuzu;

import com.paulzzh.yuzu.init.InitSounds;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

@Mod(modid = YuZuUI.MODID, version = Tags.VERSION, name = "YuZuUI", dependencies = "required-after:mixinbooter")
public class YuZuUI {

    public static final String MODID = "yuzu";
    public static final Logger LOG = LogManager.getLogger(MODID);
    public static boolean inGamed = false;
    public static boolean exit = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (!Objects.equals(YuZuUIConfig.greeting, "")){
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
