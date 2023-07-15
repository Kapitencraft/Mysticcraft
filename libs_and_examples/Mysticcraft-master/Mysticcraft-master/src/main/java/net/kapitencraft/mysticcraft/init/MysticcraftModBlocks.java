
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.kapitencraft.mysticcraft.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.block.Block;

import net.kapitencraft.mysticcraft.block.WitherKingSpawnBlockBlock;
import net.kapitencraft.mysticcraft.block.VoidgloomchestplacerBlock;
import net.kapitencraft.mysticcraft.block.VoidGloomBlockBlock;
import net.kapitencraft.mysticcraft.block.TitaniumOreBlock;
import net.kapitencraft.mysticcraft.block.TitaniumBlockBlock;
import net.kapitencraft.mysticcraft.block.TeriumWoodBlock;
import net.kapitencraft.mysticcraft.block.TeriumStairsBlock;
import net.kapitencraft.mysticcraft.block.TeriumSlabBlock;
import net.kapitencraft.mysticcraft.block.TeriumPressurePlateBlock;
import net.kapitencraft.mysticcraft.block.TeriumPlanksBlock;
import net.kapitencraft.mysticcraft.block.TeriumLogBlock;
import net.kapitencraft.mysticcraft.block.TeriumLeavesBlock;
import net.kapitencraft.mysticcraft.block.TeriumFenceGateBlock;
import net.kapitencraft.mysticcraft.block.TeriumFenceBlock;
import net.kapitencraft.mysticcraft.block.TeriumButtonBlock;
import net.kapitencraft.mysticcraft.block.RubyOreBlock;
import net.kapitencraft.mysticcraft.block.RubyBlockBlock;
import net.kapitencraft.mysticcraft.block.ReforgeAnvilBlockBlock;
import net.kapitencraft.mysticcraft.block.MithrilOreBlock;
import net.kapitencraft.mysticcraft.block.FilloriumWoodBlock;
import net.kapitencraft.mysticcraft.block.FilloriumStairsBlock;
import net.kapitencraft.mysticcraft.block.FilloriumSlabBlock;
import net.kapitencraft.mysticcraft.block.FilloriumPressurePlateBlock;
import net.kapitencraft.mysticcraft.block.FilloriumPlanksBlock;
import net.kapitencraft.mysticcraft.block.FilloriumLogBlock;
import net.kapitencraft.mysticcraft.block.FilloriumLeavesBlock;
import net.kapitencraft.mysticcraft.block.FilloriumFenceGateBlock;
import net.kapitencraft.mysticcraft.block.FilloriumFenceBlock;
import net.kapitencraft.mysticcraft.block.FilloriumButtonBlock;
import net.kapitencraft.mysticcraft.block.CrystalblockBlock;
import net.kapitencraft.mysticcraft.block.CopperOreBlock;
import net.kapitencraft.mysticcraft.block.CopperBlockBlock;
import net.kapitencraft.mysticcraft.MysticcraftMod;

public class MysticcraftModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, MysticcraftMod.MODID);
	public static final RegistryObject<Block> MITHRIL_ORE = REGISTRY.register("mithril_ore", () -> new MithrilOreBlock());
	public static final RegistryObject<Block> TERIUM_WOOD = REGISTRY.register("terium_wood", () -> new TeriumWoodBlock());
	public static final RegistryObject<Block> TERIUM_LOG = REGISTRY.register("terium_log", () -> new TeriumLogBlock());
	public static final RegistryObject<Block> TERIUM_PLANKS = REGISTRY.register("terium_planks", () -> new TeriumPlanksBlock());
	public static final RegistryObject<Block> TERIUM_LEAVES = REGISTRY.register("terium_leaves", () -> new TeriumLeavesBlock());
	public static final RegistryObject<Block> TERIUM_STAIRS = REGISTRY.register("terium_stairs", () -> new TeriumStairsBlock());
	public static final RegistryObject<Block> TERIUM_SLAB = REGISTRY.register("terium_slab", () -> new TeriumSlabBlock());
	public static final RegistryObject<Block> TERIUM_FENCE = REGISTRY.register("terium_fence", () -> new TeriumFenceBlock());
	public static final RegistryObject<Block> TERIUM_FENCE_GATE = REGISTRY.register("terium_fence_gate", () -> new TeriumFenceGateBlock());
	public static final RegistryObject<Block> TERIUM_PRESSURE_PLATE = REGISTRY.register("terium_pressure_plate",
			() -> new TeriumPressurePlateBlock());
	public static final RegistryObject<Block> TERIUM_BUTTON = REGISTRY.register("terium_button", () -> new TeriumButtonBlock());
	public static final RegistryObject<Block> TITANIUM_ORE = REGISTRY.register("titanium_ore", () -> new TitaniumOreBlock());
	public static final RegistryObject<Block> TITANIUM_BLOCK = REGISTRY.register("titanium_block", () -> new TitaniumBlockBlock());
	public static final RegistryObject<Block> FILLORIUM_WOOD = REGISTRY.register("fillorium_wood", () -> new FilloriumWoodBlock());
	public static final RegistryObject<Block> FILLORIUM_LOG = REGISTRY.register("fillorium_log", () -> new FilloriumLogBlock());
	public static final RegistryObject<Block> FILLORIUM_PLANKS = REGISTRY.register("fillorium_planks", () -> new FilloriumPlanksBlock());
	public static final RegistryObject<Block> FILLORIUM_LEAVES = REGISTRY.register("fillorium_leaves", () -> new FilloriumLeavesBlock());
	public static final RegistryObject<Block> FILLORIUM_STAIRS = REGISTRY.register("fillorium_stairs", () -> new FilloriumStairsBlock());
	public static final RegistryObject<Block> FILLORIUM_SLAB = REGISTRY.register("fillorium_slab", () -> new FilloriumSlabBlock());
	public static final RegistryObject<Block> FILLORIUM_FENCE = REGISTRY.register("fillorium_fence", () -> new FilloriumFenceBlock());
	public static final RegistryObject<Block> FILLORIUM_FENCE_GATE = REGISTRY.register("fillorium_fence_gate", () -> new FilloriumFenceGateBlock());
	public static final RegistryObject<Block> FILLORIUM_PRESSURE_PLATE = REGISTRY.register("fillorium_pressure_plate",
			() -> new FilloriumPressurePlateBlock());
	public static final RegistryObject<Block> FILLORIUM_BUTTON = REGISTRY.register("fillorium_button", () -> new FilloriumButtonBlock());
	public static final RegistryObject<Block> COPPER_ORE = REGISTRY.register("copper_ore", () -> new CopperOreBlock());
	public static final RegistryObject<Block> COPPER_BLOCK = REGISTRY.register("copper_block", () -> new CopperBlockBlock());
	public static final RegistryObject<Block> RUBY_ORE = REGISTRY.register("ruby_ore", () -> new RubyOreBlock());
	public static final RegistryObject<Block> RUBY_BLOCK = REGISTRY.register("ruby_block", () -> new RubyBlockBlock());
	public static final RegistryObject<Block> CRYSTALBLOCK = REGISTRY.register("crystalblock", () -> new CrystalblockBlock());
	public static final RegistryObject<Block> REFORGE_ANVIL_BLOCK = REGISTRY.register("reforge_anvil_block", () -> new ReforgeAnvilBlockBlock());
	public static final RegistryObject<Block> WITHER_KING_SPAWN_BLOCK = REGISTRY.register("wither_king_spawn_block",
			() -> new WitherKingSpawnBlockBlock());
	public static final RegistryObject<Block> VOID_GLOOM_BLOCK = REGISTRY.register("void_gloom_block", () -> new VoidGloomBlockBlock());
	public static final RegistryObject<Block> VOIDGLOOMCHESTPLACER = REGISTRY.register("voidgloomchestplacer", () -> new VoidgloomchestplacerBlock());

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientSideHandler {
		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			CrystalblockBlock.registerRenderLayer();
		}
	}
}
