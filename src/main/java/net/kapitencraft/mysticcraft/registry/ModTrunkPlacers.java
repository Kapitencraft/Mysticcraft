package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.worldgen.tree.ColossalTrunkPlacer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface ModTrunkPlacers  {
    DeferredRegister<TrunkPlacerType<?>> REGISTRY = MysticcraftMod.registry(Registries.TRUNK_PLACER_TYPE);

    Holder<TrunkPlacerType<?>> COLOSSAL = REGISTRY.register("giant", () -> new TrunkPlacerType<>(ColossalTrunkPlacer.CODEC));
}
