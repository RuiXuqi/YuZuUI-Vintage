package com.paulzzh.yuzu.gui;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static com.paulzzh.yuzu.gui.YuZuUIGuiMainMenu.mc;

public class RenderUtils {
    public static void blit(ResourceLocation texture, float x, float y, float width, float height) {
        mc.getTextureManager().bindTexture(texture);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + height, 0, 0, 1); // 左下角
        tessellator.addVertexWithUV(x + width, y + height, 0, 1, 1); // 右下角
        tessellator.addVertexWithUV(x + width, y, 0, 1, 0); // 右上角
        tessellator.addVertexWithUV(x, y, 0, 0, 0); // 左上角
        tessellator.draw();
    }

    /**
     * Draws a rectangle with a vertical gradient between the specified colors.
     */
    public static void drawGradientRect(int left, int top, int right, int bottom, int z, int startColor, int endColor) {
        float f = (float) (startColor >> 24 & 255) / 255.0F;
        float f1 = (float) (startColor >> 16 & 255) / 255.0F;
        float f2 = (float) (startColor >> 8 & 255) / 255.0F;
        float f3 = (float) (startColor & 255) / 255.0F;
        float f4 = (float) (endColor >> 24 & 255) / 255.0F;
        float f5 = (float) (endColor >> 16 & 255) / 255.0F;
        float f6 = (float) (endColor >> 8 & 255) / 255.0F;
        float f7 = (float) (endColor & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex((double) right, (double) top, (double) z);
        tessellator.addVertex((double) left, (double) top, (double) z);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex((double) left, (double) bottom, (double) z);
        tessellator.addVertex((double) right, (double) bottom, (double) z);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
}
