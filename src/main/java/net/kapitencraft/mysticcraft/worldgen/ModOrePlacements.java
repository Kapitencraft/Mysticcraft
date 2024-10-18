package net.kapitencraft.mysticcraft.worldgen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModOrePlacements {
    public static final ResourceKey<PlacedFeature> CRIMSONIUM_PLACED = createKey("crimsonium_placed");
    public static final ResourceKey<PlacedFeature> GEMSTONE_SPAWN_PLACED = createKey("gemstone_spawn_placed");

    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    private static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, CRIMSONIUM_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.CRIMSONIUM_ORE), ModOrePlacements.commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(4), VerticalAnchor.aboveBottom(10))));
        register(context, GEMSTONE_SPAWN_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.GEMSTONE_SPAWN), List.of(HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(20), VerticalAnchor.aboveBottom(80))));
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, MysticcraftMod.res(name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
