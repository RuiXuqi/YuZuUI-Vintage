package com.paulzzh.yuzu.gui;

@FunctionalInterface
public interface Easing {
    float apply(float t);

    /// 线性函数
    Easing LINEAR = t -> t;

    /// 缓动函数
    static Easing exponentialOut(double a) {
        return t -> (float) ((Math.pow(a, t) - 1) / (a - 1));
    }
}
