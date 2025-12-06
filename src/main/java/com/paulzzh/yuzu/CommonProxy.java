package com.paulzzh.yuzu;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        YuZuUI.LOG.warn("YuZuUI is a client side only mod. Stop loading...");
    }

    public void init(FMLInitializationEvent event) {
    }
}
