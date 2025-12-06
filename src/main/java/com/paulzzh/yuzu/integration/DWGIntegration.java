package com.paulzzh.yuzu.integration;

import com.fireball1725.defaultworldgenerator.gui.GuiCreateCustomWorld;
import cpw.mods.fml.common.Optional;
import net.minecraft.client.gui.GuiScreen;

import javax.annotation.Nonnull;

public class DWGIntegration {
    public static final String DWG_MODID = "defaultworldgenerator";

    @Nonnull
    @Optional.Method(modid = DWG_MODID)
    public static GuiScreen getDWGGui(GuiScreen currentScreen) {
        return new GuiCreateCustomWorld(currentScreen);
    }
}
