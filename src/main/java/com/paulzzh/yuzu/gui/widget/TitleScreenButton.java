package com.paulzzh.yuzu.gui.widget;

import com.paulzzh.yuzu.function.AnimationFunction;
import com.paulzzh.yuzu.gui.RenderUtils;
import com.paulzzh.yuzu.gui.VirtualScreen;
import com.paulzzh.yuzu.sound.SoundManager;
import com.paulzzh.yuzu.sound.VoiceType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.time.Instant;
import java.util.function.Consumer;

import static com.paulzzh.yuzu.sound.InitSounds.YUZU_TITLE_BUTTON_CLICK;
import static com.paulzzh.yuzu.sound.InitSounds.YUZU_TITLE_BUTTON_ON;
import static com.paulzzh.yuzu.sound.SoundManager.playSound;
import static com.paulzzh.yuzu.sound.SoundManager.playVoice;

/**
 * @author : IMG
 * @create : 2024/10/26
 */
@SuppressWarnings("FieldMayBeFinal")
public class TitleScreenButton extends AnimatedElement implements Clickable {
    private final ResourceLocation texture;
    private final ResourceLocation textureHover;
    /**
     * 按钮点击声音。
     */
    private SoundEvent sound;
    /**
     * 特定的语音种类。
     */
    private VoiceType voiceType;
    /**
     * 随机到的语音，会在播放后刷新。
     */
    private SoundEvent voice;
    private Consumer<TitleScreenButton> onClick;
    /**
     * 上一帧是否悬停。
     */
    private boolean wasHovered;
    /**
     * 本帧是否悬停。
     */
    private boolean isHovered;
    private boolean visible;
    private boolean clickable;

    private AnimationFunction<Float> alphaFunction;

    public TitleScreenButton(float x, float y, float width, float height, ResourceLocation texture, ResourceLocation textureHover, VirtualScreen virtualScreen) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = texture;
        this.textureHover = textureHover;
        this.virtualScreen = virtualScreen;
        this.sound = YUZU_TITLE_BUTTON_CLICK;
        this.voiceType = null;
        this.visible = true;
        this.alpha = 0f;
    }

    @Override
    public void render(Minecraft mc, int mouseX, int mouseY, float delta) {
        this.clickable = this.alpha != 0.0F && this.visible;
        if (this.visible) {
            this.isHovered = isMouseOver(mouseX, mouseY) && this.clickable;
            if (!this.wasHovered && this.isHovered) {
                mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(YUZU_TITLE_BUTTON_ON, 1.0F));
            }
            this.wasHovered = this.isHovered;
            this.renderButton(mc);
        }
        tick();
    }

    public void renderButton(Minecraft mc) {
        if (this.visible) {
            if (this.isHovered) {
                mc.getTextureManager().bindTexture(textureHover);
            } else {
                mc.getTextureManager().bindTexture(texture);
            }
            RenderUtils.blit(
                virtualScreen.toPracticalX(x),
                virtualScreen.toPracticalY(y),
                virtualScreen.toPracticalWidth(width),
                virtualScreen.toPracticalHeight(height)
            );
        }
    }

    protected void tick() {
        if (delay == null || duration == 0) {
            return;
        }

        long currentTime = Instant.now().toEpochMilli();
        if (startTime == null) {
            startTime = currentTime;
        } else {
            long t = currentTime - startTime;
            if (t > delay) {
                float time = Math.min((float) (t - delay) / duration, 1);
                if (alphaFunction != null) {
                    alpha = alphaFunction.apply(time, alpha);
                }
            }
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (this.clickable && this.isHovered) {
            if (sound != null) {
                playSound(mc, sound);
            }
            if (voiceType != null) {
                voice = playVoice(mc, voiceType);
            }
            if (onClick != null) {
                onClick.accept(this);
            }
            return true;
        }
        return false;
    }

    /**
     * 实时的悬停检测。不受其他变量影响。
     */
    public boolean isMouseOver(double mouseX, double mouseY) {
        float virtualX = virtualScreen.toVirtualX((float) mouseX) - x;
        float virtualY = virtualScreen.toVirtualY((float) mouseY) - y;
        return virtualX >= 0 && virtualX < width && virtualY >= 0 && virtualY < height;
    }

    public boolean isVoiceAvailable() {
        return SoundManager.getIsVoiceAvailable(voice);
    }

    public void setOnClick(Consumer<TitleScreenButton> onClick) {
        this.onClick = onClick;
    }

    public void setAlphaFunction(AnimationFunction<Float> alphaFunction) {
        this.alphaFunction = alphaFunction;
    }

    public void setSound(SoundEvent sound) {
        this.sound = sound;
    }

    public void setVoiceType(VoiceType voiceType) {
        this.voiceType = voiceType;
    }
}
