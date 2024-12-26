package com.paulzzh.yuzu.mixins.early.minecraft;

import com.paulzzh.yuzu.config.YuZuUIConfig;
import net.minecraft.client.audio.MusicTicker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.paulzzh.yuzu.YuZuUI.exit;
import static com.paulzzh.yuzu.YuZuUI.inGamed;
import static com.paulzzh.yuzu.gui.YuZuUIGuiMainMenu.tickSound;

@Mixin(value = MusicTicker.class)
public class MusicTickerMixin {
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
