package com.paulzzh.yuzu.sound;

import com.paulzzh.yuzu.YuZuUI;
import com.paulzzh.yuzu.gui.screen.SenrenBankaTitleScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;

public class TitleScreenMusicTicker {
    private static PositionedSoundRecord ISOUND_TITLE;
    private static Long soundStartTime = null;

    public static void tickBGM() {
        SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();
        if (!YuZuUI.exit && (ISOUND_TITLE == null || !soundHandler.isSoundPlaying(ISOUND_TITLE))) {
            long currentTime = Minecraft.getSystemTime();
            if (soundStartTime == null) soundStartTime = currentTime;
            if (currentTime - soundStartTime > SenrenBankaTitleScreen.getDelay()) {
                ISOUND_TITLE = SoundManager.getSoundRecord(SoundRegister.YUZU_TITLE_MUSIC, 0.25F, 1.0F);
                soundHandler.playSound(ISOUND_TITLE);
                soundStartTime = null;
            }
        }
    }

    public static void stopBGM() {
        Minecraft.getMinecraft().getSoundHandler().stopSound(ISOUND_TITLE);
    }
}
