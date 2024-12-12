package com.paulzzh.yuzu.mixins.early.minecraft;

import net.minecraft.client.audio.MusicTicker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.paulzzh.yuzu.YuZuUI.exit;
import static com.paulzzh.yuzu.YuZuUI.inGamed;

@Mixin(value = MusicTicker.class)
public class MusicTickerMixin {
    @Inject(method = "update", at = @At(value = "HEAD"), cancellable = true)
    public void inject(CallbackInfo ci) {
        if (!exit && !inGamed) {
            ci.cancel();
        }
    }
}
