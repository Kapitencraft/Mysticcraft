package net.kapitencraft.mysticcraft.worldgen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModEntityTypes;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public interface ModBiomeModifiers {
    ResourceKey<BiomeModifier> ADD_CRIMSONITE_ORE = registerKey("add_crimsonite_ore");
    ResourceKey<BiomeModifier> ADD_GEMSTONE_SEEDS = registerKey("add_gemstone_seeds");
    ResourceKey<BiomeModifier> SPAWN_FROZEN_BLAZE = registerKey("spawn_frozen_blaze");
    ResourceKey<BiomeModifier> SPAWN_VAMPIRE_BAT = registerKey("spawn_vampire_bat");

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, MysticcraftMod.res(name));
    }

    static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        context.register(ADD_CRIMSONITE_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.CRIMSON_FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.CRIMSONIUM_PLACED)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_GEMSTONE_SEEDS, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.GEMSTONE_SPAWN),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.GEMSTONE_SPAWN_PLACED)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(SPAWN_FROZEN_BLAZE, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.SOUL_SAND_VALLEY)),
                List.of(
                        new MobSpawnSettings.SpawnerData(
                                ModEntityTypes.FROZEN_BLAZE.get(),
                                80,
                                2,
                                5
                        )
                )
        ));
        context.register(SPAWN_VAMPIRE_BAT, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                List.of(
                        new MobSpawnSettings.SpawnerData(
                                ModEntityTypes.VAMPIRE_BAT.get(),
                                1,
                                1,
                                2
                        )
                )
        ));
    }
}
