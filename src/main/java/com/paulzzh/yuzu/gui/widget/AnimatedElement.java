package com.paulzzh.yuzu.gui.widget;

import com.paulzzh.yuzu.gui.VirtualScreen;
import com.paulzzh.yuzu.gui.Easing;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 动画相关，抽象成类避免重复代码。
 */
@SuppressWarnings("unused")
public abstract class AnimatedElement extends Element {
    private final Map<String, Consumer<Float>> animators = new HashMap<>();
    private long duration = 0L;
    private long delay = 0L;
    private @Nullable Long startTime = null;
    private @Nullable Runnable onComplete = null;
    private boolean completed = false;

    public AnimatedElement(VirtualScreen virtualScreen) {
        super(virtualScreen);
    }

    protected void tick() {
        if (this.duration == 0L || this.animators.isEmpty()) {
            return;
        }
        long currentTime = System.currentTimeMillis();
        if (this.startTime == null) {
            this.startTime = currentTime;
        } else {
            float elapsed = currentTime - this.startTime;
            if (elapsed > this.delay) {
                float time = Math.min((elapsed - this.delay) / this.duration, 1.0f);
                this.animators.values().forEach(c -> c.accept(time));
                if (time >= 1.0f && !this.completed) {
                    this.completed = true;
                    if (this.onComplete != null) {
                        this.onComplete.run();
                    }
                }
            }
        }
    }

    protected void addAnimator(String identifier, Consumer<Float> animator) {
        this.animators.put(identifier, animator);
    }

    public void animateX(float endX, Easing easing) {
        this.animateX(this.x, endX, easing);
    }

    public void animateX(float startX, float endX, Easing easing) {
        this.addAnimator("x", t -> this.x = startX + (endX - startX) * easing.apply(t));
    }

    public void animateY(float endY, Easing easing) {
        this.animateY(this.y, endY, easing);
    }

    public void animateY(float startY, float endY, Easing easing) {
        this.addAnimator("y", t -> this.y = startY + (endY - startY) * easing.apply(t));
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void resetAnimation() {
        this.animators.clear();
        this.duration = 0L;
        this.delay = 0L;
        this.startTime = null;
        this.onComplete = null;
        this.completed = false;
    }

    public void setOnComplete(@Nullable Runnable action) {
        this.onComplete = action;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
