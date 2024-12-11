package com.paulzzh.yuzu.config;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.SimpleGuiConfig;
import net.minecraft.client.gui.GuiScreen;

public class YuZuUIGuiConfig extends SimpleGuiConfig {
    public YuZuUIGuiConfig(GuiScreen parent) throws ConfigException {
        super(parent, YuZuUIConfig.class, "yuzu", "YuZuUI");
    }
}
