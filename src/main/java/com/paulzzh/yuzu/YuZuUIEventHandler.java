package com.paulzzh.yuzu;

import com.paulzzh.yuzu.gui.screen.SenrenBankaTitleScreen;
import com.paulzzh.yuzu.integration.CMMIntegration;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID, value = Side.CLIENT)
public class YuZuUIEventHandler {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void openGui(GuiOpenEvent event) {
        GuiScreen gui = event.getGui();
        if (YuZuUI.exit) return;
        if (gui instanceof GuiMainMenu || CMMIntegration.isCMMMenu(gui)) {
            event.setGui(new SenrenBankaTitleScreen());
        }
    }

    @SubscribeEvent
    public static void onClientConnectedToServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        YuZuUI.inGamed = true;
        YuZuUI.exit = false;
    }
}
