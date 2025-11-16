package com.paulzzh.yuzu.mixin;

import com.paulzzh.yuzu.YuZuUI;
import com.paulzzh.yuzu.YuZuUIConfig;
import com.paulzzh.yuzu.sound.SoundRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(
        method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/SoundHandler;stopSounds()V")
    )
    private void inject2(CallbackInfo ci) {
        YuZuUI.inGamed = true;
    }

    @Inject(method = "getAmbientMusicType", at = @At("RETURN"), cancellable = true)
    private void setTitleMusicType(@Nonnull CallbackInfoReturnable<MusicTicker.MusicType> cir) {
        if (
            SoundRegister.YUZU_TITLE != null
                && cir.getReturnValue() == MusicTicker.MusicType.MENU
                && YuZuUIConfig.bgm && !YuZuUI.exit && !YuZuUI.inGamed
        ) {
            cir.setReturnValue(SoundRegister.YUZU_TITLE);
        }
    }
}
