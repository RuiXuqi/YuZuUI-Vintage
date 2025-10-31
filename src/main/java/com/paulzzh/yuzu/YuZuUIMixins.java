package com.paulzzh.yuzu;

import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class YuZuUIMixins implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        return Loader.isModLoaded("galacticraft") ? Collections.singletonList("mixins.yuzu.late.json") : Collections.emptyList();
    }
}
