package com.paulzzh.yuzu.mixin.early.minecraft;

import com.paulzzh.yuzu.YuZuUI;
import com.paulzzh.yuzu.YuZuUIConfig;
import com.paulzzh.yuzu.gui.screen.SenrenBankaTitleScreen;
import net.minecraft.client.audio.MusicTicker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MusicTicker.class)
public class MusicTickerMixin {
    @Inject(method = "update", at = @At(value = "HEAD"), cancellable = true)
    private void inject(CallbackInfo ci) {
        if (YuZuUIConfig.bgm) {
            if (!YuZuUI.exit && !YuZuUI.inGamed) {
                MusicTicker self = (MusicTicker) (Object) this;
                SenrenBankaTitleScreen.tickSound(self.mc);
                ci.cancel();
            }
        }
    }
}
