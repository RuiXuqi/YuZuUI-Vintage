package com.paulzzh.yuzu.config;

import com.paulzzh.yuzu.Tags;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig {
    public ConfigGui(GuiScreen parent) {
        super(
                parent,
                Config.getRootConfigElements(),
                Tags.MOD_ID,
                false, false,
                Tags.MOD_NAME
        );
    }
}
