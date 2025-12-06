package com.paulzzh.yuzu;

import com.paulzzh.yuzu.gui.screen.SenrenBankaTitleScreen;
import com.paulzzh.yuzu.integration.CMMIntegration;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;

import javax.annotation.Nonnull;

public class YuZuUIEventHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void openGui(@Nonnull GuiOpenEvent event) {
        if ((event.gui instanceof GuiMainMenu || CMMIntegration.isCMMMenu(event.gui)) && !YuZuUI.exit) {
            event.gui = new SenrenBankaTitleScreen();
        }
    }

    @SubscribeEvent
    public void onClientConnectedToServer(@Nonnull FMLNetworkEvent.ClientConnectedToServerEvent event) {
        YuZuUI.inGamed = true;
        YuZuUI.exit = false;
    }
}
