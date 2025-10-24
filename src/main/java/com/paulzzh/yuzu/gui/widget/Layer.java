package com.paulzzh.yuzu.gui.widget;

import com.paulzzh.yuzu.function.AnimationFunction;
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
    public void render(Minecraft mc, int mouseX, int mouseY, float delta) {
        if (duration == null || duration == 0) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
            mc.getTextureManager().bindTexture(texture);
            RenderUtils.blit(
                virtualScreen.toPracticalX(x),
                virtualScreen.toPracticalY(y),
                virtualScreen.toPracticalWidth(width * scale),
                virtualScreen.toPracticalHeight(height * scale)
            );
        }

        // 计算动画进度
        long currentTime = System.currentTimeMillis();
        if (startTime == null) {
            startTime = currentTime;
        } else {
            float elapsed = currentTime - startTime;
            if (elapsed > delay) {
                float time = Math.min((elapsed - delay) / duration, 1.0f);
                if (xFunction != null) {
                    x = xFunction.apply(time, x);
                }
                if (yFunction != null) {
                    y = yFunction.apply(time, y);
                }
                if (alphaFunction != null) {
                    alpha = alphaFunction.apply(time, alpha);
                }
                if (scaleFunction != null) {
                    scale = scaleFunction.apply(time, scale);
                }
            }
        }

        // 渲染更新后的状态
        GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
        mc.getTextureManager().bindTexture(texture);
        RenderUtils.blit(
            virtualScreen.toPracticalX(x),
            virtualScreen.toPracticalY(y),
            virtualScreen.toPracticalWidth(width * scale),
            virtualScreen.toPracticalHeight(height * scale)
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
