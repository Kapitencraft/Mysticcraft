package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.worldgen.feature.GemstoneSpawnFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModFeatures {
    DeferredRegister<Feature<?>> REGISTRY = MysticcraftMod.registry(Registries.FEATURE);

    Supplier<GemstoneSpawnFeature> GEMSTONE_SPAWN = REGISTRY.register("gemstone_crystal_spawn", GemstoneSpawnFeature::new);
}
