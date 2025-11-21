package com.paulzzh.yuzu.mixin;

import com.paulzzh.yuzu.YuZuUI;
import com.paulzzh.yuzu.YuZuUIConfig;
import com.paulzzh.yuzu.gui.screen.SenrenBankaTitleScreen;
import com.paulzzh.yuzu.mixininterface.MusicTickerInterface;
import com.paulzzh.yuzu.sound.SoundManager;
import com.paulzzh.yuzu.sound.SoundRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 没有修改 {@link MusicTicker#update()} 方法，因此可以不 mixin 其他模组的 MusicTicker。
 * 兼容性理论上更好点吧。
 */
@Mixin(MusicTicker.class)
public abstract class MusicTickerMixin implements MusicTickerInterface {
    @Unique
    private boolean yuZuUI$isTitleMusic;
    @Unique
    @Nullable
    private Long yuZuUI$soundStartTime = null;

    @Inject(method = "playMusic", at = @At("HEAD"), cancellable = true)
    private void catchMusicType(@Nonnull MusicTicker.MusicType musicType, CallbackInfo ci) {
        if (!YuZuUIConfig.bgm && !YuZuUI.exit && !YuZuUI.inGamed) {
            // 这使 currentMusic 最终为 null （因为并没有声音在播放），因此 update() 会继续调用 playMusic()
            ci.cancel();
            return;
        }
        this.yuZuUI$isTitleMusic = musicType == SoundRegister.YUZU_TITLE_TYPE;
    }

    @Redirect(
        method = "playMusic",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/audio/PositionedSoundRecord;getMusicRecord(Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/client/audio/PositionedSoundRecord;"
        )
    )
    private @Nonnull PositionedSoundRecord modifySoundEvent(SoundEvent soundIn) {
        return this.yuZuUI$isTitleMusic ?
            SoundManager.getSoundRecord(soundIn, SoundCategory.MUSIC, 0.25F, 1.0F)
            : PositionedSoundRecord.getMusicRecord(soundIn);
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
        if (this.yuZuUI$soundStartTime == null) return;
        long currentTime = Minecraft.getSystemTime();
        if (currentTime - this.yuZuUI$soundStartTime > SenrenBankaTitleScreen.getDelay()) {
            soundHandler.playSound(sound);
            this.yuZuUI$soundStartTime = currentTime;
        }
    }

    @Unique
    @Override
    public void yuZuUI$updateSoundStartTime() {
        this.yuZuUI$soundStartTime = Minecraft.getSystemTime();
    }
}
