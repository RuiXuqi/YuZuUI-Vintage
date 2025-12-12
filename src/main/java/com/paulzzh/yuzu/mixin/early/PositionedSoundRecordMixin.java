package com.paulzzh.yuzu.mixin.early;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@SuppressWarnings("UnusedMixin")
@Mixin(PositionedSoundRecord.class)
public interface PositionedSoundRecordMixin {
    @Invoker("<init>")
    static PositionedSoundRecord create(
        ResourceLocation soundResource,
        float volume,
        float pitch,
        boolean repeat,
        int repeatDelay,
        ISound.AttenuationType attenuationType,
        float xPosition,
        float yPosition,
        float zPosition
    ) {
        throw new AssertionError();
    }
}
