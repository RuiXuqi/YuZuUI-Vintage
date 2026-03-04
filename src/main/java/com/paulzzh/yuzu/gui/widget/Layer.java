package com.paulzzh.yuzu.gui.widget;

import com.paulzzh.yuzu.gui.RenderUtils;
import com.paulzzh.yuzu.gui.VirtualScreen;
import com.paulzzh.yuzu.gui.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * @author IMG
 * @since 2024/10/26
 */
@SuppressWarnings({"unused"})
public class Layer extends AnimatedElement {
    private final ResourceLocation texture;
    private float alpha = 1f;
    private float scale = 1f;

    public Layer(ResourceLocation texture, float x, float y, float width, float height, VirtualScreen virtualScreen) {
        super(virtualScreen);
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.tick();
        GlStateManager.color(1.0F, 1.0F, 1.0F, this.alpha);
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
        RenderUtils.blit(this.virtualScreen, this.x, this.y, this.width * this.scale, this.height * this.scale);
    }

    public void animateAlpha(float endAlpha, Easing easing) {
        this.animateAlpha(this.alpha, endAlpha, easing);
    }

    public void animateAlpha(float startAlpha, float endAlpha, Easing easing) {
        this.addAnimator("alpha", t -> this.alpha = startAlpha + (endAlpha - startAlpha) * easing.apply(t));
    }

    public void animateScale(float endScale, Easing easing) {
        this.animateScale(this.scale, endScale, easing);
    }

    public void animateScale(float startScale, float endScale, Easing easing) {
        this.addAnimator("scale", t -> this.scale = startScale + (endScale - startScale) * easing.apply(t));
    }

    public float getAlpha() {
        return this.alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
