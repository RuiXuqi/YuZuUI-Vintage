package com.paulzzh.yuzu.integration;

import com.ezrol.terry.minecraft.defaultworldgenerator.DefaultWorldGenerator;
import com.ezrol.terry.minecraft.defaultworldgenerator.config.BooleanTypeNode;
import com.ezrol.terry.minecraft.defaultworldgenerator.config.WorldTypeNode;
import com.ezrol.terry.minecraft.defaultworldgenerator.gui.DefaultWorldSelectionList;
import com.ezrol.terry.minecraft.defaultworldgenerator.gui.GuiCreateCustomWorld;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public class DWGIntegration {
    public static final String DWG_MODID = "defaultworldgenerator-port";

    /**
     * A copy of {@link com.ezrol.terry.minecraft.defaultworldgenerator.events.GuiEvents#TriggerNewWorld(GuiWorldSelection)}}
     */
    @Nonnull
    @SuppressWarnings("JavadocReference")
    @Optional.Method(modid = DWG_MODID)
    public static GuiScreen getDWGGui(GuiScreen currentScreen) {
        List<WorldTypeNode> types = DefaultWorldGenerator.modConfig.getSettings().getWorldList();
        List<WorldTypeNode> selectable = new LinkedList<>();
        for (WorldTypeNode n : types) {
            if (((BooleanTypeNode) n.getField(WorldTypeNode.Fields.SHOW_IN_LIST)).getValue()) {
                selectable.add(n);
            }
        }
        if (selectable.isEmpty()) {
            if (types.isEmpty()) {
                return new GuiCreateCustomWorld(currentScreen, new WorldTypeNode(null));
            } else {
                return new GuiCreateCustomWorld(currentScreen, types.get(0));
            }
        } else {
            return new DefaultWorldSelectionList(currentScreen, selectable);
        }
    }
}
