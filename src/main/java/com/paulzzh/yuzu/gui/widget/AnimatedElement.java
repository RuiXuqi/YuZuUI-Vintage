package com.paulzzh.yuzu.gui.widget;

/**
 * 动画相关，抽象成类避免重复代码。
 */
@SuppressWarnings("unused")
public abstract class AnimatedElement extends Element {
    Long duration;
    Long startTime = null;
    Long delay;

    public Long getDuration() {
        return this.duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getDelay() {
        return this.delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }
}
