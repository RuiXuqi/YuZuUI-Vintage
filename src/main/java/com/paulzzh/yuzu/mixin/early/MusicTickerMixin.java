package com.paulzzh.yuzu.mixin.early;

import com.paulzzh.yuzu.YuZuUI;
import com.paulzzh.yuzu.YuZuUIConfig;
import com.paulzzh.yuzu.sound.TitleScreenMusicTicker;
import net.minecraft.client.audio.MusicTicker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MusicTicker.class)
public class MusicTickerMixin {
    @Inject(method = "update", at = @At(value = "HEAD"), cancellable = true)
    public void inject(CallbackInfo ci) {
        if (!YuZuUI.exit && !YuZuUI.inGamed) {
            if (YuZuUIConfig.bgm) TitleScreenMusicTicker.tickBGM();
            ci.cancel();
        }
    }
}
