package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.worldgen.feature.GemstoneSpawnFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModFeatures {
    DeferredRegister<Feature<?>> REGISTRY = MysticcraftMod.registry(ForgeRegistries.FEATURES);

    RegistryObject<GemstoneSpawnFeature> GEMSTONE_SPAWN = REGISTRY.register("gemstone_crystal_spawn", GemstoneSpawnFeature::new);
}
