package com.paulzzh.yuzu;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class YuZuUIConfigGui extends GuiConfig {
    public YuZuUIConfigGui(GuiScreen parent) {
        //noinspection unchecked,rawtypes
        super(
            parent,
            new ConfigElement(YuZuUIConfig.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
            YuZuUI.MOD_ID,
            false, false,
            YuZuUI.MOD_NAME
        );
    }
}
