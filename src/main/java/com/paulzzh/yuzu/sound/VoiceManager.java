package com.paulzzh.yuzu.sound;

import com.paulzzh.yuzu.YuZuUIConfig;
import net.minecraft.util.SoundEvent;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class VoiceManager {

    public enum Character {
        LENA("lena"),
        MAKO("mako"),
        MURASAME("murasame"),
        YOSHINO("yoshino"),
        KOHARU("koharu"),
        ROKA("roka"),
        RENTAROU("rentarou"),
        MIZUHA("mizuha"),
        YASUHARU("yasuharu"),
        GENJUROU("genjurou");

        private final String key;

        Character(String key) {
            this.key = key;
        }

        public String toString() {
            return this.key;
        }
    }

    public enum VoiceType {
        SENREN("senren"),
        MOD_LIST("button_mod_list"),
        OPTIONS("button_options"),
        QUIT_GAME("button_quit_game"),
        REALMS("button_realms"),
        SELECT_WORLD("button_select_world");

        private final String key;

        VoiceType(String key) {
            this.key = key;
        }

        public String toString() {
            return this.key;
        }
    }

    private static final Map<Character, Map<VoiceType, SoundEvent>> VOICE_MAP = new EnumMap<>(Character.class);
    private static final List<Character> CHARACTER_ENABLED = new ArrayList<>();

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

}
