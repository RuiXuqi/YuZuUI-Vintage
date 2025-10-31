package com.paulzzh.yuzu;

import com.paulzzh.yuzu.gui.screen.SenrenBankaTitleScreen;
import com.paulzzh.yuzu.integration.CMMIntegration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import javax.annotation.Nonnull;

public class YuZuUIEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void openGui(@Nonnull GuiOpenEvent event) {
        GuiScreen gui = event.getGui();
        if ((gui instanceof GuiMainMenu || CMMIntegration.isCMMMenu(gui))
            && Minecraft.getMinecraft().currentScreen != getInstance()
            && !YuZuUI.exit) {
            event.setGui(getInstance());
        }
    }

    @SubscribeEvent
    public void onClientConnectedToServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        YuZuUI.inGamed = true;
        YuZuUI.exit = false;
    }

    private static SenrenBankaTitleScreen getInstance() {
        return TitleScreenHolder.INSTANCE;
    }

    private static class TitleScreenHolder {
        private static final SenrenBankaTitleScreen INSTANCE = new SenrenBankaTitleScreen();
    }
}
