package com.paulzzh.yuzu;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.paulzzh.yuzu.gui.screen.SenrenBankaTitleScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.resource.IResourceType;
import net.minecraftforge.client.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.client.resource.VanillaResourceType;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;

public class YuZuUIReloadListener implements ISelectiveResourceReloadListener {
    public static final YuZuUIReloadListener INSTANCE = new YuZuUIReloadListener();
    private static final ResourceLocation JSON_RES = new ResourceLocation(Tags.MOD_ID, "screen.json");
    private static final Gson GSON = new Gson();

    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager manager, @Nonnull Predicate<IResourceType> predicate) {
        if (predicate.test(VanillaResourceType.TEXTURES)) {
            try {
                IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(JSON_RES);
                try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
                    try {
                        SenrenBankaTitleScreen.updateJson(GSON.fromJson(reader, JsonObject.class));
                        YuZuUI.isAvailable = true;
                        YuZuUI.inGamed = true; // 重载 UI
                    } catch (JsonParseException e) {
                        YuZuUI.LOG.error("Invalid {}.", JSON_RES.toString(), e);
                    }
                }
            } catch (IOException e) {
                YuZuUI.LOG.error("Failed to read '{}'.", JSON_RES.toString(), e);
                YuZuUI.isAvailable = false;
            }
        }
    }
}
