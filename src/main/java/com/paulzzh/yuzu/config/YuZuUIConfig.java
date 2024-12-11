package com.paulzzh.yuzu.config;

import com.gtnewhorizon.gtnhlib.config.Config;

@Config(modid = "yuzu")
public class YuZuUIConfig {
    @Config.Comment("How shall I greet?")
    @Config.DefaultString("Hello World")
    @Config.RequiresMcRestart
    public static String greeting;
}
