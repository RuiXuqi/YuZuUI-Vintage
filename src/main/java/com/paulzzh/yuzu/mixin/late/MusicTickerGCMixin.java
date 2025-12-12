package com.paulzzh.yuzu.mixin.late;

import com.paulzzh.yuzu.sound.TitleScreenMusicTicker;
import micdoodle8.mods.galacticraft.core.client.sounds.MusicTickerGC;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("UnusedMixin")
@Mixin(value = MusicTickerGC.class, remap = false)
public class MusicTickerGCMixin {
    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    public void inject(CallbackInfo ci) {
        if (TitleScreenMusicTicker.tickBGM()) ci.cancel();
    }
}
