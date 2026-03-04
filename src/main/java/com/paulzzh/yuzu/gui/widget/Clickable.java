package com.paulzzh.yuzu.gui.widget;

public interface Clickable {
    boolean mousePressed(int mouseX, int mouseY, int button);

    void onClick(int mouseX, int mouseY, int button);
}
