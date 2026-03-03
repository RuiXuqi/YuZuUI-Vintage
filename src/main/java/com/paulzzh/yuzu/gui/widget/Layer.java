package com.paulzzh.yuzu.gui.widget;

import com.paulzzh.yuzu.gui.RenderUtils;
import com.paulzzh.yuzu.gui.VirtualScreen;
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
    private float scale;

    public Layer(ResourceLocation texture, float x, float y, float width, float height, float scale, float alpha, VirtualScreen virtualScreen) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.alpha = alpha;
        this.virtualScreen = virtualScreen;
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        if (this.duration == null || this.duration == 0) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, this.alpha);
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
            RenderUtils.blit(
                    this.virtualScreen.toPracticalX(this.x),
                    this.virtualScreen.toPracticalY(this.y),
                    this.virtualScreen.toPracticalWidth(this.width * this.scale),
                    this.virtualScreen.toPracticalHeight(this.height * this.scale)
            );
        }

        // 计算动画进度
        long currentTime = System.currentTimeMillis();
        if (this.startTime == null) {
            this.startTime = currentTime;
        } else {
            float elapsed = currentTime - this.startTime;
            if (elapsed > this.delay) {
                float time = Math.min((elapsed - this.delay) / this.duration, 1.0f);
                if (this.xFunction != null) {
                    this.x = this.xFunction.apply(time, this.x);
                }
                if (this.yFunction != null) {
                    this.y = this.yFunction.apply(time, this.y);
                }
                if (this.alphaFunction != null) {
                    this.alpha = this.alphaFunction.apply(time, this.alpha);
                }
                if (this.scaleFunction != null) {
                    this.scale = this.scaleFunction.apply(time, this.scale);
                }
            }
        }

        // 渲染更新后的状态
        GlStateManager.color(1.0F, 1.0F, 1.0F, this.alpha);
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
        RenderUtils.blit(
                this.virtualScreen.toPracticalX(this.x),
                this.virtualScreen.toPracticalY(this.y),
                this.virtualScreen.toPracticalWidth(this.width * this.scale),
                this.virtualScreen.toPracticalHeight(this.height * this.scale)
        );
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * 动画相关
     */
    private AnimationFunction<Float> xFunction;
    private AnimationFunction<Float> yFunction;
    private AnimationFunction<Float> alphaFunction;
    private AnimationFunction<Float> scaleFunction;

    public AnimationFunction<Float> getXFunction() {
        return this.xFunction;
    }

    public void setXFunction(AnimationFunction<Float> xFunction) {
        this.xFunction = xFunction;
    }

    public AnimationFunction<Float> getYFunction() {
        return this.yFunction;
    }

    public void setYFunction(AnimationFunction<Float> yFunction) {
        this.yFunction = yFunction;
    }

    public AnimationFunction<Float> getAlphaFunction() {
        return this.alphaFunction;
    }

    public void setAlphaFunction(AnimationFunction<Float> alphaFunction) {
        this.alphaFunction = alphaFunction;
    }

    public AnimationFunction<Float> getScaleFunction() {
        return this.scaleFunction;
    }

    public void setScaleFunction(AnimationFunction<Float> scaleFunction) {
        this.scaleFunction = scaleFunction;
    }
}
