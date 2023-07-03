package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.GemstoneGrinderBlockEntity;
import net.kapitencraft.mysticcraft.block.entity.ManaSAMLauncherBlockEntity;
import net.kapitencraft.mysticcraft.block.entity.ReforgingAnvilBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MysticcraftMod.MOD_ID);
    public static final RegistryObject<BlockEntityType<GemstoneGrinderBlockEntity>> GEMSTONE_GRINDER = REGISTRY.register("gemstone_grinder", () -> BlockEntityType.Builder.of(GemstoneGrinderBlockEntity::new, ModBlocks.GEMSTONE_GRINDER.getBlock()).build(null));
    public static final RegistryObject<BlockEntityType<ManaSAMLauncherBlockEntity>> MANA_SAM_LAUNCHER = REGISTRY.register("mana_sam_launcher", () -> BlockEntityType.Builder.of(ManaSAMLauncherBlockEntity::new, ModBlocks.MANA_SAM_LAUNCHER.getBlock()).build(null));
    public static final RegistryObject<BlockEntityType<ReforgingAnvilBlockEntity>> REFORGING_ANVIL = REGISTRY.register("reforging_anvil", () -> BlockEntityType.Builder.of(ReforgingAnvilBlockEntity::new, ModBlocks.REFORGING_ANVIL.getBlock()).build(null));
}
