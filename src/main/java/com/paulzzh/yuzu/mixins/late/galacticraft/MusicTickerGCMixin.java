package com.paulzzh.yuzu.mixins.late.galacticraft;

import com.paulzzh.yuzu.config.YuZuUIConfig;
import micdoodle8.mods.galacticraft.core.client.sounds.MusicTickerGC;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.paulzzh.yuzu.YuZuUI.exit;
import static com.paulzzh.yuzu.YuZuUI.inGamed;
import static com.paulzzh.yuzu.gui.YuZuUIGuiMainMenu.tickSound;

@Mixin(value = MusicTickerGC.class)
public class MusicTickerGCMixin {
    @Inject(method = "update", at = @At(value = "HEAD"), cancellable = true)
    public void inject(CallbackInfo ci) {
        if (YuZuUIConfig.bgm) {
            if (!exit && !inGamed) {
                tickSound();
                ci.cancel();
            }
        }
    }
}
