
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.kapitencraft.mysticcraft.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;

import net.kapitencraft.mysticcraft.block.entity.ReforgeAnvilBlockBlockEntity;
import net.kapitencraft.mysticcraft.MysticcraftMod;

public class MysticcraftModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MysticcraftMod.MODID);
	public static final RegistryObject<BlockEntityType<?>> REFORGE_ANVIL_BLOCK = register("reforge_anvil_block",
			MysticcraftModBlocks.REFORGE_ANVIL_BLOCK, ReforgeAnvilBlockBlockEntity::new);

	private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block,
			BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}
}
