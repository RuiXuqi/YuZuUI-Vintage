package com.paulzzh.yuzu.sound;

import com.paulzzh.yuzu.YuZuUI;
import com.paulzzh.yuzu.YuZuUIConfig;
import com.paulzzh.yuzu.gui.screen.SenrenBankaTitleScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;

import javax.annotation.Nullable;

public class TitleScreenMusicTicker {
    private static @Nullable PositionedSoundRecord ISOUND_TITLE;
    private static @Nullable Long soundStartTime = null;

    /**
     * @return 是否应由它接管音乐，也就是是否需要取消其他 MusicTicker 的更新。
     */
    public static boolean tickBGM() {
        SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();
        boolean inMenu = !YuZuUI.exit && !YuZuUI.inGamed;

        if (inMenu && YuZuUIConfig.bgm) {
            if (ISOUND_TITLE == null || !soundHandler.isSoundPlaying(ISOUND_TITLE)) {
                long currentTime = Minecraft.getSystemTime();
                if (soundStartTime == null) soundStartTime = currentTime;
                if (currentTime - soundStartTime > SenrenBankaTitleScreen.getDelay()) {
                    ISOUND_TITLE = SoundManager.getSoundRecord(SoundRegister.YUZU_TITLE_MUSIC, 0.25F, 1.0F);
                    soundHandler.playSound(ISOUND_TITLE);
                    soundStartTime = null;
                }
            }
        } else if (ISOUND_TITLE != null) {
            soundHandler.stopSound(ISOUND_TITLE);
            ISOUND_TITLE = null;
            soundStartTime = null;
        }

        return inMenu;
    }
}
