package com.paulzzh.yuzu.texture;

import com.paulzzh.yuzu.Tags;
import net.minecraft.util.ResourceLocation;

/**
 * @author IMG
 * @since 2025/2/16
 */
public class TextureConst {
    public static final ResourceLocation TITLE_CHARALL = newGuiResource("title_charall.png");
    public static final ResourceLocation BACKGROUND_TEXTURE = newGuiResource("title_background.png");
    public static final ResourceLocation TITLE_YOSHINO = newGuiResource("title_yoshino.png");
    public static final ResourceLocation TITLE_MURASAME = newGuiResource("title_murasame.png");
    public static final ResourceLocation TITLE_MAKO = newGuiResource("title_mako.png");
    public static final ResourceLocation TITLE_LENA = newGuiResource("title_lena.png");
    public static final ResourceLocation TITLE_LOGO = newGuiResource("title_logo.png");
    public static final ResourceLocation TITLE_HEAD = newGuiResource("title_head.png");

    public static final ResourceLocation TITLE_NEW_GAME_BUTTON_NORMAL = newGuiResource("title_new_game_button_normal.png");
    public static final ResourceLocation TITLE_NEW_GAME_BUTTON_ON = newGuiResource("title_new_game_button_on.png");

    public static final ResourceLocation TITLE_SELECT_WORLD_BUTTON_NORMAL = newGuiResource("title_select_world_button_normal.png");
    public static final ResourceLocation TITLE_SELECT_WORLD_BUTTON_ON = newGuiResource("title_select_world_button_on.png");

    public static final ResourceLocation TITLE_OPTIONS_BUTTON_NORMAL = newGuiResource("title_options_button_normal.png");
    public static final ResourceLocation TITLE_OPTIONS_BUTTON_ON = newGuiResource("title_options_button_on.png");

    public static final ResourceLocation TITLE_QUIT_GAME_BUTTON_NORMAL = newGuiResource("title_quit_game_button_normal.png");
    public static final ResourceLocation TITLE_QUIT_GAME_BUTTON_ON = newGuiResource("title_quit_game_button_on.png");

    public static final ResourceLocation TITLE_CONTINUE_BUTTON_NORMAL = newGuiResource("title_continue_button_normal.png");
    public static final ResourceLocation TITLE_CONTINUE_BUTTON_ON = newGuiResource("title_continue_button_on.png");

    public static final ResourceLocation TITLE_REALMS_BUTTON_NORMAL = newGuiResource("title_realms_button_normal.png");
    public static final ResourceLocation TITLE_REALMS_BUTTON_ON = newGuiResource("title_realms_button_on.png");

    public static final ResourceLocation TITLE_MOD_LIST_BUTTON_NORMAL = newGuiResource("title_mod_list_button_normal.png");
    public static final ResourceLocation TITLE_MOD_LIST_BUTTON_ON = newGuiResource("title_mod_list_button_on.png");

    private static ResourceLocation newGuiResource(String path) {
        return new ResourceLocation(Tags.MOD_ID, "textures/gui/" + path);
    }
}
