package net.kapitencraft.mysticcraft.worldgen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModEntityTypes;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public interface ModBiomeModifiers {
    ResourceKey<BiomeModifier> ADD_CRIMSONITE_ORE = registerKey("add_crimsonite_ore");
    ResourceKey<BiomeModifier> ADD_GEMSTONE_SEEDS = registerKey("add_gemstone_seeds");
    ResourceKey<BiomeModifier> SPAWN_FROZEN_BLAZE = registerKey("spawn_frozen_blaze");
    ResourceKey<BiomeModifier> SPAWN_VAMPIRE_BAT = registerKey("spawn_vampire_bat");

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, MysticcraftMod.res(name));
    }

    static void bootstrap(BootstapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        context.register(ADD_CRIMSONITE_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.CRIMSON_FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.CRIMSONIUM_PLACED)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_GEMSTONE_SEEDS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.GEMSTONE_SPAWN),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.GEMSTONE_SPAWN_PLACED)),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(SPAWN_FROZEN_BLAZE, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
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
        context.register(SPAWN_VAMPIRE_BAT, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
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
