package com.paulzzh.yuzu.gui.widget;

import com.paulzzh.yuzu.YuZuUI;
import com.paulzzh.yuzu.gui.IEasing;
import com.paulzzh.yuzu.gui.RenderUtils;
import com.paulzzh.yuzu.gui.VirtualScreen;
import com.paulzzh.yuzu.gui.widget.api.Clickable;
import com.paulzzh.yuzu.gui.widget.api.Renderable;
import com.paulzzh.yuzu.gui.widget.api.TooltipDrawable;
import com.paulzzh.yuzu.sound.SoundManager;
import com.paulzzh.yuzu.sound.SoundRegister;
import com.paulzzh.yuzu.sound.VoiceType;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author IMG
 * @since 2024/10/26
 */
public class TitleScreenButton extends AnimatedElement implements Renderable, Clickable, TooltipDrawable {
    private final VirtualScreen virtualScreen;
    private final ResourceLocation texture;
    private final ResourceLocation textureHover;
    /// 按钮点击声音
    private SoundEvent sound;
    /// 特定的语音种类
    private VoiceType voiceType;
    private Consumer<TitleScreenButton> onClick;
    /// 上一帧是否悬停
    private boolean wasHovered;
    /// 本帧是否悬停
    private boolean isHovered;
    private boolean visible = true;
    private float alpha = 1f;
    private boolean clickable;
    private @Nullable Supplier<String> tooltipSupplier;

    public TitleScreenButton(
            VirtualScreen virtualScreen, ResourceLocation texture, ResourceLocation textureHover,
            float x, float y, float width, float height
    ) {
        this.virtualScreen = virtualScreen;
        this.texture = texture;
        this.textureHover = textureHover;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sound = SoundRegister.YUZU_TITLE_BUTTON_CLICK;
        this.voiceType = null;
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.tick();
        this.clickable = this.alpha != 0.0F && this.visible && !YuZuUI.exit;
        if (this.visible) {
            this.isHovered = this.isMouseOver(mouseX, mouseY);
            if (this.clickable && !this.wasHovered && this.isHovered) {
                SoundManager.playSound(SoundRegister.YUZU_TITLE_BUTTON_ON);
            }
            this.wasHovered = this.isHovered;
            Minecraft.getMinecraft().getTextureManager().bindTexture(
                    this.clickable && this.isHovered ? this.textureHover : this.texture
            );
            RenderUtils.blit(this.virtualScreen, this.x, this.y, this.width, this.height);
        }
    }

    @Override
    public boolean mousePressed(int mouseX, int mouseY, int button) {
        return this.clickable && this.isHovered;
    }

    @Override
    public void onClick(int mouseX, int mouseY, int button) {
        if (this.sound != null) {
            SoundManager.playSound(this.sound);
        }
        if (this.voiceType != null) {
            SoundManager.playVoice(this.voiceType);
        }
        if (this.onClick != null) {
            this.onClick.accept(this);
        }
    }

    /**
     * 实时的悬停检测。不受其他变量影响。
     */
    private boolean isMouseOver(int mouseX, int mouseY) {
        float vMouseX = this.virtualScreen.toVirtualX(mouseX);
        float vMouseY = this.virtualScreen.toVirtualY(mouseY);
        return vMouseX >= this.x && vMouseX < this.x + this.width && vMouseY >= this.y && vMouseY < this.y + this.height;
    }

    @Override
    public boolean shouldDraw() {
        return this.visible && this.clickable && this.isHovered;
    }

    @Nullable
    @Override
    public String getTooltip() {
        return this.tooltipSupplier != null ? this.tooltipSupplier.get() : null;
    }

    public void animateAlpha(float endAlpha, IEasing easing) {
        this.animateAlpha(this.alpha, endAlpha, easing);
    }

    public void animateAlpha(float startAlpha, float endAlpha, IEasing easing) {
        this.addAnimator("alpha", t -> this.alpha = startAlpha + (endAlpha - startAlpha) * easing.apply(t));
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setOnClick(Consumer<TitleScreenButton> onClick) {
        this.onClick = onClick;
    }

    public void setSound(SoundEvent sound) {
        this.sound = sound;
    }

    public void setVoiceType(VoiceType voiceType) {
        this.voiceType = voiceType;
    }

    public void setTooltipSupplier(@Nullable Supplier<String> tooltipSupplier) {
        this.tooltipSupplier = tooltipSupplier;
    }

    public float getAlpha() {
        return this.alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
