package com.paulzzh.yuzu.gui.widget;

import net.minecraft.client.Minecraft;

public interface Clickable {

    boolean mousePressed(Minecraft mc, int mouseX, int mouseY);

}
