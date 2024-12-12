package com.paulzzh.yuzu.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
    public static void blit(ResourceLocation texture, float x, float y, float width, float height) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + height, 0, 0, 1); // 左下角
        tessellator.addVertexWithUV(x + width, y + height, 0, 1, 1); // 右下角
        tessellator.addVertexWithUV(x + width, y, 0, 1, 0); // 右上角
        tessellator.addVertexWithUV(x, y, 0, 0, 0); // 左上角
        tessellator.draw();
    }

    /**
     * Draws a solid color rectangle with the specified coordinates. Args: x1, y1, x2, y2
     */
    public static void drawRect(int left, int top, int right, int bottom) {
        int j1;

        if (left < right) {
            j1 = left;
            left = right;
            right = j1;
        }

        if (top < bottom) {
            j1 = top;
            top = bottom;
            bottom = j1;
        }
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        tessellator.startDrawingQuads();
        tessellator.addVertex((double) left, (double) bottom, 0.0D);
        tessellator.addVertex((double) right, (double) bottom, 0.0D);
        tessellator.addVertex((double) right, (double) top, 0.0D);
        tessellator.addVertex((double) left, (double) top, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    /**
     * Draws a rectangle with a vertical gradient between the specified colors.
     */
    protected static void drawGradientRect(int x1, int y1, int x2, int y2, int color, float startAlpha, float endAlpha) {
        float f1 = (float) (color >> 16 & 255) / 255.0F;
        float f2 = (float) (color >> 8 & 255) / 255.0F;
        float f3 = (float) (color & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, startAlpha);
        tessellator.addVertex((double) x1, (double) y2, (double) 0);
        tessellator.addVertex((double) x1, (double) y1, (double) 0);
        tessellator.setColorRGBA_F(f1, f2, f3, endAlpha);
        tessellator.addVertex((double) x2, (double) y1, (double) 0);
        tessellator.addVertex((double) x2, (double) y2, (double) 0);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
}
