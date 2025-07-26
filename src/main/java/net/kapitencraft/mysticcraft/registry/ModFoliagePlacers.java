package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.worldgen.tree.ColossalFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModFoliagePlacers {
    DeferredRegister<FoliagePlacerType<?>> REGISTRY = MysticcraftMod.registry(ForgeRegistries.FOLIAGE_PLACER_TYPES);

    RegistryObject<FoliagePlacerType<?>> COLOSSAL = REGISTRY.register("colossal", () -> new FoliagePlacerType<>(ColossalFoliagePlacer.CODEC));
}
