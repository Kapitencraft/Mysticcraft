package net.kapitencraft.mysticcraft.block.grower;

import net.kapitencraft.mysticcraft.worldgen.ModConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class PeridotSycamoreGrower extends AbstractGiganticTreeGrower {
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredSizedFeature(RandomSource pRandom) {
        return ModConfiguredFeatures.PERIDOT_SYCAMORE_TREE;
    }

    @Override
    protected @Nullable ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return null;
    }
}
