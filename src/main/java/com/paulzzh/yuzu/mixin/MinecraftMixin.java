package com.paulzzh.yuzu.mixin;

import com.paulzzh.yuzu.sound.TitleScreenMusicTicker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Redirect(
            method = "runTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/audio/MusicTicker;update()V"
            )
    )
    private void updateYuZuTicker(MusicTicker instance) {
        if (TitleScreenMusicTicker.tickBGM()) {
            MusicTickerAccessor accessor = (MusicTickerAccessor) instance;
            ISound currentMusic = accessor.getCurrentMusic();
            if (currentMusic != null) {
                Minecraft.getMinecraft().getSoundHandler().stopSound(currentMusic);
                accessor.setCurrentMusic(null);
            }
        } else {
            instance.update();
        }
    }
}
