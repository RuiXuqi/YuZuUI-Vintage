package com.paulzzh.yuzu.gui;

import com.img.gui.Layer;
import com.img.gui.TitleScreenButton;
import com.img.gui.VirtualScreen;
import com.paulzzh.yuzu.YuZuUI;
import com.paulzzh.yuzu.config.YuZuUIConfig;
import cpw.mods.fml.client.GuiModList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.concurrent.CompletableFuture;

import static com.paulzzh.yuzu.YuZuUI.exit;
import static com.paulzzh.yuzu.YuZuUI.inGamed;
import static java.lang.Thread.sleep;

public class YuZuUIGuiMainMenu extends GuiScreen {
    private static final ResourceLocation YUZU_TITLE_MUSIC = new ResourceLocation(YuZuUI.MODID, "yuzu_title_music");
    private static final ResourceLocation YUZU_TITLE_BUTTON_CLICK = new ResourceLocation(YuZuUI.MODID, "yuzu_title_button_click");
    private static final ResourceLocation YUZU_TITLE_BUTTON_NEW_GAME = new ResourceLocation(YuZuUI.MODID, "yuzu_title_button_new_game");
    private static final ResourceLocation YUZU_TITLE_BUTTON_SELECT_WORLD = new ResourceLocation(YuZuUI.MODID, "yuzu_title_button_select_world");
    private static final ResourceLocation YUZU_TITLE_BUTTON_ON = new ResourceLocation(YuZuUI.MODID, "yuzu_title_button_on");
    private static final ResourceLocation YUZU_TITLE_BUTTON_OPTIONS = new ResourceLocation(YuZuUI.MODID, "yuzu_title_button_options");
    private static final ResourceLocation YUZU_TITLE_BUTTON_QUIT_GAME = new ResourceLocation(YuZuUI.MODID, "yuzu_title_button_quit_game");
    private static final ResourceLocation YUZU_TITLE_SENREN = new ResourceLocation(YuZuUI.MODID, "yuzu_title_senren");
    private static final ResourceLocation YUZU_TITLE_BUTTON_REALMS = new ResourceLocation(YuZuUI.MODID, "yuzu_title_button_realms");
    private static final ResourceLocation YUZU_TITLE_BUTTON_MOD_LIST = new ResourceLocation(YuZuUI.MODID, "yuzu_title_button_mod_list");
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(YuZuUI.MODID, "textures/gui/background.png");
    private static final ResourceLocation TITLE_YOSHINO = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_yoshino.png");
    private static final ResourceLocation TITLE_MURASAME = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_murasame.png");
    private static final ResourceLocation TITLE_MAKO = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_mako.png");
    private static final ResourceLocation TITLE_LENA = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_lena.png");
    private static final ResourceLocation TITLE_LOGO = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_logo.png");
    private static final ResourceLocation TITLE_HEAD = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_head.png");
    private static final ResourceLocation TITLE_NEW_GAME_BUTTON_NORMAL = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_new_game_button_normal.png");
    private static final ResourceLocation TITLE_NEW_GAME_BUTTON_ON = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_new_game_button_on.png");
    private static final ResourceLocation TITLE_SELECT_WORLD_BUTTON_NORMAL = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_select_world_button_normal.png");
    private static final ResourceLocation TITLE_SELECT_WORLD_BUTTON_ON = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_select_world_button_on.png");
    private static final ResourceLocation TITLE_OPTIONS_BUTTON_NORMAL = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_options_button_normal.png");
    private static final ResourceLocation TITLE_OPTIONS_BUTTON_ON = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_options_button_on.png");
    private static final ResourceLocation TITLE_QUIT_GAME_BUTTON_NORMAL = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_quit_game_button_normal.png");
    private static final ResourceLocation TITLE_QUIT_GAME_BUTTON_ON = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_quit_game_button_on.png");
    private static final ResourceLocation TITLE_CONTINUE_BUTTON_NORMAL = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_continue_button_normal.png");
    private static final ResourceLocation TITLE_CONTINUE_BUTTON_ON = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_continue_button_on.png");
    private static final ResourceLocation TITLE_REAMLS_BUTTON_NORMAL = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_realms_button_normal.png");
    private static final ResourceLocation TITLE_REAMLS_BUTTON_ON = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_realms_button_on.png");
    private static final ResourceLocation TITLE_MOD_LIST_BUTTON_NORMAL = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_mod_list_button_normal.png");
    private static final ResourceLocation TITLE_MOD_LIST_BUTTON_ON = new ResourceLocation(YuZuUI.MODID, "textures/gui/title_mod_list_button_on.png");
    private static final VirtualScreen VIRTUAL_SCREEN = new VirtualScreen(1920, 1080);
    private static final double a = 0.06;
    protected static Minecraft mc;
    // 人物立绘
    private static Layer yoshinoLayer;
    private static Layer murasameLayer;
    private static Layer makoLayer;
    private static Layer lenaLayer;
    // logo
    private static Layer logoLayer;
    // 左边菜单
    private static Layer headLayer;
    // 按钮
    private static TitleScreenButton newGameButton;
    private static TitleScreenButton selectWorldButton;
    private static TitleScreenButton continueButton;
    private static TitleScreenButton realmsButton;
    private static TitleScreenButton modListButton;
    private static TitleScreenButton optionsButton;
    private static TitleScreenButton quitGameButton;
    private static int y = 346;
    private static int dy = 100;
    private static Long delay = 1500L;

    private static PositionedSoundRecord ISOUND_TITLE;

    public YuZuUIGuiMainMenu() {
        mc = Minecraft.getMinecraft();
        initWidgets();
    }

    public static void tickSound() {
        if (!exit && (ISOUND_TITLE == null || !mc.getSoundHandler().isSoundPlaying(ISOUND_TITLE))) {
            ISOUND_TITLE = PositionedSoundRecord.func_147674_a(YUZU_TITLE_MUSIC, 1.0F);
            mc.getSoundHandler().playSound(ISOUND_TITLE);
        }
    }

    @Override
    public void initGui() {
        mc = Minecraft.getMinecraft();
        createNormalMenuOptions();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        if (button == 0) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.func_147673_a(YUZU_TITLE_BUTTON_CLICK));
            if (newGameButton != null) {
                newGameButton.mousePressed(mouseX, mouseY);
            }
            if (selectWorldButton != null) {
                selectWorldButton.mousePressed(mouseX, mouseY);
            }
            if (continueButton != null) {
                continueButton.mousePressed(mouseX, mouseY);
            }
            if (realmsButton != null) {
                realmsButton.mousePressed(mouseX, mouseY);
            }
            if (optionsButton != null) {
                optionsButton.mousePressed(mouseX, mouseY);
            }
            if (quitGameButton != null) {
                quitGameButton.mousePressed(mouseX, mouseY);
            }
            if (modListButton != null) {
                modListButton.mousePressed(mouseX, mouseY);
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        mc.getSoundHandler().playSound(PositionedSoundRecord.func_147673_a(YUZU_TITLE_BUTTON_ON));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float delta) {
        // 背景图片保持比例
        int screenWidth = this.width;
        int screenHeight = this.height;
        // 背景适应后的数据
        int currentWidth;
        int currentHeight;
        if (screenWidth * 9 > screenHeight * 16) {
            currentWidth = screenHeight * 16 / 9;
            currentHeight = screenHeight;
        } else {
            currentWidth = screenWidth;
            currentHeight = screenWidth * 9 / 16;
        }
        int currentX = (screenWidth - currentWidth) / 2;
        int currentY = (screenHeight - currentHeight) / 2;

        VIRTUAL_SCREEN.setPracticalWidth(currentWidth);
        VIRTUAL_SCREEN.setPracticalHeight(currentHeight);
        VIRTUAL_SCREEN.setCurrentX(currentX);
        VIRTUAL_SCREEN.setCurrentY(currentY);

        // 绘制黑色背景
        drawRect(0, 0, screenWidth, screenHeight, 0xFF000000);

        // 开启裁剪
        //GL11.glEnable(GL11.GL_SCISSOR_TEST);
        //GL11.glScissor(currentX, currentY, (int) VIRTUAL_SCREEN.toPracticalX(1920), (int) VIRTUAL_SCREEN.toPracticalY(1080));

        // 开启混合处理半透明
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        // 绘制背景
        RenderUtils.blit(BACKGROUND_TEXTURE, currentX, currentY, currentWidth, currentHeight);

        // 绘制人物立绘
        if (lenaLayer != null) {
            lenaLayer.render(mouseX, mouseY, delta);
        }
        if (makoLayer != null) {
            makoLayer.render(mouseX, mouseY, delta);
        }
        if (murasameLayer != null) {
            murasameLayer.render(mouseX, mouseY, delta);
        }
        if (yoshinoLayer != null) {
            yoshinoLayer.render(mouseX, mouseY, delta);
        }

        // 绘制左边的菜单
        if (headLayer != null) {
            headLayer.render(mouseX, mouseY, delta);
        }

        // 绘制按钮
        if (newGameButton != null) {
            newGameButton.render(mouseX, mouseY, delta);
        }
        if (selectWorldButton != null) {
            selectWorldButton.render(mouseX, mouseY, delta);
        }
        if (continueButton != null) {
            continueButton.render(mouseX, mouseY, delta);
        }
        if (realmsButton != null) {
            realmsButton.render(mouseX, mouseY, delta);
        }
        if (optionsButton != null) {
            optionsButton.render(mouseX, mouseY, delta);
        }
        if (quitGameButton != null) {
            quitGameButton.render(mouseX, mouseY, delta);
        }
        if (modListButton != null) {
            modListButton.render(mouseX, mouseY, delta);
        }

        // 绘制logo
        if (logoLayer != null) {
            logoLayer.render(mouseX, mouseY, delta);
        }

        tick();

        //GL11.glDisable(GL11.GL_SCISSOR_TEST);
        //GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void tick() {
        if (YuZuUIConfig.bgm) {
            tickSound();
        }

        if (yoshinoLayer != null) {
            yoshinoLayer.tick();
        }
        if (murasameLayer != null) {
            murasameLayer.tick();
        }
        if (makoLayer != null) {
            makoLayer.tick();
        }
        if (lenaLayer != null) {
            lenaLayer.tick();
        }
        if (headLayer != null) {
            headLayer.tick();
        }
        if (logoLayer != null) {
            logoLayer.tick();
        }
        if (newGameButton != null) {
            newGameButton.tick();
        }
        if (selectWorldButton != null) {
            selectWorldButton.tick();
        }
        if (continueButton != null) {
            continueButton.tick();
        }
        if (realmsButton != null) {
            realmsButton.tick();
        }
        if (optionsButton != null) {
            optionsButton.tick();
        }
        if (quitGameButton != null) {
            quitGameButton.tick();
        }
        if (modListButton != null) {
            modListButton.tick();
        }
    }

    private void createNormalMenuOptions() {
        if (inGamed) {
            delay = 200L;
            initWidgets();
            inGamed = false;
        }

        // 设置按钮的点击事件
        if (newGameButton != null) {
            newGameButton.setOnClick((button) -> {
                mc.displayGuiScreen(new GuiCreateWorld(this));
            });
        }

        if (selectWorldButton != null) {
            selectWorldButton.setOnClick((button) -> {
                mc.getSoundHandler().playSound(PositionedSoundRecord.func_147673_a(YUZU_TITLE_BUTTON_SELECT_WORLD));
                mc.displayGuiScreen(new GuiSelectWorld(this));
            });
        }

        if (continueButton != null) {
            continueButton.setOnClick((button) -> {
                mc.displayGuiScreen(new GuiMultiplayer(this));
            });
        }

        if (realmsButton != null) {
            realmsButton.setOnClick((button) -> {
                mc.getSoundHandler().playSound(PositionedSoundRecord.func_147673_a(YUZU_TITLE_BUTTON_REALMS));
                //new RealmsBridge().switchToRealms(this);
                mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
            });
        }

        if (optionsButton != null) {
            optionsButton.setOnClick((button) -> {
                mc.getSoundHandler().playSound(PositionedSoundRecord.func_147673_a(YUZU_TITLE_BUTTON_OPTIONS));
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
            });
        }

        if (quitGameButton != null) {
            quitGameButton.setOnClick((button) -> {
                exit = true;
                mc.getSoundHandler().playSound(PositionedSoundRecord.func_147673_a(YUZU_TITLE_BUTTON_QUIT_GAME));
                if (YuZuUIConfig.just_exit) {
                    CompletableFuture.completedFuture(null).whenComplete((unused, e) -> {
                        try {
                            // 等待音效播放完成
                            sleep(1500);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        mc.shutdown();
                    });
                } else {
                    if (ISOUND_TITLE != null) {
                        mc.getSoundHandler().stopSound(ISOUND_TITLE);
                    }
                    mc.displayGuiScreen(null);
                }
            });
        }

        if (modListButton != null) {
            modListButton.setOnClick((button) -> {
                mc.getSoundHandler().playSound(PositionedSoundRecord.func_147673_a(YUZU_TITLE_BUTTON_MOD_LIST));
                mc.displayGuiScreen(new GuiModList(this));
            });
        }
    }

    /**
     * 初始化组件
     */
    private void initWidgets() {
        if (YuZuUIConfig.bgm) {
            mc.getSoundHandler().stopSounds();
        }
        yoshinoLayer = new Layer(TITLE_YOSHINO, 504, 50, 973, 1058, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN) {{
            setDelay(delay);
            setDuration(590L);

            setYFunction((t, now) -> {
                return (25f - 50f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 50f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        murasameLayer = new Layer(TITLE_MURASAME, 221, 90, 1045, 994, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN) {{
            setDelay(delay + 110L);
            setDuration(710L);

            setXFunction((t, now) -> {
                return (173f - 221f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 221f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        makoLayer = new Layer(TITLE_MAKO, 805, 387, 1118, 694, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN) {{
            setDelay(delay + 280L);
            setDuration(680L);

            setXFunction((t, now) -> {
                return (898f - 805f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 805f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        lenaLayer = new Layer(TITLE_LENA, 1002, 149, 876, 1053, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN) {{
            setDelay(delay + 440L);
            setDuration(680L);

            setXFunction((t, now) -> {
                return (float) ((1065f - 1002f) * ((Math.pow(0.05, t) - 1) / (0.05 - 1)) + 1002f);
            });

            setYFunction((t, now) -> {
                return (float) ((27f - 149f) * ((Math.pow(0.05, t) - 1) / (0.05 - 1)) + 149f);
            });

            setAlphaFunction((t, now) -> {
                return (float) ((1f - 0f) * ((Math.pow(0.05, t) - 1) / (0.05 - 1)) + 0f);
            });
        }};

        logoLayer = new Layer(TITLE_LOGO, 17, 57, 442, 188, 1.067f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN) {{
            setDelay(delay + 300L);
            setDuration(570L);

            setXFunction((t, now) -> {
                return (31f - 17f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 17f;
            });

            setYFunction((t, now) -> {
                return (60f - 57f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 57f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 0f;
            });

            setScaleFunction((t, now) -> {
                return (1f - 1.067f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 1.067f;
            });
        }};

        headLayer = new Layer(TITLE_HEAD, 0, 0, 600, 1080, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN) {{
            setDelay(delay + 1130L);
            setDuration(530L);

            setAlphaFunction((t, now) -> {
                float v = (1f - 0f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 0f;
                if (v == 1f && now != 1f) {
                    mc.getSoundHandler().playSound(PositionedSoundRecord.func_147673_a(YUZU_TITLE_SENREN));

                }
                return v;
            });
        }};

        newGameButton = new TitleScreenButton(60, y, 207, 54, TITLE_NEW_GAME_BUTTON_NORMAL, TITLE_NEW_GAME_BUTTON_ON, VIRTUAL_SCREEN, 0f) {{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        selectWorldButton = new TitleScreenButton(60, y + dy, 206, 55, TITLE_SELECT_WORLD_BUTTON_NORMAL, TITLE_SELECT_WORLD_BUTTON_ON, VIRTUAL_SCREEN, 0f) {{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        continueButton = new TitleScreenButton(66, y + dy * 2, 313, 56, TITLE_CONTINUE_BUTTON_NORMAL, TITLE_CONTINUE_BUTTON_ON, VIRTUAL_SCREEN, 0f) {{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        realmsButton = new TitleScreenButton(66, y + dy * 3, 164, 54, TITLE_REAMLS_BUTTON_NORMAL, TITLE_REAMLS_BUTTON_ON, VIRTUAL_SCREEN, 0f) {{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        modListButton = new TitleScreenButton(58, y + dy * 4, 211, 54, TITLE_MOD_LIST_BUTTON_NORMAL, TITLE_MOD_LIST_BUTTON_ON, VIRTUAL_SCREEN, 0f) {{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        optionsButton = new TitleScreenButton(59, y + dy * 5, 253, 56, TITLE_OPTIONS_BUTTON_NORMAL, TITLE_OPTIONS_BUTTON_ON, VIRTUAL_SCREEN, 0f) {{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        quitGameButton = new TitleScreenButton(60, y + dy * 6, 233, 54, TITLE_QUIT_GAME_BUTTON_NORMAL, TITLE_QUIT_GAME_BUTTON_ON, VIRTUAL_SCREEN, 0f) {{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};
    }
}
