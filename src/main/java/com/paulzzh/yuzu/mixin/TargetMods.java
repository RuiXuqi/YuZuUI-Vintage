package com.paulzzh.yuzu.mixin;

import com.gtnewhorizon.gtnhmixins.builders.ITargetMod;
import com.gtnewhorizon.gtnhmixins.builders.TargetModBuilder;

import javax.annotation.Nonnull;

public enum TargetMods implements ITargetMod {

    GALACTICRAFT("GalacticraftCore");

    private final TargetModBuilder builder;

//    TargetMods(String coreModClass, String modId) {
//        this.builder = new TargetModBuilder().setCoreModClass(coreModClass).setModId(modId);
//    }

    TargetMods(String modId) {
        this.builder = new TargetModBuilder().setModId(modId);
    }

    @Nonnull
    @Override
    public TargetModBuilder getBuilder() {
        return builder;
    }
}
