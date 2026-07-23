package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.worldgen.structure.StoneCircleStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModStructureTypes {
    DeferredRegister<StructureType<?>> REGISTRY = MysticcraftMod.registry(Registries.STRUCTURE_TYPE);

    Supplier<StructureType<StoneCircleStructure>> STONE_CIRCLE = REGISTRY.register("stone_circle", () -> () -> StoneCircleStructure.CODEC);
}
