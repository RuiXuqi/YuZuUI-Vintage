package com.paulzzh.yuzu.config;

import com.paulzzh.yuzu.Tags;
import com.paulzzh.yuzu.YuZuUIConfig;
import com.paulzzh.yuzu.config.util.ConfigBuilder;
import com.paulzzh.yuzu.config.util.IFormatter;
import com.paulzzh.yuzu.sound.SoundManager;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.List;

public final class Config {
    private static Configuration config;

    public static void init(File configFile) {
        if (config != null) throw new IllegalStateException("Init have been performed!");
        config = new Configuration(configFile);
        readFromFile();
    }

    public static void readFromFile() {
        build(ConfigBuilder.startReadingFromFile(config));
    }

    public static void readFromProp() {
        build(ConfigBuilder.startReadingFromProp(config));
    }

    public static void save() {
        build(ConfigBuilder.startSaving(config));
    }

    private static void build(@Nonnull ConfigBuilder builder) {
        builder.setLangKeyPrefix("yuzu.config");
        builder.setLangKeyFormatter(IFormatter.CAMEL_TO_SNAKE);

        builder.pushCategory(Configuration.CATEGORY_GENERAL, null, null);
        YuZuUIConfig.build(builder);
        builder.popCategoryWithoutLangKey();

        builder.finishBuilding();
    }

    public static Configuration getConfig() {
        return config;
    }

    @Nonnull
    public static List<IConfigElement> getRootConfigElements() {
        return new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements();
    }

    @Mod.EventBusSubscriber(value = Side.CLIENT, modid = Tags.MOD_ID)
    public static class ConfigSyncEvent {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Tags.MOD_ID)) {
                Config.readFromProp();
                SoundManager.updateCharacterStatus();
            }
        }
    }
}
