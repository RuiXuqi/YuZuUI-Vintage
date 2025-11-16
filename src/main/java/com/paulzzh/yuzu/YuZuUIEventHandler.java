package com.paulzzh.yuzu;

import com.paulzzh.yuzu.gui.screen.SenrenBankaTitleScreen;
import com.paulzzh.yuzu.integration.CMMIntegration;
import com.paulzzh.yuzu.sound.SoundManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import javax.annotation.Nonnull;

public class YuZuUIEventHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void openGui(@Nonnull GuiOpenEvent event) {
        GuiScreen gui = event.getGui();
        if (
            (gui instanceof GuiMainMenu || CMMIntegration.isCMMMenu(gui))
                && !(Minecraft.getMinecraft().currentScreen instanceof SenrenBankaTitleScreen)
                && !YuZuUI.exit
        ) {
            event.setGui(new SenrenBankaTitleScreen());
        }
    }

    @SubscribeEvent
    public void onClientConnectedToServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        YuZuUI.inGamed = true;
        YuZuUI.exit = false;
    }

    @SubscribeEvent
    public static void onConfigChanged(@Nonnull ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Tags.MOD_ID)) {
            ConfigManager.sync(Tags.MOD_ID, Config.Type.INSTANCE);
            SoundManager.updateCharacterStatus();
        }
    }
}
