package com.paulzzh.yuzu.gui.screen;

import com.paulzzh.yuzu.YuZuUI;
import com.paulzzh.yuzu.YuZuUIConfig;
import com.paulzzh.yuzu.gui.RenderUtils;
import com.paulzzh.yuzu.gui.VirtualScreen;
import com.paulzzh.yuzu.gui.widget.*;
import com.paulzzh.yuzu.integration.DWGIntegration;
import com.paulzzh.yuzu.mixininterface.MusicTickerInterface;
import com.paulzzh.yuzu.sound.SoundManager;
import com.paulzzh.yuzu.sound.SoundRegister;
import com.paulzzh.yuzu.sound.VoiceType;
import com.paulzzh.yuzu.texture.TextureConst;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiModList;
import net.minecraftforge.fml.common.Loader;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author IMG
 * @since 2025/2/16
 */
@SuppressWarnings({"CodeBlock2Expr", "PointlessArithmeticExpression"})
public class SenrenBankaTitleScreen extends GuiScreen {
    /*
    客观存在的一些属性。不需要跟着实例走。
     */
    private static final VirtualScreen VIRTUAL_SCREEN = new VirtualScreen(1920, 1080);
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(1);
    private static final List<Element> ELEMENTS = new ArrayList<>();
    private static final List<Clickable> CLICKABLES = new ArrayList<>();
    private static final List<TooltipDrawable> TOOLTIP_DRAWABLES = new ArrayList<>();
    private static long delay = 0L;
    /**
     * 存储关于跳过退出语音延迟的信息。
     * <pre>
     * -1	初始状态。
     * 0	点击退出按钮，开始延迟。
     * 1	又一次点击了屏幕任意位置。
     * 2	又又一次点击屏幕，立即关闭游戏。
     * <pre>
     */
    private short passExitSound = -1;

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


        drawRect(0, 0, screenWidth, screenHeight, 0xFF000000);
        RenderUtils.scissor(this.mc, currentX, currentY, currentWidth, currentHeight);
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.disableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        for (Element element : ELEMENTS) {
            element.render(this.mc, mouseX, mouseY, delta);
        }

        GlStateManager.disableBlend();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        if (YuZuUIConfig.tooltip) {
            String activeTooltip = null;
            for (TooltipDrawable tooltipDrawable : TOOLTIP_DRAWABLES) {
                if (tooltipDrawable.shouldDraw()) {
                    activeTooltip = tooltipDrawable.getTooltip();
                }
            }

            if (activeTooltip != null) {
                this.drawHoveringText(activeTooltip, mouseX, mouseY);
            }
        }
    }

    /**
     * 初始化 Gui。每次打开 UI 时都会调用。
     */
    @Override
    public void initGui() {
        this.mc.setConnectedToRealms(false);
        if (!YuZuUI.isShowed) {
            initWidgets();
            YuZuUI.isShowed = true;
            return;
        }
        if (YuZuUI.inGamed) {
            initWidgets();
            YuZuUI.inGamed = false;
        }
    }

    /**
     * 初始化所有组件。动画和声音重新播放，其他声音停止。
     */
    public void initWidgets() {
        if (YuZuUIConfig.bgm) {
            this.mc.getSoundHandler().stopSounds();
            ((MusicTickerInterface) this.mc.getMusicTicker()).yuZuUI$updateSoundStartTime();
        }
        this.clearWidgets();

        double a = 0.06;
        delay = YuZuUI.isShowed ? 100L : 1300L;

        // 以下数据来源于 https://github.com/paulzzh/YuZuUI-GTNH/blob/master/src/main/java/com/paulzzh/yuzu/gui/YuZuUIGuiMainMenu.java
        Layer yoshino = new Layer(TextureConst.TITLE_YOSHINO, 517, 50, 973, 1058, 1f, 0, VIRTUAL_SCREEN) {{
            setDelay(delay);
            setDuration(590L);

            setYFunction((t, now) -> {
                return (22f - 50f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 50f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        Layer murasame = new Layer(TextureConst.TITLE_MURASAME, 221, 86, 1045, 994, 1f, 0, VIRTUAL_SCREEN) {{
            setDelay(delay + 110L);
            setDuration(710L);

            setXFunction((t, now) -> {
                return (175f - 221f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 221f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        Layer mako = new Layer(TextureConst.TITLE_MAKO, 805, 386, 1118, 694, 1f, 0, VIRTUAL_SCREEN) {{
            setDelay(delay + 280L);
            setDuration(680L);

            setXFunction((t, now) -> {
                return (906f - 805f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 805f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        Layer lena = new Layer(TextureConst.TITLE_LENA, 1002, 149, 876, 1053, 1f, 0, VIRTUAL_SCREEN) {{
            setDelay(delay + 440L);
            setDuration(680L);

            setXFunction((t, now) -> {
                return (float) ((1074f - 1002f) * ((Math.pow(0.05, t) - 1) / (0.05 - 1)) + 1002f);
            });

            setYFunction((t, now) -> {
                return (float) ((27f - 149f) * ((Math.pow(0.05, t) - 1) / (0.05 - 1)) + 149f);
            });

            setAlphaFunction((t, now) -> {
                return (float) ((1f - 0f) * ((Math.pow(0.05, t) - 1) / (0.05 - 1)) + 0f);
            });
        }};

        Layer logo = new Layer(TextureConst.TITLE_LOGO, 17, 57, 442, 188, 1.067f, 0, VIRTUAL_SCREEN) {{
            setDelay(delay + 300L);
            setDuration(570L);

            setXFunction((t, now) -> {
                return (36f - 17f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 17f;
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

        Layer head = new Layer(TextureConst.TITLE_HEAD, 0, 334, 12, 687, 1f, 0, VIRTUAL_SCREEN) {{
            setDelay(delay + 1130L);
            setDuration(530L);

            setAlphaFunction((t, now) -> {
                float v = (1f - 0f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 0f;
                if (v == 1f && now != 1f) {
                    SoundManager.playVoice(mc, VoiceType.SENREN);
                }
                return v;
            });
        }};

        Layer background = new Layer(TextureConst.BACKGROUND_TEXTURE, -64, -36, 1920, 1080, 1.067f, 1, VIRTUAL_SCREEN) {{
            setDelay(delay);
            setDuration(1120L);

            setXFunction((t, now) -> {
                return (64f - 0f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) - 64f;
            });

            setYFunction((t, now) -> {
                return (36f - 0f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) - 36f;
            });

            setScaleFunction((t, now) -> {
                return (1f - 1.067f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 1.067f;
            });
        }};

        Layer all = new Layer(TextureConst.TITLE_CHARALL, 0, 0, 1920, 1080, 1f, 0, VIRTUAL_SCREEN) {{
            setDelay(delay + 1130L);
            setDuration(530L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 0f;
            });
        }};

        this.addChild(background);
        this.addChild(lena);
        this.addChild(mako);
        this.addChild(murasame);
        this.addChild(yoshino);
        this.addChild(all);
        this.addChild(logo);
        this.addChild(head);

        // 添加按钮
        int y = 346;
        int dy = 100;

        // 新建世界
        TitleScreenButton newGameButton = this.createTitleScreenButton(60, y, 207, 54,
            TextureConst.TITLE_NEW_GAME_BUTTON_NORMAL, TextureConst.TITLE_NEW_GAME_BUTTON_ON);
        newGameButton.setTooltipSupplier(() -> I18n.format("selectWorld.create"));
        newGameButton.setOnClick((button) -> {
            // 安装了 DefaultWorldGenerator
            // 判定不能丢，不然找不到方法
            if (Loader.isModLoaded(DWGIntegration.DWG_MODID)) {
                GuiScreen screen = DWGIntegration.getDWGGui(this);
                this.mc.displayGuiScreen(screen);
                return;
            }
            this.mc.displayGuiScreen(new GuiCreateWorld(this));
        });

        // 选择世界
        TitleScreenButton selectWorldButton = this.createTitleScreenButton(60, y + dy, 206, 55,
            TextureConst.TITLE_SELECT_WORLD_BUTTON_NORMAL, TextureConst.TITLE_SELECT_WORLD_BUTTON_ON);
        selectWorldButton.setTooltipSupplier(() -> I18n.format("menu.singleplayer"));
        selectWorldButton.setSound(SoundRegister.YUZU_TITLE_BUTTON_SINGLEPLAYER);
        selectWorldButton.setOnClick((button) -> {
            this.mc.displayGuiScreen(new GuiWorldSelection(this));
        });

        // 多人游戏
        TitleScreenButton continueButton = this.createTitleScreenButton(66, y + dy * 2, 313, 56,
            TextureConst.TITLE_CONTINUE_BUTTON_NORMAL, TextureConst.TITLE_CONTINUE_BUTTON_ON);
        continueButton.setTooltipSupplier(() -> I18n.format("menu.multiplayer"));
        continueButton.setSound(SoundRegister.YUZU_TITLE_BUTTON_MULTIPLAYER);
        continueButton.setOnClick((button) -> {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        });

        // Realms
        TitleScreenButton realmsButton = this.createTitleScreenButton(66, y + dy * 3, 164, 54,
            TextureConst.TITLE_REALMS_BUTTON_NORMAL, TextureConst.TITLE_REALMS_BUTTON_ON);
        realmsButton.setTooltipSupplier(() -> I18n.format(YuZuUIConfig.replaceRealms ? "options.language" : "menu.online"));
        realmsButton.setVoiceType(VoiceType.REALMS);
        realmsButton.setOnClick((button) -> {
            if (YuZuUIConfig.replaceRealms) {
                this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
            } else {
                new RealmsBridge().switchToRealms(this);
            }
        });

        // 模组列表
        TitleScreenButton modListButton = this.createTitleScreenButton(58, y + dy * 4, 211, 54,
            TextureConst.TITLE_MOD_LIST_BUTTON_NORMAL, TextureConst.TITLE_MOD_LIST_BUTTON_ON);
        modListButton.setTooltipSupplier(() -> I18n.format("fml.menu.mods"));
        modListButton.setVoiceType(VoiceType.MOD_LIST);
        modListButton.setOnClick((button) -> {
            this.mc.displayGuiScreen(new GuiModList(this));
        });

        // 设置
        TitleScreenButton optionsButton = this.createTitleScreenButton(59, y + dy * 5, 253, 56,
            TextureConst.TITLE_OPTIONS_BUTTON_NORMAL, TextureConst.TITLE_OPTIONS_BUTTON_ON);
        optionsButton.setTooltipSupplier(() -> I18n.format("menu.options"));
        optionsButton.setVoiceType(VoiceType.OPTIONS);
        optionsButton.setOnClick((button) -> {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        });

        // 退出游戏
        TitleScreenButton quitGameButton = this.createTitleScreenButton(60, y + dy * 6, 233, 54,
            TextureConst.TITLE_QUIT_GAME_BUTTON_NORMAL, TextureConst.TITLE_QUIT_GAME_BUTTON_ON);
        quitGameButton.setTooltipSupplier(() -> I18n.format(YuZuUIConfig.justExit ? "menu.quit" : "yuzu.menu.quit_to_title"));
        quitGameButton.setVoiceType(VoiceType.QUIT_GAME);
        quitGameButton.setOnClick((button) -> {
            YuZuUI.exit = true;
            if (YuZuUIConfig.justExit) {
                if (this.passExitSound == -1) {
                    this.passExitSound = 0;
                }
                CompletableFuture.completedFuture(null).whenComplete((unused, e) -> {
                    EXECUTOR.execute(() -> {
                        // 没语音就不要等了
                        if (SoundManager.getIsVoiceAvailable(this.mc)) {
                            try {
                                // 等待音效播放完成
                                for (int i = 0; i < 150; i++) {
                                    Thread.sleep(10);
                                    if (this.passExitSound == 2) {
                                        break;
                                    }
                                }
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        this.mc.shutdown();
                    });
                });
            } else {
                this.mc.displayGuiScreen(null);
            }
        });

        this.addChild(newGameButton);
        this.addChild(selectWorldButton);
        this.addChild(continueButton);
        this.addChild(realmsButton);
        this.addChild(modListButton);
        this.addChild(optionsButton);
        this.addChild(quitGameButton);
    }

    private TitleScreenButton createTitleScreenButton(float x, float y, float width, float height, ResourceLocation texture, ResourceLocation textureHover) {
        return new TitleScreenButton(x, y, width, height, texture, textureHover, VIRTUAL_SCREEN) {{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};
    }

    protected <T> void addChild(T child) {
        if (child instanceof Element element) {
            ELEMENTS.add(element);
        }
        if (child instanceof Clickable clickable) {
            CLICKABLES.add(clickable);
        }
        if (child instanceof TooltipDrawable tooltipDrawable) {
            TOOLTIP_DRAWABLES.add(tooltipDrawable);
        }
    }

    protected void clearWidgets() {
        ELEMENTS.clear();
        CLICKABLES.clear();
        TOOLTIP_DRAWABLES.clear();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        if (button != 0) return;
        if (this.passExitSound == 0 || this.passExitSound == 1) {
            this.passExitSound++;
        }
        for (Clickable clickable : CLICKABLES) {
            if (clickable.mousePressed(this.mc, mouseX, mouseY)) {
                return;
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        SoundManager.playSound(this.mc, SoundRegister.YUZU_TITLE_BUTTON_ON);
    }

    public static long getDelay() {
        return delay;
    }
}
