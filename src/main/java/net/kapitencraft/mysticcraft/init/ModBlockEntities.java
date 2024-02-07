package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.GemstoneGrinderBlockEntity;
import net.kapitencraft.mysticcraft.block.entity.ReforgeAnvilBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModBlockEntities {
    DeferredRegister<BlockEntityType<?>> REGISTRY = MysticcraftMod.makeRegistry(ForgeRegistries.BLOCK_ENTITY_TYPES);
    RegistryObject<BlockEntityType<GemstoneGrinderBlockEntity>> GEMSTONE_GRINDER = REGISTRY.register("gemstone_grinder", () -> BlockEntityType.Builder.of(GemstoneGrinderBlockEntity::new, ModBlocks.GEMSTONE_GRINDER.getBlock()).build(null));
    RegistryObject<BlockEntityType<ReforgeAnvilBlockEntity>> REFORGING_ANVIL = REGISTRY.register("reforging_anvil", () -> BlockEntityType.Builder.of(ReforgeAnvilBlockEntity::new, ModBlocks.REFORGING_ANVIL.getBlock()).build(null));
}
