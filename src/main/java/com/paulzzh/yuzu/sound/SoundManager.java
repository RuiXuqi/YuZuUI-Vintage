package com.paulzzh.yuzu.sound;

import com.paulzzh.yuzu.YuZuUIConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 辅助一些声音相关的调用。
 */
public class SoundManager {

    private static final Map<Character, Map<VoiceType, SoundEvent>> VOICE_MAP = new EnumMap<>(Character.class);
    private static final List<Character> CHARACTER_ENABLED = new ArrayList<>();
    private static PositionedSoundRecord playedVoiceRecord;

    public static void init() {
        for (Character character : Character.values()) {
            VOICE_MAP.put(character, new EnumMap<>(VoiceType.class));
            for (VoiceType type : VoiceType.values()) {
                SoundEvent sound = SoundRegister.registerSound(type.toString() + "_" + character.toString());
                VOICE_MAP.get(character).put(type, sound);
            }
        }
        updateCharacterStatus();
    }

    /**
     * 随机从所有启用人物的语音中获取特定种类的一种语音。
     *
     * @param type 所需的语音种类。
     */
    public static SoundEvent getVoice(VoiceType type) {
        Character character = CHARACTER_ENABLED.get(
            ThreadLocalRandom.current().nextInt(CHARACTER_ENABLED.size())
        );

        return getVoice(character, type);
    }

//    /**
//     * 随机获取一位特定人物的语音。
//     *
//     * @param character 语音声源。
//     */
//    public static SoundEvent getVoice(Character character) {
//        Map<VoiceType, SoundEvent> characterVoices = VOICE_MAP.get(character);
//
//        List<VoiceType> availableTypes = new ArrayList<>(characterVoices.keySet());
//        VoiceType randomType = availableTypes.get(
//            ThreadLocalRandom.current().nextInt(availableTypes.size())
//        );
//
//        return characterVoices.get(randomType);
//    }

    private static SoundEvent getVoice(Character character, VoiceType type) {
        return VOICE_MAP.get(character).get(type);
    }

    public static void updateCharacterStatus() {
        CHARACTER_ENABLED.clear();
        for (Character character : Character.values()) {
            if (character.getConfigSupplier().getAsBoolean()) {
                CHARACTER_ENABLED.add(character);
            }
        }
    }

    /**
     * 获取语音是否可用。
     */
    @SuppressWarnings("deprecation")
    public static boolean getIsVoiceAvailable(Minecraft mc) {
        return YuZuUIConfig.voice && !CHARACTER_ENABLED.isEmpty()
            && mc.gameSettings.getSoundLevel(SoundCategory.VOICE) != 0;
    }

    /**
     * 播放一些音效用的方法。音量：0.25
     */
    public static void playSound(@Nonnull Minecraft mc, @Nonnull SoundEvent sound) {
        PositionedSoundRecord record = PositionedSoundRecord.getMasterRecord(sound, 1.0F);
        mc.getSoundHandler().playSound(record);
    }

    /**
     * 语音专用的播放方法，会停止当前正在播放的语音并刷新 playedVoiceRecord。音量：1.0
     */
    public static void playVoice(@Nonnull Minecraft mc, VoiceType VoiceType) {
        if (getIsVoiceAvailable(mc)) {
            // 不用检查语音有没有在播放或者 null，方法本身自带检查
            if (YuZuUIConfig.preventMixingVoice) {
                mc.getSoundHandler().stopSound(playedVoiceRecord);
            }
            SoundEvent soundEvent = getVoice(VoiceType);
            // 原版竟然没用 VOICE 类别？？？没搜到用法
            playedVoiceRecord = getSoundRecord(soundEvent, SoundCategory.VOICE, 1.0F, 1.0F);
            mc.getSoundHandler().playSound(playedVoiceRecord);
        }
    }

    public static @Nonnull PositionedSoundRecord getSoundRecord(@Nonnull SoundEvent event, @Nonnull SoundCategory category, float volume, float pitchIn) {
        return new PositionedSoundRecord(event.getSoundName(), category, volume, pitchIn, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
    }
}
