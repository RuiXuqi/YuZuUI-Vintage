package com.paulzzh.yuzu;

import com.paulzzh.yuzu.sound.SoundManager;
import com.paulzzh.yuzu.sound.TitleScreenMusicTicker;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

public class YuZuUIConfigHandler {
    @SubscribeEvent
    public void onConfigChanged(@Nonnull ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(YuZuUI.MOD_ID)) {
            YuZuUIConfig.syncConfig();
            SoundManager.updateCharacterStatus();
            if (!YuZuUIConfig.bgm) TitleScreenMusicTicker.stopBGM();
        }
    }
}
