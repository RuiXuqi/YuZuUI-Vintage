package com.paulzzh.yuzu.gui.widget;

import com.paulzzh.yuzu.gui.VirtualScreen;

@SuppressWarnings("unused")
public abstract class Element {
    protected final VirtualScreen virtualScreen;
    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public Element(VirtualScreen virtualScreen) {
        this.virtualScreen = virtualScreen;
    }

    public abstract void render(int mouseX, int mouseY, float delta);

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
