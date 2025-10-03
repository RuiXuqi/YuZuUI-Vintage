package com.paulzzh.yuzu.gui;

import com.paulzzh.yuzu.YuZuUIConfig;
import com.paulzzh.yuzu.sound.VoiceManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.client.GuiModList;
import org.lwjgl.opengl.GL11;

//import java.lang.reflect.Constructor;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;

import static com.paulzzh.yuzu.YuZuUI.exit;
import static com.paulzzh.yuzu.YuZuUI.inGamed;
import static com.paulzzh.yuzu.constant.TextureConst.*;
import static com.paulzzh.yuzu.sound.InitSounds.*;
import static java.lang.Thread.sleep;

public class YuZuUIGuiMainMenu extends GuiScreen {
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
    // bg
    private static Layer stage0;
    private static Layer stage1;
    // 按钮
    private static TitleScreenButton newGameButton;
    private static TitleScreenButton selectWorldButton;
    private static TitleScreenButton continueButton;
    private static TitleScreenButton realmsButton;
    private static TitleScreenButton modListButton;
    private static TitleScreenButton optionsButton;
    private static TitleScreenButton quitGameButton;
    private static final int y = 346;
    private static final int dy = 100;
    private static Long delay = 1500L;
    private static Long soundStartTime = null;

    private static PositionedSoundRecord ISOUND_TITLE;
//    private static Constructor<?> GuiCreateCustomWorld;
    private int stage;

    public YuZuUIGuiMainMenu() {
        mc = Minecraft.getMinecraft();
        stage = 0;
//        try {
//            Class<?> clazz = Class.forName("com.fireball1725.defaultworldgenerator.gui.GuiCreateCustomWorld");
//            GuiCreateCustomWorld = clazz.getConstructor(GuiScreen.class);
//        } catch (Exception ignored) {
//        }
        initWidgets();
    }

    public static void tickSound() {
        if (!exit && (ISOUND_TITLE == null || !mc.getSoundHandler().isSoundPlaying(ISOUND_TITLE))) {
            long currentTime = Instant.now().toEpochMilli();
            if (soundStartTime == null) {
                soundStartTime = currentTime;
            }
            if (currentTime - soundStartTime > delay) {
                ISOUND_TITLE = PositionedSoundRecord.getMusicRecord(YUZU_TITLE_MUSIC);
                mc.getSoundHandler().playSound(ISOUND_TITLE);
                soundStartTime = null;
            }
        }
    }

    private static boolean playVoice(VoiceManager.VoiceType voice) {
        return playVoice(VoiceManager.getVoice(voice));
    }

    private static boolean playVoice(SoundEvent voice) {
        if (YuZuUIConfig.voice && voice != null) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(voice, 1.0F));
            return true;
        } else {
            return false;
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
        mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(YUZU_TITLE_BUTTON_ON, 1.0F));
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

        if (stage <= 1) {
            // 绘制背景
            if (stage0 != null) {
                stage0.render(mouseX, mouseY, delta);
            }

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
        }

        // 绘制背景
        if (stage1 != null) {
            stage1.render(mouseX, mouseY, delta);
        }

        // 绘制logo
        if (logoLayer != null) {
            logoLayer.render(mouseX, mouseY, delta);
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

        // 绘制黑色背景
        if (currentX == 0 && currentY != 0) {
            drawRect(0, 0, screenWidth, currentY, 0xFF000000);
            drawRect(0, currentY + currentHeight, screenWidth, screenHeight, 0xFF000000);
        }
        if (currentY == 0 && currentX != 0) {
            drawRect(0, 0, currentX, screenHeight, 0xFF000000);
            drawRect(currentX + currentWidth, 0, screenWidth, screenHeight, 0xFF000000);
        }

        GL11.glDisable(GL11.GL_BLEND);

        if (YuZuUIConfig.bgm) {
            tickSound();
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
                playVoice(YUZU_TITLE_BUTTON_NEW_GAME);
//                // 安装了 DefaultWorldGenerator
//                if (GuiCreateCustomWorld != null) {
//                    try {
//                        mc.displayGuiScreen((GuiScreen) GuiCreateCustomWorld.newInstance(this));
//                    } catch (Exception ignored) {
//                        // ..? 改为打开选择世界，避免锁定世界生成器失效
//                        mc.displayGuiScreen(new GuiWorldSelection(this));
//                    }
//                } else {
                    mc.displayGuiScreen(new GuiCreateWorld(this));
//                }
            });
        }

        if (selectWorldButton != null) {
            selectWorldButton.setOnClick((button) -> {
                playVoice(VoiceManager.VoiceType.SELECT_WORLD);
                mc.displayGuiScreen(new GuiWorldSelection(this));
            });
        }

        if (continueButton != null) {
            continueButton.setOnClick((button) -> {
                mc.displayGuiScreen(new GuiMultiplayer(this));
            });
        }

        if (realmsButton != null) {
            realmsButton.setOnClick((button) -> {
                playVoice(VoiceManager.VoiceType.REALMS);
                if (YuZuUIConfig.replaceRealms){
                    mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
                } else {
                    new RealmsBridge().switchToRealms(this);
                }
            });
        }

        if (optionsButton != null) {
            optionsButton.setOnClick((button) -> {
                playVoice(VoiceManager.VoiceType.OPTIONS);
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
            });
        }

        if (quitGameButton != null) {
            quitGameButton.setOnClick((button) -> {
                exit = true;
                if (playVoice(VoiceManager.VoiceType.QUIT_GAME) && YuZuUIConfig.justExit) {
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
                playVoice(VoiceManager.VoiceType.MOD_LIST);
                mc.displayGuiScreen(new GuiModList(this));
            });
        }
    }

    /**
     * 初始化组件
     */
    private void initWidgets() {
        stage = 0;

        if (YuZuUIConfig.bgm) {
            mc.getSoundHandler().stopSounds();
        }

        stage0 = new Layer(BACKGROUND_TEXTURE, -64, -36, 1920, 1080, 1.067f, 0, 0, 256, 256, 256, 256, 1, VIRTUAL_SCREEN) {{
            setDelay(delay);
            setDuration(1120L);

            setXFunction((t, now) -> {
                return (64f - 0f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) - 64f;
            });

            setYFunction((t, now) -> {
                return (36f - 0f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) - 36f;
            });

            setScaleFunction((t, now) -> {
                float v = (1f - 1.067f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 1.067f;
                if (v == 1f && now != 1f) {
                    stage = 1;
                }
                return v;
            });
        }};

        stage1 = new Layer(TITLE_CHARALL, 0, 0, 1920, 1080, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN) {{
            setDelay(delay + 1130L);
            setDuration(530L);

            setAlphaFunction((t, now) -> {
                float v = (1f - 0f) * (float) ((Math.pow(0.1, t) - 1) / (0.1 - 1)) + 0f;
                if (v == 1f && now != 1f) {
                    stage = 2;
                    playVoice(VoiceManager.VoiceType.SENREN);
                }
                return v;
            });
        }};

        yoshinoLayer = new Layer(TITLE_YOSHINO, 517, 50, 973, 1058, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN) {{
            setDelay(delay);
            setDuration(590L);

            setYFunction((t, now) -> {
                return (22f - 50f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 50f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        murasameLayer = new Layer(TITLE_MURASAME, 221, 86, 1045, 994, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN) {{
            setDelay(delay + 110L);
            setDuration(710L);

            setXFunction((t, now) -> {
                return (175f - 221f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 221f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        makoLayer = new Layer(TITLE_MAKO, 805, 386, 1118, 694, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN) {{
            setDelay(delay + 280L);
            setDuration(680L);

            setXFunction((t, now) -> {
                return (906f - 805f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 805f;
            });

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * (float) ((Math.pow(a, t) - 1) / (a - 1)) + 0f;
            });
        }};

        lenaLayer = new Layer(TITLE_LENA, 1002, 149, 876, 1053, 1f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN) {{
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

        logoLayer = new Layer(TITLE_LOGO, 17, 57, 437, 184, 1.067f, 0, 0, 256, 256, 256, 256, 0, VIRTUAL_SCREEN) {{
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

        newGameButton = new TitleScreenButton(60, y, 207, 54, TITLE_NEW_GAME_BUTTON_NORMAL, TITLE_NEW_GAME_BUTTON_ON, VIRTUAL_SCREEN, 0f, 1) {{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        selectWorldButton = new TitleScreenButton(60, y + dy, 206, 55, TITLE_SELECT_WORLD_BUTTON_NORMAL, TITLE_SELECT_WORLD_BUTTON_ON, VIRTUAL_SCREEN, 0f, 1) {{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        continueButton = new TitleScreenButton(66, y + dy * 2, 313, 56, TITLE_CONTINUE_BUTTON_NORMAL, TITLE_CONTINUE_BUTTON_ON, VIRTUAL_SCREEN, 0f, 2) {{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        realmsButton = new TitleScreenButton(66, y + dy * 3, 164, 54, TITLE_REALMS_BUTTON_NORMAL, TITLE_REALMS_BUTTON_ON, VIRTUAL_SCREEN, 0f, 0) {{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        modListButton = new TitleScreenButton(58, y + dy * 4, 211, 54, TITLE_MOD_LIST_BUTTON_NORMAL, TITLE_MOD_LIST_BUTTON_ON, VIRTUAL_SCREEN, 0f, 0) {{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        optionsButton = new TitleScreenButton(59, y + dy * 5, 253, 56, TITLE_OPTIONS_BUTTON_NORMAL, TITLE_OPTIONS_BUTTON_ON, VIRTUAL_SCREEN, 0f, 0) {{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};

        quitGameButton = new TitleScreenButton(60, y + dy * 6, 233, 54, TITLE_QUIT_GAME_BUTTON_NORMAL, TITLE_QUIT_GAME_BUTTON_ON, VIRTUAL_SCREEN, 0f, 0) {{
            setDelay(delay + 1670L);
            setDuration(570L);

            setAlphaFunction((t, now) -> {
                return (1f - 0f) * t + 0f;
            });
        }};
    }
}
