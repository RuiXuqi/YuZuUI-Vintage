package com.paulzzh.yuzu.integration;

import lumien.custommainmenu.gui.GuiCustom;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nullable;

public class CMMIntegration {
    private static final String CMM = "custommainmenu";
    private static boolean CMM_LOADED = false;

    public static void init() {
        CMM_LOADED = Loader.isModLoaded(CMM);
    }

    public static boolean isCMMMenu(@Nullable GuiScreen screen) {
        return CMM_LOADED && innerIsCMMMenu(screen);
    }

    @Optional.Method(modid = CMM)
    private static boolean innerIsCMMMenu(@Nullable GuiScreen screen) {
        return screen instanceof GuiCustom;
    }
}
