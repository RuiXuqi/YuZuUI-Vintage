package com.paulzzh.yuzu.reflection;

import net.minecraft.client.gui.GuiScreen;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class DWGHelper {
    private static Class<?> defaultWorldGeneratorClass;
    private static Class<?> worldTypeNodeClass;
    private static Class<?> guiCreateCustomWorldClass;
    private static Class<?> fieldsClass;
    private static Class<?> selectionListClass;

    public static void init() {
        try {
            defaultWorldGeneratorClass = Class.forName("com.ezrol.terry.minecraft.defaultworldgenerator.DefaultWorldGenerator");
            worldTypeNodeClass = Class.forName("com.ezrol.terry.minecraft.defaultworldgenerator.config.WorldTypeNode");
            guiCreateCustomWorldClass = Class.forName("com.ezrol.terry.minecraft.defaultworldgenerator.gui.GuiCreateCustomWorld");
            fieldsClass = Class.forName("com.ezrol.terry.minecraft.defaultworldgenerator.config.WorldTypeNode$Fields");
            selectionListClass = Class.forName("com.ezrol.terry.minecraft.defaultworldgenerator.gui.DefaultWorldSelectionList");
        } catch (Exception ignored) {
        }
    }

    /**
     * @return GuiCreateCustomWorld 实例
     */
    @Nullable
    public static GuiScreen getDWGGui(GuiScreen currentScreen) {
        try {
            Field modConfigField = defaultWorldGeneratorClass.getDeclaredField("modConfig");
            Object modConfig = modConfigField.get(null);

            Method getSettingsMethod = modConfig.getClass().getMethod("getSettings");
            Object settings = getSettingsMethod.invoke(modConfig);
            Method getWorldListMethod = settings.getClass().getMethod("getWorldList");
            List<?> types = (List<?>) getWorldListMethod.invoke(settings);

            List<Object> selectable = new LinkedList<>();
            Field showInListField = fieldsClass.getDeclaredField("SHOW_IN_LIST");
            Object showInList = showInListField.get(null);

            for (Object node : types) {
                Method getFieldMethod = worldTypeNodeClass.getMethod("getField", fieldsClass);
                Object booleanNode = getFieldMethod.invoke(node, showInList);
                Method getValueMethod = booleanNode.getClass().getMethod("getValue");
                Boolean show = (Boolean) getValueMethod.invoke(booleanNode);

                if (show) {
                    selectable.add(node);
                }
            }

            Object screen;
            if (selectable.isEmpty()) {
                if (types.isEmpty()) {
                    Constructor<?> worldTypeNodeConstructor = worldTypeNodeClass.getConstructor(Class.forName("com.ezrol.terry.minecraft.defaultworldgenerator.config.StructNode"));
                    Object emptyNode = worldTypeNodeConstructor.newInstance(new Object[]{null});
                    screen = guiCreateCustomWorldClass.getConstructor(GuiScreen.class, worldTypeNodeClass)
                        .newInstance(currentScreen, emptyNode);
                } else {
                    screen = guiCreateCustomWorldClass.getConstructor(GuiScreen.class, worldTypeNodeClass)
                        .newInstance(currentScreen, types.get(0));
                }
            } else {
                screen = selectionListClass.getConstructor(GuiScreen.class, List.class)
                    .newInstance(currentScreen, selectable);
            }
            return (GuiScreen) screen;
        } catch (Exception ignored) {
            return null;
        }
    }
}
