package com.paulzzh.yuzu.mixin;

import com.paulzzh.yuzu.YuZuUI;
import com.paulzzh.yuzu.YuZuUIConfig;
import com.paulzzh.yuzu.gui.screen.SenrenBankaTitleScreen;
import com.paulzzh.yuzu.mixininterface.MusicTickerInterface;
import com.paulzzh.yuzu.sound.SoundRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 没有修改 {@link MusicTicker#update()} 方法，因此可以不 mixin 其他模组的 MusicTicker。
 * 兼容性理论上更好点吧。
 */
@Mixin(MusicTicker.class)
public abstract class MusicTickerMixin implements MusicTickerInterface {
    @Shadow
    private ISound currentMusic;
    @Unique
    private boolean yuZuUI$isTitleMusic;
    @Unique
    @Nullable
    private Long yuZuUI$soundStartTime = null;

    @Redirect(
        method = "playMusic",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/audio/PositionedSoundRecord;getMusicRecord(Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/client/audio/PositionedSoundRecord;"
        )
    )
    private @Nonnull PositionedSoundRecord catchSoundEvent(SoundEvent soundIn) {
        this.yuZuUI$isTitleMusic = soundIn == SoundRegister.YUZU_TITLE_MUSIC;
        return PositionedSoundRecord.getMusicRecord(soundIn);
    }

    /**
     * 延迟播放音乐。希望没人重写这个还不调用 super()。
     */
    @Redirect(
        method = "playMusic",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/audio/SoundHandler;playSound(Lnet/minecraft/client/audio/ISound;)V"
        )
    )
    private void playTitleMusic(SoundHandler soundHandler, ISound sound) {
        if (!this.yuZuUI$isTitleMusic) {
            soundHandler.playSound(sound);
            return;
        }
        if (
            !soundHandler.isSoundPlaying(sound)
                && YuZuUIConfig.bgm && !YuZuUI.exit && !YuZuUI.inGamed
        ) {
            if (this.yuZuUI$soundStartTime == null) return;
            long currentTime = Minecraft.getSystemTime();
            if (currentTime - this.yuZuUI$soundStartTime > SenrenBankaTitleScreen.getDelay()) {
                soundHandler.playSound(sound);
                this.yuZuUI$soundStartTime = currentTime;
            }
        } else {
            soundHandler.stopSound(sound);
            this.currentMusic = null;
        }
    }

    @Unique
    @Override
    public void yuZuUI$updateSoundStartTime() {
        this.yuZuUI$soundStartTime = Minecraft.getSystemTime();
    }
}
