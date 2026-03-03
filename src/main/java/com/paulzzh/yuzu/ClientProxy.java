package com.paulzzh.yuzu;

import com.paulzzh.yuzu.config.Config;
import com.paulzzh.yuzu.integration.CMMIntegration;
import com.paulzzh.yuzu.integration.DWGIntegration;
import com.paulzzh.yuzu.sound.SoundRegister;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@SuppressWarnings("unused")
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        Config.init(event.getSuggestedConfigurationFile());
        CMMIntegration.init();
        DWGIntegration.init();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        SoundRegister.registerSounds();
    }
}
