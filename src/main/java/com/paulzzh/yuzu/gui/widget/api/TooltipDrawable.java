package com.paulzzh.yuzu.gui.widget.api;

import javax.annotation.Nullable;

public interface TooltipDrawable {
    boolean shouldDraw();

    @Nullable
    String getTooltip();
}
