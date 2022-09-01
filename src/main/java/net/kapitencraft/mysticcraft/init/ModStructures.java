package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;

public class ModStructures {
    public static final DeferredRegister<StructureType<?>> REGISTRY = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, MysticcraftMod.MOD_ID);
}
