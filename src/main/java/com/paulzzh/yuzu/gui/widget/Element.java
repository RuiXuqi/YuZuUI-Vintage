package com.paulzzh.yuzu.gui.widget;

import com.paulzzh.yuzu.gui.VirtualScreen;
import net.minecraft.client.Minecraft;

@SuppressWarnings("unused")
public abstract class Element {
    VirtualScreen virtualScreen;
    float x;
    float y;
    float width;
    float height;
    float alpha;

    public abstract void render(Minecraft mc, int mouseX, int mouseY, float delta);

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getAlpha() {
        return this.alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public VirtualScreen getVirtualScreen() {
        return this.virtualScreen;
    }

    public void setVirtualScreen(VirtualScreen virtualScreen) {
        this.virtualScreen = virtualScreen;
    }
}
