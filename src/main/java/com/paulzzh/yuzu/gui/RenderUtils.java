package com.paulzzh.yuzu.gui;

import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
    public static void blit(float x, float y, float width, float height) {
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + height, 0, 0, 1); // 左下角
        tessellator.addVertexWithUV(x + width, y + height, 0, 1, 1); // 右下角
        tessellator.addVertexWithUV(x + width, y, 0, 1, 0); // 右上角
        tessellator.addVertexWithUV(x, y, 0, 0, 0); // 左上角
        tessellator.draw();
    }
}
