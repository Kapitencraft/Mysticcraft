package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.worldgen.tree.ColossalFoliagePlacer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface ModFoliagePlacers {
    DeferredRegister<FoliagePlacerType<?>> REGISTRY = MysticcraftMod.registry(Registries.FOLIAGE_PLACER_TYPE);

    Holder<FoliagePlacerType<?>> COLOSSAL = REGISTRY.register("colossal", () -> new FoliagePlacerType<>(ColossalFoliagePlacer.CODEC));
}
