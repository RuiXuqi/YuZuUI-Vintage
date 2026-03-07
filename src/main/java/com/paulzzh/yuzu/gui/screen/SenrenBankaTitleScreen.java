package com.paulzzh.yuzu.gui.screen;

import com.google.gson.*;
import com.paulzzh.yuzu.YuZuUI;
import com.paulzzh.yuzu.YuZuUIConfig;
import com.paulzzh.yuzu.gui.IEasing;
import com.paulzzh.yuzu.gui.JsonParseUtils;
import com.paulzzh.yuzu.gui.VirtualScreen;
import com.paulzzh.yuzu.gui.widget.AnimatedElement;
import com.paulzzh.yuzu.gui.widget.Element;
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
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.client.GuiModList;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author IMG
 * @since 2025/2/16
 */
public class SenrenBankaTitleScreen extends GuiScreen {
    // 客观存在的一些属性。不需要跟着实例走
    private static final VirtualScreen VIRTUAL_SCREEN = new VirtualScreen();
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

    private static JsonObject uiJsonCache;
    private static int backgroundColor;
    private static long baseDelay;
    private static long showedDelay;
    private static @Nullable String fallbackLang;

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

        drawRect(0, 0, screenWidth, screenHeight, backgroundColor);
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

    private void initWidgets() {
        this.clearWidgets();

        delay = YuZuUI.isShowed ? showedDelay : baseDelay;

        final String currentLang = this.mc.getLanguageManager().getCurrentLanguage().getLanguageCode().toLowerCase(Locale.ENGLISH);
        for (JsonElement element : uiJsonCache.getAsJsonArray("elements")) {
            JsonObject obj = element.getAsJsonObject();
            String id = obj.get("id").getAsString();
            try {
                String type = JsonParseUtils.tryGet(obj, "type").getAsString();
                JsonObject variants = obj.getAsJsonObject("variants");
                JsonObject fallback = variants.has(fallbackLang) ?
                        variants.getAsJsonObject(fallbackLang) : variants.entrySet().iterator().next().getValue().getAsJsonObject();
                JsonObject current = variants.has(currentLang) ?
                        variants.getAsJsonObject(currentLang) : null;
                JsonObject variant = JsonParseUtils.mergeVariant(fallback, current);
                if (variant == null) throw new JsonParseException("No variant found!");
                this.buildWidget(variant, type);
            } catch (JsonParseException e) {
                YuZuUI.LOG.error("Error when parsing element '{}'. Skipping...", id, e);
            }
        }
    }

    private void buildWidget(JsonObject variant, String type) throws JsonParseException {
        JsonArray vPos = JsonParseUtils.tryGetAsJsonArray(variant, "pos");
        float vX = vPos.get(0).getAsFloat();
        float vY = vPos.get(1).getAsFloat();
        JsonArray vSize = JsonParseUtils.tryGetAsJsonArray(variant, "size");
        float vWidth = vSize.get(0).getAsFloat();
        float vHeight = vSize.get(1).getAsFloat();

        JsonElement textures = JsonParseUtils.tryGet(variant, "textures");
        ResourceLocation normalTex;
        ResourceLocation hoverTex;
        if (textures.isJsonPrimitive()) {
            normalTex = new ResourceLocation(textures.getAsString() + ".png");
            hoverTex = normalTex;
        } else {
            normalTex = new ResourceLocation(textures.getAsJsonObject().get("normal").getAsString() + ".png");
            hoverTex = new ResourceLocation(textures.getAsJsonObject().get("hover").getAsString() + ".png");
        }

        AnimatedElement element = switch (type) {
            case "layer" -> {
                Layer layer = new Layer(VIRTUAL_SCREEN, normalTex, vX, vY, vWidth, vHeight);
                if (variant.has("alpha")) {
                    layer.setAlpha(variant.get("alpha").getAsFloat());
                }
                if (variant.has("scale")) {
                    layer.setScale(variant.get("scale").getAsFloat());
                }
                yield layer;
            }
            case "button" -> {
                TitleScreenButton button = new TitleScreenButton(VIRTUAL_SCREEN, normalTex, hoverTex, vX, vY, vWidth, vHeight);
                if (variant.has("alpha")) {
                    button.setAlpha(variant.get("alpha").getAsFloat());
                }
                if (variant.has("tooltip")) {
                    final String tooltipKey = variant.get("tooltip").getAsString();
                    button.setTooltipSupplier(() -> I18n.format(tooltipKey));
                }
                if (variant.has("sound")) {
                    SoundEvent se = SoundEvent.REGISTRY.getObject(new ResourceLocation(variant.get("sound").getAsString()));
                    if (se != null) button.setSound(se);
                    else throw new JsonParseException("Sound is invalid.");
                }
                if (variant.has("voice")) {
                    try {
                        button.setVoiceType(VoiceType.valueOf(variant.get("voice").getAsString().toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        throw new JsonParseException("VoiceType is invalid.");
                    }
                }
                if (variant.has("action")) {
                    button.setOnClick(this.getOnClick(variant.get("action").getAsString()));
                }
                yield button;
            }
            default -> throw new JsonParseException("Type is invalid.");
        };
        if (variant.has("delay_offset")) {
            element.setDelay(delay + variant.get("delay_offset").getAsLong());
        }
        if (variant.has("duration")) {
            element.setDuration(variant.get("duration").getAsLong());
        }
        if (variant.has("on_complete")) {
            // TODO: 添加更多行为
            String[] oc = variant.get("on_complete").getAsString().split(":");
            if ("play_voice".equals(oc[0])) {
                VoiceType vt = VoiceType.valueOf(oc[1].toUpperCase());
                element.setOnComplete(() -> SoundManager.playVoice(vt));
            }
        }
        if (variant.has("animations")) {
            for (JsonElement animation : variant.getAsJsonArray("animations")) {
                JsonObject object = animation.getAsJsonObject();
                setAnim(
                        element,
                        JsonParseUtils.tryGet(object, "property").getAsString(),
                        JsonParseUtils.tryGet(object, "to").getAsFloat(),
                        getEasing(JsonParseUtils.tryGet(object, "easing").getAsString())
                );
            }
        }
        this.addChild(element);
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

    /*
    Json 处理部分
     */

    public static void updateJson(JsonObject root) throws JsonParseException {
        uiJsonCache = root;

        String prefix = "/";
        JsonObject config = JsonParseUtils.tryGetAsJsonObject(root, "config", prefix);
        prefix += "config/";

        JsonArray size = JsonParseUtils.tryGetAsJsonArray(config, "size", prefix);
        VIRTUAL_SCREEN.setVirtualWidth(size.get(0).getAsInt());
        VIRTUAL_SCREEN.setVirtualHeight(size.get(1).getAsInt());

        if (config.has("background_color")) {
            backgroundColor = Long.decode(config.get("background_color").getAsString()).intValue();
        } else {
            backgroundColor = 0xFF000000;
        }
        fallbackLang = config.get("fallback_variant").getAsString();

        JsonObject delay = JsonParseUtils.tryGetAsJsonObject(config, "delay", prefix);
        baseDelay = delay.get("base").getAsLong();
        showedDelay = delay.get("showed").getAsLong();
    }

    @Nullable
    private Consumer<TitleScreenButton> getOnClick(String raw) throws JsonParseException {
        if (raw.isEmpty()) return null;
        return switch (raw) {
            case "new_game" -> b -> {
                GuiScreen screen = DWGIntegration.tryGetDWGGui(this);
                if (screen == null) screen = new GuiCreateWorld(this);
                this.mc.displayGuiScreen(screen);
            };
            case "select_world" -> b -> this.mc.displayGuiScreen(new GuiWorldSelection(this));
            case "continue" -> b -> this.mc.displayGuiScreen(new GuiMultiplayer(this));
            case "realms" -> b -> {
                if (YuZuUIConfig.replaceRealms) {
                    this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
                } else {
                    new RealmsBridge().switchToRealms(this);
                }
            };
            case "mod_list" -> b -> this.mc.displayGuiScreen(new GuiModList(this));
            case "options" -> b -> this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
            case "quit_game" -> b -> {
                YuZuUI.exit = true;
                if (YuZuUIConfig.justExit) {
                    if (this.passExitSound == -1) {
                        this.passExitSound = 0;
                    }
                    CompletableFuture.runAsync(() -> {
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
                    }, EXECUTOR);
                } else {
                    this.mc.displayGuiScreen(null);
                }
            };
            default -> throw new JsonParseException("Invalid on click type '" + raw + "'!");
        };
    }

    @Nonnull
    private static IEasing getEasing(String raw) throws JsonParseException {
        String[] parts = raw.split(":");
        return switch (parts[0]) {
            case "exponential_out" -> {
                if (parts.length != 2) {
                    throw new JsonParseException("Invalid easing '" + raw + "'!");
                }
                yield IEasing.exponentialOut(Double.parseDouble(parts[1]));
            }
            case "linear" -> IEasing.LINEAR;
            default -> throw new JsonParseException("Invalid easing '" + raw + "'!");
        };
    }

    private static void setAnim(Element element, String property, float to, IEasing easing) throws JsonParseException {
        if (element instanceof Layer layer) {
            switch (property) {
                case "x" -> layer.animateX(to, easing);
                case "y" -> layer.animateY(to, easing);
                case "alpha" -> layer.animateAlpha(to, easing);
                case "scale" -> layer.animateScale(to, easing);
                default -> throw new JsonParseException("Invalid property '" + property + "'!");
            }
            return;
        }
        if (element instanceof TitleScreenButton button) {
            switch (property) {
                case "x" -> button.animateX(to, easing);
                case "y" -> button.animateY(to, easing);
                case "alpha" -> button.animateAlpha(to, easing);
                default -> throw new JsonParseException("Invalid property '" + property + "'!");
            }
            //return;
        }
    }
}
