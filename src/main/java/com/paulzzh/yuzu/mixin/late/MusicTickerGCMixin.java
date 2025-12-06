package com.paulzzh.yuzu.mixin.late;

import com.paulzzh.yuzu.YuZuUI;
import com.paulzzh.yuzu.YuZuUIConfig;
import com.paulzzh.yuzu.sound.TitleScreenMusicTicker;
import micdoodle8.mods.galacticraft.core.client.sounds.MusicTickerGC;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MusicTickerGC.class, remap = false)
public class MusicTickerGCMixin {
    @Inject(method = "update", at = @At(value = "HEAD"), cancellable = true)
    public void inject(CallbackInfo ci) {
        if (!YuZuUI.exit && !YuZuUI.inGamed) {
            if (YuZuUIConfig.bgm) TitleScreenMusicTicker.tickBGM();
            ci.cancel();
        }
    }
}
