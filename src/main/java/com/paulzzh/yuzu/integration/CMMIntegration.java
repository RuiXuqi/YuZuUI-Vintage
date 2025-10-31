package com.paulzzh.yuzu.integration;

import lumien.custommainmenu.gui.GuiCustom;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nullable;

public class CMMIntegration {
    public static final String CMM_MODID = "custommainmenu";

    public static boolean isCMMMenu(@Nullable GuiScreen screen) {
        if (!Loader.isModLoaded(CMM_MODID)) return false;
        return innerIsCMMMenu(screen);
    }

    @Optional.Method(modid = CMM_MODID)
    private static boolean innerIsCMMMenu(@Nullable GuiScreen screen) {
        return screen instanceof GuiCustom;
    }
}
