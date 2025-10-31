package com.paulzzh.yuzu.mixin.early.minecraft;

import com.paulzzh.yuzu.YuZuUI;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class)
public class MinecraftMixin {
    @Inject(
        method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/SoundHandler;stopSounds()V")
    )
    private void inject2(CallbackInfo ci) {
        YuZuUI.inGamed = true;
    }
}
