package com.paulzzh.yuzu.gui.screen;

import com.paulzzh.yuzu.YuZuUI;
import com.paulzzh.yuzu.YuZuUIConfig;
import com.paulzzh.yuzu.gui.IEasing;
import com.paulzzh.yuzu.gui.VirtualScreen;
import com.paulzzh.yuzu.gui.widget.Layer;
import com.paulzzh.yuzu.gui.widget.TitleScreenButton;
import com.paulzzh.yuzu.gui.widget.api.Clickable;
import com.paulzzh.yuzu.gui.widget.api.Renderable;
import com.paulzzh.yuzu.gui.widget.api.TooltipDrawable;
import com.paulzzh.yuzu.integration.DWGIntegration;
import com.paulzzh.yuzu.sound.SoundManager;
import com.paulzzh.yuzu.sound.SoundRegister;
import com.paulzzh.yuzu.sound.TitleScreenMusicTicker;
import com.paulzzh.yuzu.sound.VoiceType;
import com.paulzzh.yuzu.texture.TextureConst;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiModList;
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
@SuppressWarnings("CodeBlock2Expr")
public class SenrenBankaTitleScreen extends GuiScreen {
    // 客观存在的一些属性。不需要跟着实例走
    private static final VirtualScreen VIRTUAL_SCREEN = new VirtualScreen(1920, 1080);
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(1);
    private static final List<Renderable> RENDERABLES = new ArrayList<>();
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
        final int screenWidth = this.width;
        final int screenHeight = this.height;
        // 背景保持比例适应短边
        int currentWidth;
        int currentHeight;
        if (screenWidth * 9 > screenHeight * 16) {
            currentWidth = screenHeight * 16 / 9;
            currentHeight = screenHeight;
        } else {
            currentWidth = screenWidth;
            currentHeight = screenWidth * 9 / 16;
        }
        VIRTUAL_SCREEN.setPracticalWidth(currentWidth);
        VIRTUAL_SCREEN.setPracticalHeight(currentHeight);
        // 平移背景使其居中
        int offsetX = (screenWidth - currentWidth) / 2;
        int offsetY = (screenHeight - currentHeight) / 2;
        VIRTUAL_SCREEN.setOffsetX(offsetX);
        VIRTUAL_SCREEN.setOffsetY(offsetY);

        drawRect(0, 0, screenWidth, screenHeight, 0xFF000000);
        VIRTUAL_SCREEN.scissorAround();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        for (Renderable element : RENDERABLES) {
            element.render(mouseX, mouseY, delta);
        }

        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

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
            this.initWidgets();
            YuZuUI.isShowed = true;
            return;
        }
        if (YuZuUI.inGamed) {
            this.initWidgets();
            YuZuUI.inGamed = false;
            //return;
        }
    }

    /**
     * 初始化所有组件。动画和声音重新播放，其他声音停止。
     */
    private void initWidgets() {
        TitleScreenMusicTicker.tickBGM();
        this.clearWidgets();

        delay = YuZuUI.isShowed ? 100L : 1300L;

        IEasing ease01 = IEasing.exponentialOut(0.1);
        IEasing ease005 = IEasing.exponentialOut(0.05);
        IEasing ease06 = IEasing.exponentialOut(0.06);

        // 以下数据来源于 https://github.com/paulzzh/YuZuUI-GTNH/blob/master/src/main/java/com/paulzzh/yuzu/gui/YuZuUIGuiMainMenu.java
        Layer background = new Layer(
                VIRTUAL_SCREEN, TextureConst.BACKGROUND_TEXTURE,
                -64, -36, 1920, 1080
        ) {{
            this.setScale(1.067f);
            this.setDelay(delay);
            this.setDuration(1120L);
            this.animateX(0f, ease01);
            this.animateY(0f, ease01);
            this.animateScale(1f, ease01);
        }};
        this.addChild(background);

        Layer lena = new Layer(
                VIRTUAL_SCREEN, TextureConst.TITLE_LENA,
                1002, 149, 876, 1053
        ) {{
            this.setAlpha(0f);
            this.setDelay(delay + 440L);
            this.setDuration(680L);
            this.animateX(1074f, ease005);
            this.animateY(27f, ease005);
            this.animateAlpha(1f, ease005);
        }};
        this.addChild(lena);

        Layer mako = new Layer(
                VIRTUAL_SCREEN, TextureConst.TITLE_MAKO,
                805, 386, 1118, 694
        ) {{
            this.setAlpha(0f);
            this.setDelay(delay + 280L);
            this.setDuration(680L);
            this.animateX(906f, ease06);
            this.animateAlpha(1f, ease06);
        }};
        this.addChild(mako);

        Layer murasame = new Layer(
                VIRTUAL_SCREEN, TextureConst.TITLE_MURASAME,
                221, 86, 1045, 994
        ) {{
            this.setAlpha(0f);
            this.setDelay(delay + 110L);
            this.setDuration(710L);
            this.animateX(175f, ease06);
            this.animateAlpha(1f, ease06);
        }};
        this.addChild(murasame);

        Layer yoshino = new Layer(
                VIRTUAL_SCREEN, TextureConst.TITLE_YOSHINO,
                517, 50, 973, 1058
        ) {{
            this.setAlpha(0f);
            this.setDelay(delay);
            this.setDuration(590L);
            this.animateY(22f, ease06);
            this.animateAlpha(1f, ease06);
        }};
        this.addChild(yoshino);

        Layer all = new Layer(
                VIRTUAL_SCREEN, TextureConst.TITLE_CHARALL,
                0, 0, 1920, 1080
        ) {{
            this.setAlpha(0f);
            this.setDelay(delay + 1130L);
            this.setDuration(530L);
            this.animateAlpha(1f, ease01);
        }};
        this.addChild(all);

        Layer logo = new Layer(
                VIRTUAL_SCREEN, TextureConst.TITLE_LOGO,
                17, 57, 442, 188
        ) {{
            this.setAlpha(0f);
            this.setScale(1.067f);
            this.setDelay(delay + 300L);
            this.setDuration(570L);
            this.animateX(36f, ease01);
            this.animateY(60f, ease01);
            this.animateAlpha(1f, ease01);
            this.animateScale(1f, ease01);
        }};
        this.addChild(logo);

        Layer head = new Layer(
                VIRTUAL_SCREEN, TextureConst.TITLE_HEAD,
                0, 334, 12, 687
        ) {{
            this.setAlpha(0f);
            this.setDelay(delay + 1130L);
            this.setDuration(530L);
            this.animateAlpha(1f, ease01);
            this.setOnComplete(() -> SoundManager.playVoice(VoiceType.SENREN));
        }};
        this.addChild(head);

        // 添加按钮
        int y = 346;
        final int dy = 100;

        // 新建世界
        TitleScreenButton newGameButton = createTitleScreenButton(
                TextureConst.TITLE_NEW_GAME_BUTTON_NORMAL, TextureConst.TITLE_NEW_GAME_BUTTON_ON,
                60, y, 207, 54
        );
        newGameButton.setTooltipSupplier(() -> I18n.format("selectWorld.create"));
        newGameButton.setOnClick((button) -> {
            GuiScreen screen = DWGIntegration.tryGetDWGGui(this);
            if (screen == null) screen = new GuiCreateWorld(this);
            this.mc.displayGuiScreen(screen);
        });
        this.addChild(newGameButton);

        // 选择世界
        TitleScreenButton selectWorldButton = createTitleScreenButton(
                TextureConst.TITLE_SELECT_WORLD_BUTTON_NORMAL, TextureConst.TITLE_SELECT_WORLD_BUTTON_ON,
                60, y + dy, 206, 55
        );
        selectWorldButton.setTooltipSupplier(() -> I18n.format("menu.singleplayer"));
        selectWorldButton.setSound(SoundRegister.YUZU_TITLE_BUTTON_SINGLEPLAYER);
        selectWorldButton.setOnClick((button) -> {
            this.mc.displayGuiScreen(new GuiWorldSelection(this));
        });
        this.addChild(selectWorldButton);

        // 多人游戏
        TitleScreenButton continueButton = createTitleScreenButton(
                TextureConst.TITLE_CONTINUE_BUTTON_NORMAL, TextureConst.TITLE_CONTINUE_BUTTON_ON,
                66, y + dy * 2, 313, 56
        );
        continueButton.setTooltipSupplier(() -> I18n.format("menu.multiplayer"));
        continueButton.setSound(SoundRegister.YUZU_TITLE_BUTTON_MULTIPLAYER);
        continueButton.setOnClick((button) -> {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        });
        this.addChild(continueButton);

        // Realms
        TitleScreenButton realmsButton = createTitleScreenButton(
                TextureConst.TITLE_REALMS_BUTTON_NORMAL, TextureConst.TITLE_REALMS_BUTTON_ON,
                66, y + dy * 3, 164, 54
        );
        realmsButton.setTooltipSupplier(() -> I18n.format(YuZuUIConfig.replaceRealms ? "options.language" : "menu.online"));
        realmsButton.setVoiceType(VoiceType.REALMS);
        realmsButton.setOnClick((button) -> {
            if (YuZuUIConfig.replaceRealms) {
                this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
            } else {
                new RealmsBridge().switchToRealms(this);
            }
        });
        this.addChild(realmsButton);

        // 模组列表
        TitleScreenButton modListButton = createTitleScreenButton(
                TextureConst.TITLE_MOD_LIST_BUTTON_NORMAL, TextureConst.TITLE_MOD_LIST_BUTTON_ON,
                58, y + dy * 4, 211, 54
        );
        modListButton.setTooltipSupplier(() -> I18n.format("fml.menu.mods"));
        modListButton.setVoiceType(VoiceType.MOD_LIST);
        modListButton.setOnClick((button) -> {
            this.mc.displayGuiScreen(new GuiModList(this));
        });
        this.addChild(modListButton);

        // 设置
        TitleScreenButton optionsButton = createTitleScreenButton(
                TextureConst.TITLE_OPTIONS_BUTTON_NORMAL, TextureConst.TITLE_OPTIONS_BUTTON_ON,
                59, y + dy * 5, 253, 56
        );
        optionsButton.setTooltipSupplier(() -> I18n.format("menu.options"));
        optionsButton.setVoiceType(VoiceType.OPTIONS);
        optionsButton.setOnClick((button) -> {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        });
        this.addChild(optionsButton);

        // 退出游戏
        TitleScreenButton quitGameButton = createTitleScreenButton(
                TextureConst.TITLE_QUIT_GAME_BUTTON_NORMAL, TextureConst.TITLE_QUIT_GAME_BUTTON_ON,
                60, y + dy * 6, 233, 54
        );
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
                        if (SoundManager.isVoiceAvailable()) {
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
        this.addChild(quitGameButton);
    }

    private static TitleScreenButton createTitleScreenButton(
            ResourceLocation texture, ResourceLocation textureHover,
            float x, float y, float width, float height
    ) {
        return new TitleScreenButton(VIRTUAL_SCREEN, texture, textureHover, x, y, width, height) {{
            this.setAlpha(0f);
            this.setDelay(delay + 1670L);
            this.setDuration(570L);
            this.animateAlpha(1f, IEasing.LINEAR);
        }};
    }

    private <T> void addChild(T child) {
        if (child instanceof Renderable renderable) {
            RENDERABLES.add(renderable);
        }
        if (child instanceof Clickable clickable) {
            CLICKABLES.add(clickable);
        }
        if (child instanceof TooltipDrawable tooltipDrawable) {
            TOOLTIP_DRAWABLES.add(tooltipDrawable);
        }
    }

    private void clearWidgets() {
        RENDERABLES.clear();
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
            if (clickable.mousePressed(mouseX, mouseY, button)) {
                clickable.onClick(mouseX, mouseY, button);
                return;
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        SoundManager.playSound(SoundRegister.YUZU_TITLE_BUTTON_ON);
    }

    public static long getDelay() {
        return delay;
    }
}
