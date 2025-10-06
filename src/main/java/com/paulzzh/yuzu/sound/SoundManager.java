package com.paulzzh.yuzu.sound;

import com.paulzzh.yuzu.YuZuUIConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class SoundManager {

    private static final Map<Character, Map<VoiceType, SoundEvent>> VOICE_MAP = new EnumMap<>(Character.class);
    private static final List<Character> CHARACTER_ENABLED = new ArrayList<>();
    private static PositionedSoundRecord playedVoiceRecord;

    public static void init() {
        for (Character character : Character.values()) {
            VOICE_MAP.put(character, new EnumMap<>(VoiceType.class));
            for (VoiceType type : VoiceType.values()) {
                SoundEvent sound = InitSounds.registerSound(type.toString() + "_" + character.toString());
                VOICE_MAP.get(character).put(type, sound);
            }
        }
        updateCharacterStatus();
    }

    /**
     * Get a specified type of voice randomly from all the enabled characters' voices.
     *
     * @param type The type of voice needed.
     */
    public static SoundEvent getVoice(VoiceType type) {
        if (CHARACTER_ENABLED.isEmpty()) return null;

        Character character = CHARACTER_ENABLED.get(
            ThreadLocalRandom.current().nextInt(CHARACTER_ENABLED.size())
        );

        return getVoice(character, type);
    }

    /**
     * Get a voice from a specified character randomly.
     *
     * @param character The author of voice.
     */
    public static SoundEvent getVoice(Character character) {
        Map<VoiceType, SoundEvent> characterVoices = VOICE_MAP.get(character);

        List<VoiceType> availableTypes = new ArrayList<>(characterVoices.keySet());
        VoiceType randomType = availableTypes.get(
            ThreadLocalRandom.current().nextInt(availableTypes.size())
        );

        return characterVoices.get(randomType);
    }

    private static SoundEvent getVoice(Character character, VoiceType type) {
        return VOICE_MAP.get(character).get(type);
    }

    public static void updateCharacterStatus() {
        CHARACTER_ENABLED.clear();
        if (YuZuUIConfig.VoiceList.lena) {
            CHARACTER_ENABLED.add(Character.LENA);
        }
        if (YuZuUIConfig.VoiceList.mako) {
            CHARACTER_ENABLED.add(Character.MAKO);
        }
        if (YuZuUIConfig.VoiceList.murasame) {
            CHARACTER_ENABLED.add(Character.MURASAME);
        }
        if (YuZuUIConfig.VoiceList.yoshino) {
            CHARACTER_ENABLED.add(Character.YOSHINO);
        }
        if (YuZuUIConfig.VoiceList.koharu) {
            CHARACTER_ENABLED.add(Character.KOHARU);
        }
        if (YuZuUIConfig.VoiceList.roka) {
            CHARACTER_ENABLED.add(Character.ROKA);
        }
        if (YuZuUIConfig.VoiceList.rentarou) {
            CHARACTER_ENABLED.add(Character.RENTAROU);
        }
        if (YuZuUIConfig.VoiceList.mizuha) {
            CHARACTER_ENABLED.add(Character.MIZUHA);
        }
        if (YuZuUIConfig.VoiceList.yasuharu) {
            CHARACTER_ENABLED.add(Character.YASUHARU);
        }
        if (YuZuUIConfig.VoiceList.genjurou) {
            CHARACTER_ENABLED.add(Character.GENJUROU);
        }
    }

    public static boolean getIsVoiceAvailable(SoundEvent voice) {
        return YuZuUIConfig.voice && voice != null;
    }

    public static PositionedSoundRecord playSound(Minecraft mc, SoundEvent sound) {
        PositionedSoundRecord record = PositionedSoundRecord.getMasterRecord(sound, 1.0F);
        mc.getSoundHandler().playSound(record);
        return record;
    }

    public static void playVoice(Minecraft mc, @Nullable SoundEvent voice) {
        if (getIsVoiceAvailable(voice)) {
            // 不用检查语音有没有在播放或者 null，方法本身自带检查
            if (YuZuUIConfig.preventMixingVoice) {
                mc.getSoundHandler().stopSound(playedVoiceRecord);
            }
            playedVoiceRecord = playSound(mc, voice);
        }
    }

    public static SoundEvent playVoice(Minecraft mc, VoiceType voiceType) {
        SoundEvent voice = getVoice(voiceType);
        playVoice(mc, voice);
        return voice;
    }
}
