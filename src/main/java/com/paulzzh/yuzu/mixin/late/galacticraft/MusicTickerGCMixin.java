package com.paulzzh.yuzu.mixin.late.galacticraft;

import com.paulzzh.yuzu.YuZuUI;
import com.paulzzh.yuzu.YuZuUIConfig;
import com.paulzzh.yuzu.gui.screen.SenrenBankaTitleScreen;
import micdoodle8.mods.galacticraft.core.client.sounds.MusicTickerGC;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MusicTickerGC.class, remap = false)
public class MusicTickerGCMixin {
    @Inject(method = "update", at = @At(value = "HEAD"), cancellable = true)
    private void inject(CallbackInfo ci) {
        if (YuZuUIConfig.bgm) {
            if (!YuZuUI.exit && !YuZuUI.inGamed) {
                MusicTickerGC self = (MusicTickerGC) (Object) this;
                SenrenBankaTitleScreen.tickSound(self.mc);
                ci.cancel();
            }
        }
    }
}
