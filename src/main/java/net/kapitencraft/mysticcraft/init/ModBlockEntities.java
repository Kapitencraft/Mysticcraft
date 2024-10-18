package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public interface ModBlockEntities {
    DeferredRegister<BlockEntityType<?>> REGISTRY = MysticcraftMod.makeRegistry(ForgeRegistries.BLOCK_ENTITY_TYPES);
}
