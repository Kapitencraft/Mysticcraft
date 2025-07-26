package net.kapitencraft.mysticcraft.worldgen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.ModFeatures;
import net.kapitencraft.mysticcraft.worldgen.feature.GemstoneSpawnFeature;
import net.kapitencraft.mysticcraft.worldgen.tree.ColossalFoliagePlacer;
import net.kapitencraft.mysticcraft.worldgen.tree.ColossalTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;

public interface ModConfiguredFeatures {
    ResourceKey<ConfiguredFeature<?, ?>> CRIMSONIUM_ORE = registerKey("crimsonium_ore");
    ResourceKey<ConfiguredFeature<?, ?>> GEMSTONE_SPAWN = registerKey("gemstone_spawn");
    ResourceKey<ConfiguredFeature<?, ?>> PERIDOT_SYCAMORE_TREE = registerKey("peridot_sycamore_tree");


    static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest netherrackReplaceable = new BlockMatchTest(Blocks.NETHERRACK);

        List<OreConfiguration.TargetBlockState> crimsoniumOres = List.of(OreConfiguration.target(netherrackReplaceable, ModBlocks.CRIMSONIUM_ORE.get().defaultBlockState()));

        register(context, CRIMSONIUM_ORE, Feature.ORE, new OreConfiguration(crimsoniumOres, 4));
        register(context, GEMSTONE_SPAWN, ModFeatures.GEMSTONE_SPAWN.get(), new GemstoneSpawnFeature.Config());
        register(context, PERIDOT_SYCAMORE_TREE, Feature.TREE, peridotSycamoreTree().build());
    }

    private static TreeConfiguration.TreeConfigurationBuilder peridotSycamoreTree() {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.PERIDOT_SYCAMORE_LOG.get()),
                new ColossalTrunkPlacer(25, 5, 13),
                BlockStateProvider.simple(Blocks.ACACIA_LEAVES),
                new ColossalFoliagePlacer(),
                new TwoLayersFeatureSize(1, 0, 2)
        ).ignoreVines();
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, MysticcraftMod.res(name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
        context.register(key, new ConfiguredFeature<>(feature, config));
    }
}
