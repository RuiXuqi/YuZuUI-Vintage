package com.paulzzh.yuzu.mixin;

import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public enum Mixins implements IMixins {
    VANILLA(new MixinBuilder()
        .setPhase(Phase.EARLY)
        .addClientMixins("MinecraftMixin")
        .addClientMixins("MusicTickerMixin")
        .addClientMixins("PositionedSoundRecordMixin")
    ),
    GALACTICRAFT(new MixinBuilder()
        .setPhase(Phase.LATE)
        .addRequiredMod(TargetMods.GALACTICRAFT)
        .addClientMixins("MusicTickerGCMixin")
    );

    private final MixinBuilder builder;

    Mixins(MixinBuilder builder) {
        this.builder = builder;
    }

    @Nonnull
    @Override
    public MixinBuilder getBuilder() {
        return builder;
    }
}
