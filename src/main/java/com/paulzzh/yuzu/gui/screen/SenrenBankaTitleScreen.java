package com.paulzzh.yuzu.gui.screen;

import com.paulzzh.yuzu.YuZuUI;
import com.paulzzh.yuzu.YuZuUIConfig;
import com.paulzzh.yuzu.gui.RenderUtils;
import com.paulzzh.yuzu.gui.VirtualScreen;
import com.paulzzh.yuzu.gui.Easing;
import com.paulzzh.yuzu.gui.widget.*;
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
        int screenWidth = this.width;
        int screenHeight = this.height;
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
        int xOffset = (screenWidth - currentWidth) / 2;
        int yOffset = (screenHeight - currentHeight) / 2;
        VIRTUAL_SCREEN.setXOffset(xOffset);
        VIRTUAL_SCREEN.setYOffset(yOffset);

        drawRect(0, 0, screenWidth, screenHeight, 0xFF000000);
        RenderUtils.scissor(xOffset, yOffset, currentWidth, currentHeight);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        for (Element element : ELEMENTS) {
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
    public void initWidgets() {
        TitleScreenMusicTicker.tickBGM();
        this.clearWidgets();

        delay = YuZuUI.isShowed ? 100L : 1300L;

        Easing ease06 = Easing.exponentialOut(0.06);
        Easing ease005 = Easing.exponentialOut(0.05);
        Easing ease01 = Easing.exponentialOut(0.1);

        // 以下数据来源于 https://github.com/paulzzh/YuZuUI-GTNH/blob/master/src/main/java/com/paulzzh/yuzu/gui/YuZuUIGuiMainMenu.java
        Layer yoshino = new Layer(TextureConst.TITLE_YOSHINO, 517, 50, 973, 1058, VIRTUAL_SCREEN) {{
            this.setAlpha(0f);
            this.setDelay(delay);
            this.setDuration(590L);
            this.animateY(22f, ease06);
            this.animateAlpha(1f, ease06);
        }};

        Layer murasame = new Layer(TextureConst.TITLE_MURASAME, 221, 86, 1045, 994, VIRTUAL_SCREEN) {{
            this.setAlpha(0f);
            this.setDelay(delay + 110L);
            this.setDuration(710L);
            this.animateX(175f, ease06);
            this.animateAlpha(1f, ease06);
        }};

        Layer mako = new Layer(TextureConst.TITLE_MAKO, 805, 386, 1118, 694, VIRTUAL_SCREEN) {{
            this.setAlpha(0f);
            this.setDelay(delay + 280L);
            this.setDuration(680L);
            this.animateX(906f, ease06);
            this.animateAlpha(1f, ease06);
        }};

        Layer lena = new Layer(TextureConst.TITLE_LENA, 1002, 149, 876, 1053, VIRTUAL_SCREEN) {{
            this.setAlpha(0f);
            this.setDelay(delay + 440L);
            this.setDuration(680L);
            this.animateX(1074f, ease005);
            this.animateY(27f, ease005);
            this.animateAlpha(1f, ease005);
        }};

        Layer logo = new Layer(TextureConst.TITLE_LOGO, 17, 57, 442, 188, VIRTUAL_SCREEN) {{
            this.setAlpha(0f);
            this.setScale(1.067f);
            this.setDelay(delay + 300L);
            this.setDuration(570L);
            this.animateX(36f, ease01);
            this.animateY(60f, ease01);
            this.animateAlpha(1f, ease01);
            this.animateScale(1f, ease01);
        }};

        Layer head = new Layer(TextureConst.TITLE_HEAD, 0, 334, 12, 687, VIRTUAL_SCREEN) {{
            this.setAlpha(0f);
            this.setDelay(delay + 1130L);
            this.setDuration(530L);
            this.animateAlpha(1f, ease01);
            this.setOnComplete(() -> SoundManager.playVoice(VoiceType.SENREN));
        }};

        Layer background = new Layer(TextureConst.BACKGROUND_TEXTURE, -64, -36, 1920, 1080, VIRTUAL_SCREEN) {{
            this.setScale(1.067f);
            this.setDelay(delay);
            this.setDuration(1120L);
            this.animateX(0f, ease01);
            this.animateY(0f, ease01);
            this.animateScale(1f, ease01);
        }};

        Layer all = new Layer(TextureConst.TITLE_CHARALL, 0, 0, 1920, 1080, VIRTUAL_SCREEN) {{
            this.setAlpha(0f);
            this.setDelay(delay + 1130L);
            this.setDuration(530L);
            this.animateAlpha(1f, ease01);
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
            GuiScreen screen = DWGIntegration.tryGetDWGGui(this);
            if (screen == null) screen = new GuiCreateWorld(this);
            this.mc.displayGuiScreen(screen);
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
            this.setAlpha(0f);
            this.setDelay(delay + 1670L);
            this.setDuration(570L);
            this.animateAlpha(1f, Easing.LINEAR);
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
            if (clickable.mousePressed(mouseX, mouseY)) {
                return;
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        SoundManager.playSound(SoundRegister.YUZU_TITLE_BUTTON_ON);
    }

    public static long getDelay() {
        return delay;
    }
}
