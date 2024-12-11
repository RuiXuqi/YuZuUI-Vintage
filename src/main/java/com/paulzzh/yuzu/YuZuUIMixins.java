package com.paulzzh.yuzu;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;
import com.paulzzh.yuzu.mixins.Mixins;

import java.util.List;
import java.util.Set;

@LateMixin
public class YuZuUIMixins implements ILateMixinLoader {
    @Override
    public String getMixinConfig() {
        return "mixins.yuzu.late.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        return Mixins.getLateMixins(loadedMods);
    }

}
