package com.paulzzh.yuzu.mixins.early.minecraft;

import com.paulzzh.yuzu.gui.YuZuUIGuiMainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.paulzzh.yuzu.YuZuUI.exit;

@Mixin(value = Minecraft.class)
public class MinecraftMixin {
    @Unique
    private static YuZuUIGuiMainMenu YuZuUI$INSTANCE = null;

    @Inject(method = "displayGuiScreen", at = @At(value = "RETURN"))
    public void inject(GuiScreen guiScreenIn, CallbackInfo ci) {
        if ((guiScreenIn instanceof GuiMainMenu
            || (guiScreenIn != null && "lumien.custommainmenu.gui.GuiCustom".equals(guiScreenIn.getClass().getCanonicalName())))
            && !exit) {
            if (YuZuUI$INSTANCE == null) {
                YuZuUI$INSTANCE = new YuZuUIGuiMainMenu();
            }
            Minecraft.getMinecraft().displayGuiScreen(YuZuUI$INSTANCE);
        }
    }
}
