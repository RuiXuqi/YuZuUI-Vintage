package com.paulzzh.yuzu.gui.widget;

import javax.annotation.Nullable;

public interface TooltipDrawable {
    boolean shouldDraw();

    @Nullable
    String getTooltip();
}
