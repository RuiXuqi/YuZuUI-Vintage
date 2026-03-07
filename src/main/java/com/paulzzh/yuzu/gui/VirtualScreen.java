package com.paulzzh.yuzu.gui;

/**
 * @author IMG
 * @since 2024/10/26
 */
@SuppressWarnings("unused")
public class VirtualScreen {
    // 虚拟宽高
    private int virtualWidth;
    private int virtualHeight;
    // 实际宽高
    private int practicalWidth;
    private int practicalHeight;
    // XY 偏移量
    private int offsetX;
    private int offsetY;

    public VirtualScreen() {
    }

    public VirtualScreen(int virtualWidth, int virtualHeight) {
        this.virtualWidth = virtualWidth;
        this.virtualHeight = virtualHeight;
    }

    public float toPracticalX(float x) {
        return this.offsetX + x * (float) this.practicalWidth / (float) this.virtualWidth;
    }

    public float toPracticalY(float y) {
        return this.offsetY + y * (float) this.practicalHeight / (float) this.virtualHeight;
    }

    public float toPracticalWidth(float width) {
        return width * (float) this.practicalWidth / (float) this.virtualWidth;
    }

    public float toPracticalHeight(float height) {
        return height * (float) this.practicalHeight / (float) this.virtualHeight;
    }

    public float toVirtualX(float x) {
        return (x - this.offsetX) * (float) this.virtualWidth / (float) this.practicalWidth;
    }

    public float toVirtualY(float y) {
        return (y - this.offsetY) * (float) this.virtualHeight / (float) this.practicalHeight;
    }

    public void scissorAround() {
        RenderUtils.scissor(this.offsetX, this.offsetY, this.practicalWidth, this.practicalHeight);
    }

    public int getVirtualWidth() {
        return this.virtualWidth;
    }

    public void setVirtualWidth(int virtualWidth) {
        this.virtualWidth = virtualWidth;
    }

    public int getVirtualHeight() {
        return this.virtualHeight;
    }

    public void setVirtualHeight(int virtualHeight) {
        this.virtualHeight = virtualHeight;
    }

    public int getPracticalWidth() {
        return this.practicalWidth;
    }

    public void setPracticalWidth(int practicalWidth) {
        this.practicalWidth = practicalWidth;
    }

    public int getPracticalHeight() {
        return this.practicalHeight;
    }

    public void setPracticalHeight(int practicalHeight) {
        this.practicalHeight = practicalHeight;
    }

    public int getOffsetX() {
        return this.offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return this.offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
}
