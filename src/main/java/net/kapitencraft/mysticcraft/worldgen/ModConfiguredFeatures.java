package net.kapitencraft.mysticcraft.worldgen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModFeatures;
import net.kapitencraft.mysticcraft.worldgen.feature.GemstoneSpawnFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSONIUM_ORE = registerKey("crimsonium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GEMSTONE_SPAWN = registerKey("gemstone_spawn");


    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest netherrackReplaceAbles = new BlockMatchTest(Blocks.NETHERRACK);

        List<OreConfiguration.TargetBlockState> crimsoniumOres = List.of(OreConfiguration.target(netherrackReplaceAbles, ModBlocks.CRIMSONIUM_ORE.getBlock().defaultBlockState()));

        register(context, CRIMSONIUM_ORE, Feature.ORE, new OreConfiguration(crimsoniumOres, 4));
        register(context, GEMSTONE_SPAWN, ModFeatures.GEMSTONE_SPAWN.get(), new GemstoneSpawnFeature.Config());
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, MysticcraftMod.res(name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC config) {
        context.register(key, new ConfiguredFeature<>(feature, config));
    }
}
