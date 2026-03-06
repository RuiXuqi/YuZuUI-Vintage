package com.paulzzh.yuzu.gui;

@FunctionalInterface
public interface IEasing {
    float apply(float t);

    /// 线性函数
    IEasing LINEAR = t -> t;

    /// 缓动函数
    static IEasing exponentialOut(double a) {
        return t -> (float) ((Math.pow(a, t) - 1) / (a - 1));
    }
}
