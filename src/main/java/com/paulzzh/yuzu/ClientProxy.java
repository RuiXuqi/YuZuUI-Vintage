package com.paulzzh.yuzu;

import com.paulzzh.yuzu.sound.SoundRegister;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        if (!YuZuUIConfig.greeting.isEmpty()) {
            YuZuUI.LOG.info(YuZuUIConfig.greeting);
            YuZuUI.LOG.info("I am YuZuUI at version " + Tags.VERSION);
        }
        YuZuUIConfig.init(event.getSuggestedConfigurationFile());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        SoundRegister.init();
        MinecraftForge.EVENT_BUS.register(new YuZuUIEventHandler());
        FMLCommonHandler.instance().bus().register(new YuZuUIConfigHandler());
    }
}
