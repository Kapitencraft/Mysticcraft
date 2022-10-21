package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.GemstoneGrinderBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MysticcraftMod.MOD_ID);
    public static final RegistryObject<BlockEntityType<GemstoneGrinderBlockEntity>> GEMSTONE_GRINDER = REGISTRY.register("gemstone_grinder", ()-> BlockEntityType.Builder.of(GemstoneGrinderBlockEntity::new, ModBlocks.GEMSTONE_GRINDER.get()).build(null));
}
