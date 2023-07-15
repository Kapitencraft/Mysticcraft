
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.kapitencraft.mysticcraft.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.ForgeSpawnEggItem;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.BlockItem;

import net.kapitencraft.mysticcraft.item.WitherBoneItem;
import net.kapitencraft.mysticcraft.item.WaterDragonScaleItem;
import net.kapitencraft.mysticcraft.item.WaterDragonArmorItem;
import net.kapitencraft.mysticcraft.item.WandofRegenerationItem;
import net.kapitencraft.mysticcraft.item.WandofMendingItem;
import net.kapitencraft.mysticcraft.item.WandofHealingItem;
import net.kapitencraft.mysticcraft.item.VoidGloomSphereItem;
import net.kapitencraft.mysticcraft.item.VoidGloomArmorItem;
import net.kapitencraft.mysticcraft.item.TotemOfPeaceItem;
import net.kapitencraft.mysticcraft.item.TitaniumSwordItem;
import net.kapitencraft.mysticcraft.item.TitaniumShovelItem;
import net.kapitencraft.mysticcraft.item.TitaniumPickaxeItem;
import net.kapitencraft.mysticcraft.item.TitaniumIngotItem;
import net.kapitencraft.mysticcraft.item.TitaniumHoeItem;
import net.kapitencraft.mysticcraft.item.TitaniumAxeItem;
import net.kapitencraft.mysticcraft.item.TitaniumArmorItem;
import net.kapitencraft.mysticcraft.item.TheStuffOfDestructionItem;
import net.kapitencraft.mysticcraft.item.TeleportBowItem;
import net.kapitencraft.mysticcraft.item.TarrantulaArmorItem;
import net.kapitencraft.mysticcraft.item.TallionItem;
import net.kapitencraft.mysticcraft.item.SpiderArmorItem;
import net.kapitencraft.mysticcraft.item.SlivyraItem;
import net.kapitencraft.mysticcraft.item.ShadowWarpItem;
import net.kapitencraft.mysticcraft.item.ShadowWarpBowItem;
import net.kapitencraft.mysticcraft.item.RubySwordItem;
import net.kapitencraft.mysticcraft.item.RubyShovelItem;
import net.kapitencraft.mysticcraft.item.RubyPickaxeItem;
import net.kapitencraft.mysticcraft.item.RubyItem;
import net.kapitencraft.mysticcraft.item.RubyHoeItem;
import net.kapitencraft.mysticcraft.item.RubyAxeItem;
import net.kapitencraft.mysticcraft.item.RubyArmorItem;
import net.kapitencraft.mysticcraft.item.ReforgedSpiderEyeItem;
import net.kapitencraft.mysticcraft.item.RefinedIronItem;
import net.kapitencraft.mysticcraft.item.RefinedIronArmorItem;
import net.kapitencraft.mysticcraft.item.RefinedGoldItem;
import net.kapitencraft.mysticcraft.item.RefinedGoldArmorItem;
import net.kapitencraft.mysticcraft.item.RefinedDiamondItem;
import net.kapitencraft.mysticcraft.item.RefinedDiamondArmorItem;
import net.kapitencraft.mysticcraft.item.PlasmaCoreItem;
import net.kapitencraft.mysticcraft.item.LongBowItem;
import net.kapitencraft.mysticcraft.item.LightningStaffItem;
import net.kapitencraft.mysticcraft.item.LightningShieldSpellItem;
import net.kapitencraft.mysticcraft.item.LightningRushItem;
import net.kapitencraft.mysticcraft.item.LightDragonScaleItem;
import net.kapitencraft.mysticcraft.item.IceDragonScaleItem;
import net.kapitencraft.mysticcraft.item.IceDragonArmorItem;
import net.kapitencraft.mysticcraft.item.HomingbowItem;
import net.kapitencraft.mysticcraft.item.FreezingBoneItem;
import net.kapitencraft.mysticcraft.item.FireDragonScaleItem;
import net.kapitencraft.mysticcraft.item.FireDragonArmorItem;
import net.kapitencraft.mysticcraft.item.ExplosivStaffItem;
import net.kapitencraft.mysticcraft.item.EnchantedBoneItem;
import net.kapitencraft.mysticcraft.item.DragonBoneItem;
import net.kapitencraft.mysticcraft.item.CopperSwordItem;
import net.kapitencraft.mysticcraft.item.CopperShovelItem;
import net.kapitencraft.mysticcraft.item.CopperPickaxeItem;
import net.kapitencraft.mysticcraft.item.CopperIngotItem;
import net.kapitencraft.mysticcraft.item.CopperHoeItem;
import net.kapitencraft.mysticcraft.item.CopperAxeItem;
import net.kapitencraft.mysticcraft.item.CopperArmorItem;
import net.kapitencraft.mysticcraft.item.BlueWalArmorItem;
import net.kapitencraft.mysticcraft.item.BearItem;
import net.kapitencraft.mysticcraft.item.BeamMeUpSpellItem;
import net.kapitencraft.mysticcraft.item.AspectoftheDragonsItem;
import net.kapitencraft.mysticcraft.item.AmethistSwordItem;
import net.kapitencraft.mysticcraft.item.AmethistShovelItem;
import net.kapitencraft.mysticcraft.item.AmethistPickaxeItem;
import net.kapitencraft.mysticcraft.item.AmethistHoeItem;
import net.kapitencraft.mysticcraft.item.AmethistAxeItem;
import net.kapitencraft.mysticcraft.item.AmethistArmorItem;
import net.kapitencraft.mysticcraft.item.AOTVItem;
import net.kapitencraft.mysticcraft.item.AOTEItem;
import net.kapitencraft.mysticcraft.MysticcraftMod;

public class MysticcraftModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MysticcraftMod.MODID);
	public static final RegistryObject<Item> ASPECTOFTHE_DRAGONS = REGISTRY.register("aspectofthe_dragons", () -> new AspectoftheDragonsItem());
	public static final RegistryObject<Item> MITHRIL_ORE = block(MysticcraftModBlocks.MITHRIL_ORE, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> AMETHIST_PICKAXE = REGISTRY.register("amethist_pickaxe", () -> new AmethistPickaxeItem());
	public static final RegistryObject<Item> AMETHIST_AXE = REGISTRY.register("amethist_axe", () -> new AmethistAxeItem());
	public static final RegistryObject<Item> AMETHIST_SWORD = REGISTRY.register("amethist_sword", () -> new AmethistSwordItem());
	public static final RegistryObject<Item> AMETHIST_SHOVEL = REGISTRY.register("amethist_shovel", () -> new AmethistShovelItem());
	public static final RegistryObject<Item> AMETHIST_HOE = REGISTRY.register("amethist_hoe", () -> new AmethistHoeItem());
	public static final RegistryObject<Item> AMETHIST_ARMOR_HELMET = REGISTRY.register("amethist_armor_helmet", () -> new AmethistArmorItem.Helmet());
	public static final RegistryObject<Item> AMETHIST_ARMOR_CHESTPLATE = REGISTRY.register("amethist_armor_chestplate",
			() -> new AmethistArmorItem.Chestplate());
	public static final RegistryObject<Item> AMETHIST_ARMOR_LEGGINGS = REGISTRY.register("amethist_armor_leggings",
			() -> new AmethistArmorItem.Leggings());
	public static final RegistryObject<Item> AMETHIST_ARMOR_BOOTS = REGISTRY.register("amethist_armor_boots", () -> new AmethistArmorItem.Boots());
	public static final RegistryObject<Item> TERIUM_WOOD = block(MysticcraftModBlocks.TERIUM_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> TERIUM_LOG = block(MysticcraftModBlocks.TERIUM_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> TERIUM_PLANKS = block(MysticcraftModBlocks.TERIUM_PLANKS, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> TERIUM_LEAVES = block(MysticcraftModBlocks.TERIUM_LEAVES, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Item> TERIUM_STAIRS = block(MysticcraftModBlocks.TERIUM_STAIRS, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> TERIUM_SLAB = block(MysticcraftModBlocks.TERIUM_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> TERIUM_FENCE = block(MysticcraftModBlocks.TERIUM_FENCE, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Item> TERIUM_FENCE_GATE = block(MysticcraftModBlocks.TERIUM_FENCE_GATE, CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Item> TERIUM_PRESSURE_PLATE = block(MysticcraftModBlocks.TERIUM_PRESSURE_PLATE, CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Item> TERIUM_BUTTON = block(MysticcraftModBlocks.TERIUM_BUTTON, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> TITANIUM_INGOT = REGISTRY.register("titanium_ingot", () -> new TitaniumIngotItem());
	public static final RegistryObject<Item> TITANIUM_ORE = block(MysticcraftModBlocks.TITANIUM_ORE, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> TITANIUM_BLOCK = block(MysticcraftModBlocks.TITANIUM_BLOCK, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> TITANIUM_PICKAXE = REGISTRY.register("titanium_pickaxe", () -> new TitaniumPickaxeItem());
	public static final RegistryObject<Item> TITANIUM_AXE = REGISTRY.register("titanium_axe", () -> new TitaniumAxeItem());
	public static final RegistryObject<Item> TITANIUM_SWORD = REGISTRY.register("titanium_sword", () -> new TitaniumSwordItem());
	public static final RegistryObject<Item> TITANIUM_SHOVEL = REGISTRY.register("titanium_shovel", () -> new TitaniumShovelItem());
	public static final RegistryObject<Item> TITANIUM_HOE = REGISTRY.register("titanium_hoe", () -> new TitaniumHoeItem());
	public static final RegistryObject<Item> TITANIUM_ARMOR_HELMET = REGISTRY.register("titanium_armor_helmet", () -> new TitaniumArmorItem.Helmet());
	public static final RegistryObject<Item> TITANIUM_ARMOR_CHESTPLATE = REGISTRY.register("titanium_armor_chestplate",
			() -> new TitaniumArmorItem.Chestplate());
	public static final RegistryObject<Item> TITANIUM_ARMOR_LEGGINGS = REGISTRY.register("titanium_armor_leggings",
			() -> new TitaniumArmorItem.Leggings());
	public static final RegistryObject<Item> TITANIUM_ARMOR_BOOTS = REGISTRY.register("titanium_armor_boots", () -> new TitaniumArmorItem.Boots());
	public static final RegistryObject<Item> FILLORIUM_WOOD = block(MysticcraftModBlocks.FILLORIUM_WOOD, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> FILLORIUM_LOG = block(MysticcraftModBlocks.FILLORIUM_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> FILLORIUM_PLANKS = block(MysticcraftModBlocks.FILLORIUM_PLANKS, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> FILLORIUM_LEAVES = block(MysticcraftModBlocks.FILLORIUM_LEAVES, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Item> FILLORIUM_STAIRS = block(MysticcraftModBlocks.FILLORIUM_STAIRS, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> FILLORIUM_SLAB = block(MysticcraftModBlocks.FILLORIUM_SLAB, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> FILLORIUM_FENCE = block(MysticcraftModBlocks.FILLORIUM_FENCE, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Item> FILLORIUM_FENCE_GATE = block(MysticcraftModBlocks.FILLORIUM_FENCE_GATE, CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Item> FILLORIUM_PRESSURE_PLATE = block(MysticcraftModBlocks.FILLORIUM_PRESSURE_PLATE,
			CreativeModeTab.TAB_REDSTONE);
	public static final RegistryObject<Item> FILLORIUM_BUTTON = block(MysticcraftModBlocks.FILLORIUM_BUTTON, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> COPPER_INGOT = REGISTRY.register("copper_ingot", () -> new CopperIngotItem());
	public static final RegistryObject<Item> COPPER_ORE = block(MysticcraftModBlocks.COPPER_ORE, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> COPPER_BLOCK = block(MysticcraftModBlocks.COPPER_BLOCK, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> COPPER_PICKAXE = REGISTRY.register("copper_pickaxe", () -> new CopperPickaxeItem());
	public static final RegistryObject<Item> COPPER_AXE = REGISTRY.register("copper_axe", () -> new CopperAxeItem());
	public static final RegistryObject<Item> COPPER_SWORD = REGISTRY.register("copper_sword", () -> new CopperSwordItem());
	public static final RegistryObject<Item> COPPER_SHOVEL = REGISTRY.register("copper_shovel", () -> new CopperShovelItem());
	public static final RegistryObject<Item> COPPER_HOE = REGISTRY.register("copper_hoe", () -> new CopperHoeItem());
	public static final RegistryObject<Item> COPPER_ARMOR_HELMET = REGISTRY.register("copper_armor_helmet", () -> new CopperArmorItem.Helmet());
	public static final RegistryObject<Item> COPPER_ARMOR_CHESTPLATE = REGISTRY.register("copper_armor_chestplate",
			() -> new CopperArmorItem.Chestplate());
	public static final RegistryObject<Item> COPPER_ARMOR_LEGGINGS = REGISTRY.register("copper_armor_leggings", () -> new CopperArmorItem.Leggings());
	public static final RegistryObject<Item> COPPER_ARMOR_BOOTS = REGISTRY.register("copper_armor_boots", () -> new CopperArmorItem.Boots());
	public static final RegistryObject<Item> RUBY = REGISTRY.register("ruby", () -> new RubyItem());
	public static final RegistryObject<Item> RUBY_ORE = block(MysticcraftModBlocks.RUBY_ORE, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> RUBY_BLOCK = block(MysticcraftModBlocks.RUBY_BLOCK, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> RUBY_PICKAXE = REGISTRY.register("ruby_pickaxe", () -> new RubyPickaxeItem());
	public static final RegistryObject<Item> RUBY_AXE = REGISTRY.register("ruby_axe", () -> new RubyAxeItem());
	public static final RegistryObject<Item> RUBY_SWORD = REGISTRY.register("ruby_sword", () -> new RubySwordItem());
	public static final RegistryObject<Item> RUBY_SHOVEL = REGISTRY.register("ruby_shovel", () -> new RubyShovelItem());
	public static final RegistryObject<Item> RUBY_HOE = REGISTRY.register("ruby_hoe", () -> new RubyHoeItem());
	public static final RegistryObject<Item> RUBY_ARMOR_HELMET = REGISTRY.register("ruby_armor_helmet", () -> new RubyArmorItem.Helmet());
	public static final RegistryObject<Item> RUBY_ARMOR_CHESTPLATE = REGISTRY.register("ruby_armor_chestplate", () -> new RubyArmorItem.Chestplate());
	public static final RegistryObject<Item> RUBY_ARMOR_LEGGINGS = REGISTRY.register("ruby_armor_leggings", () -> new RubyArmorItem.Leggings());
	public static final RegistryObject<Item> RUBY_ARMOR_BOOTS = REGISTRY.register("ruby_armor_boots", () -> new RubyArmorItem.Boots());
	public static final RegistryObject<Item> HOMINGBOW = REGISTRY.register("homingbow", () -> new HomingbowItem());
	public static final RegistryObject<Item> CRYSTALBLOCK = block(MysticcraftModBlocks.CRYSTALBLOCK, CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> WANDOF_HEALING = REGISTRY.register("wandof_healing", () -> new WandofHealingItem());
	public static final RegistryObject<Item> WANDOF_MENDING = REGISTRY.register("wandof_mending", () -> new WandofMendingItem());
	public static final RegistryObject<Item> WANDOF_REGENERATION = REGISTRY.register("wandof_regeneration", () -> new WandofRegenerationItem());
	public static final RegistryObject<Item> SPIDER_ARMOR_HELMET = REGISTRY.register("spider_armor_helmet", () -> new SpiderArmorItem.Helmet());
	public static final RegistryObject<Item> SPIDER_ARMOR_CHESTPLATE = REGISTRY.register("spider_armor_chestplate",
			() -> new SpiderArmorItem.Chestplate());
	public static final RegistryObject<Item> SPIDER_ARMOR_LEGGINGS = REGISTRY.register("spider_armor_leggings", () -> new SpiderArmorItem.Leggings());
	public static final RegistryObject<Item> SPIDER_ARMOR_BOOTS = REGISTRY.register("spider_armor_boots", () -> new SpiderArmorItem.Boots());
	public static final RegistryObject<Item> REFORGED_SPIDER_EYE = REGISTRY.register("reforged_spider_eye", () -> new ReforgedSpiderEyeItem());
	public static final RegistryObject<Item> TARRANTULA_ARMOR_HELMET = REGISTRY.register("tarrantula_armor_helmet",
			() -> new TarrantulaArmorItem.Helmet());
	public static final RegistryObject<Item> TARRANTULA_ARMOR_CHESTPLATE = REGISTRY.register("tarrantula_armor_chestplate",
			() -> new TarrantulaArmorItem.Chestplate());
	public static final RegistryObject<Item> TARRANTULA_ARMOR_LEGGINGS = REGISTRY.register("tarrantula_armor_leggings",
			() -> new TarrantulaArmorItem.Leggings());
	public static final RegistryObject<Item> TARRANTULA_ARMOR_BOOTS = REGISTRY.register("tarrantula_armor_boots",
			() -> new TarrantulaArmorItem.Boots());
	public static final RegistryObject<Item> REFORGE_ANVIL_BLOCK = block(MysticcraftModBlocks.REFORGE_ANVIL_BLOCK,
			CreativeModeTab.TAB_BUILDING_BLOCKS);
	public static final RegistryObject<Item> FIRE_DRAGON_SCALE = REGISTRY.register("fire_dragon_scale", () -> new FireDragonScaleItem());
	public static final RegistryObject<Item> ICE_DRAGON_SCALE = REGISTRY.register("ice_dragon_scale", () -> new IceDragonScaleItem());
	public static final RegistryObject<Item> LIGHT_DRAGON_SCALE = REGISTRY.register("light_dragon_scale", () -> new LightDragonScaleItem());
	public static final RegistryObject<Item> WATER_DRAGON_SCALE = REGISTRY.register("water_dragon_scale", () -> new WaterDragonScaleItem());
	public static final RegistryObject<Item> DRAGON_BONE = REGISTRY.register("dragon_bone", () -> new DragonBoneItem());
	public static final RegistryObject<Item> WITHER_BONE = REGISTRY.register("wither_bone", () -> new WitherBoneItem());
	public static final RegistryObject<Item> ENCHANTED_BONE = REGISTRY.register("enchanted_bone", () -> new EnchantedBoneItem());
	public static final RegistryObject<Item> FREEZING_BONE = REGISTRY.register("freezing_bone", () -> new FreezingBoneItem());
	public static final RegistryObject<Item> ICE_DRAGON_ARMOR_HELMET = REGISTRY.register("ice_dragon_armor_helmet",
			() -> new IceDragonArmorItem.Helmet());
	public static final RegistryObject<Item> ICE_DRAGON_ARMOR_CHESTPLATE = REGISTRY.register("ice_dragon_armor_chestplate",
			() -> new IceDragonArmorItem.Chestplate());
	public static final RegistryObject<Item> ICE_DRAGON_ARMOR_LEGGINGS = REGISTRY.register("ice_dragon_armor_leggings",
			() -> new IceDragonArmorItem.Leggings());
	public static final RegistryObject<Item> ICE_DRAGON_ARMOR_BOOTS = REGISTRY.register("ice_dragon_armor_boots",
			() -> new IceDragonArmorItem.Boots());
	public static final RegistryObject<Item> FIRE_DRAGON_ARMOR_HELMET = REGISTRY.register("fire_dragon_armor_helmet",
			() -> new FireDragonArmorItem.Helmet());
	public static final RegistryObject<Item> FIRE_DRAGON_ARMOR_CHESTPLATE = REGISTRY.register("fire_dragon_armor_chestplate",
			() -> new FireDragonArmorItem.Chestplate());
	public static final RegistryObject<Item> FIRE_DRAGON_ARMOR_LEGGINGS = REGISTRY.register("fire_dragon_armor_leggings",
			() -> new FireDragonArmorItem.Leggings());
	public static final RegistryObject<Item> FIRE_DRAGON_ARMOR_BOOTS = REGISTRY.register("fire_dragon_armor_boots",
			() -> new FireDragonArmorItem.Boots());
	public static final RegistryObject<Item> WATER_DRAGON_ARMOR_HELMET = REGISTRY.register("water_dragon_armor_helmet",
			() -> new WaterDragonArmorItem.Helmet());
	public static final RegistryObject<Item> WATER_DRAGON_ARMOR_CHESTPLATE = REGISTRY.register("water_dragon_armor_chestplate",
			() -> new WaterDragonArmorItem.Chestplate());
	public static final RegistryObject<Item> WATER_DRAGON_ARMOR_LEGGINGS = REGISTRY.register("water_dragon_armor_leggings",
			() -> new WaterDragonArmorItem.Leggings());
	public static final RegistryObject<Item> WATER_DRAGON_ARMOR_BOOTS = REGISTRY.register("water_dragon_armor_boots",
			() -> new WaterDragonArmorItem.Boots());
	public static final RegistryObject<Item> GOBLIN = REGISTRY.register("goblin_spawn_egg",
			() -> new ForgeSpawnEggItem(MysticcraftModEntities.GOBLIN, -1, -1, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
	public static final RegistryObject<Item> LIGHTNING_STAFF = REGISTRY.register("lightning_staff", () -> new LightningStaffItem());
	public static final RegistryObject<Item> SLIVYRA = REGISTRY.register("slivyra", () -> new SlivyraItem());
	public static final RegistryObject<Item> EXPLOSIV_STAFF = REGISTRY.register("explosiv_staff", () -> new ExplosivStaffItem());
	public static final RegistryObject<Item> LIGHTNING_RUSH = REGISTRY.register("lightning_rush", () -> new LightningRushItem());
	public static final RegistryObject<Item> LIGHTNING_SHIELD_SPELL = REGISTRY.register("lightning_shield_spell",
			() -> new LightningShieldSpellItem());
	public static final RegistryObject<Item> BEAM_ME_UP_SPELL = REGISTRY.register("beam_me_up_spell", () -> new BeamMeUpSpellItem());
	public static final RegistryObject<Item> SHADOW_WARP_BOW = REGISTRY.register("shadow_warp_bow", () -> new ShadowWarpBowItem());
	public static final RegistryObject<Item> SHADOW_WARP = REGISTRY.register("shadow_warp", () -> new ShadowWarpItem());
	public static final RegistryObject<Item> REFINED_DIAMOND = REGISTRY.register("refined_diamond", () -> new RefinedDiamondItem());
	public static final RegistryObject<Item> REFINED_GOLD = REGISTRY.register("refined_gold", () -> new RefinedGoldItem());
	public static final RegistryObject<Item> REFINED_IRON = REGISTRY.register("refined_iron", () -> new RefinedIronItem());
	public static final RegistryObject<Item> REFINED_DIAMOND_ARMOR_HELMET = REGISTRY.register("refined_diamond_armor_helmet",
			() -> new RefinedDiamondArmorItem.Helmet());
	public static final RegistryObject<Item> REFINED_DIAMOND_ARMOR_CHESTPLATE = REGISTRY.register("refined_diamond_armor_chestplate",
			() -> new RefinedDiamondArmorItem.Chestplate());
	public static final RegistryObject<Item> REFINED_DIAMOND_ARMOR_LEGGINGS = REGISTRY.register("refined_diamond_armor_leggings",
			() -> new RefinedDiamondArmorItem.Leggings());
	public static final RegistryObject<Item> REFINED_DIAMOND_ARMOR_BOOTS = REGISTRY.register("refined_diamond_armor_boots",
			() -> new RefinedDiamondArmorItem.Boots());
	public static final RegistryObject<Item> REFINED_GOLD_ARMOR_HELMET = REGISTRY.register("refined_gold_armor_helmet",
			() -> new RefinedGoldArmorItem.Helmet());
	public static final RegistryObject<Item> REFINED_GOLD_ARMOR_CHESTPLATE = REGISTRY.register("refined_gold_armor_chestplate",
			() -> new RefinedGoldArmorItem.Chestplate());
	public static final RegistryObject<Item> REFINED_GOLD_ARMOR_LEGGINGS = REGISTRY.register("refined_gold_armor_leggings",
			() -> new RefinedGoldArmorItem.Leggings());
	public static final RegistryObject<Item> REFINED_GOLD_ARMOR_BOOTS = REGISTRY.register("refined_gold_armor_boots",
			() -> new RefinedGoldArmorItem.Boots());
	public static final RegistryObject<Item> REFINED_IRON_ARMOR_HELMET = REGISTRY.register("refined_iron_armor_helmet",
			() -> new RefinedIronArmorItem.Helmet());
	public static final RegistryObject<Item> REFINED_IRON_ARMOR_CHESTPLATE = REGISTRY.register("refined_iron_armor_chestplate",
			() -> new RefinedIronArmorItem.Chestplate());
	public static final RegistryObject<Item> REFINED_IRON_ARMOR_LEGGINGS = REGISTRY.register("refined_iron_armor_leggings",
			() -> new RefinedIronArmorItem.Leggings());
	public static final RegistryObject<Item> REFINED_IRON_ARMOR_BOOTS = REGISTRY.register("refined_iron_armor_boots",
			() -> new RefinedIronArmorItem.Boots());
	public static final RegistryObject<Item> WITHER_KING_SPAWN_BLOCK = block(MysticcraftModBlocks.WITHER_KING_SPAWN_BLOCK, null);
	public static final RegistryObject<Item> THE_STUFF_OF_DESTRUCTION = REGISTRY.register("the_stuff_of_destruction",
			() -> new TheStuffOfDestructionItem());
	public static final RegistryObject<Item> TELEPORT_BOW = REGISTRY.register("teleport_bow", () -> new TeleportBowItem());
	public static final RegistryObject<Item> AOTE = REGISTRY.register("aote", () -> new AOTEItem());
	public static final RegistryObject<Item> AOTV = REGISTRY.register("aotv", () -> new AOTVItem());
	public static final RegistryObject<Item> VOID_GLOOM_BLOCK = block(MysticcraftModBlocks.VOID_GLOOM_BLOCK, CreativeModeTab.TAB_DECORATIONS);
	public static final RegistryObject<Item> VOID_GLOOM_SPHERE = REGISTRY.register("void_gloom_sphere", () -> new VoidGloomSphereItem());
	public static final RegistryObject<Item> VOID_GLOOM_ARMOR_HELMET = REGISTRY.register("void_gloom_armor_helmet",
			() -> new VoidGloomArmorItem.Helmet());
	public static final RegistryObject<Item> VOID_GLOOM_ARMOR_CHESTPLATE = REGISTRY.register("void_gloom_armor_chestplate",
			() -> new VoidGloomArmorItem.Chestplate());
	public static final RegistryObject<Item> VOID_GLOOM_ARMOR_LEGGINGS = REGISTRY.register("void_gloom_armor_leggings",
			() -> new VoidGloomArmorItem.Leggings());
	public static final RegistryObject<Item> VOID_GLOOM_ARMOR_BOOTS = REGISTRY.register("void_gloom_armor_boots",
			() -> new VoidGloomArmorItem.Boots());
	public static final RegistryObject<Item> LONG_BOW = REGISTRY.register("long_bow", () -> new LongBowItem());
	public static final RegistryObject<Item> VOIDGLOOMCHESTPLACER = block(MysticcraftModBlocks.VOIDGLOOMCHESTPLACER, null);
	public static final RegistryObject<Item> BEAR = REGISTRY.register("bear", () -> new BearItem());
	public static final RegistryObject<Item> BLUE_WAL_ARMOR_HELMET = REGISTRY.register("blue_wal_armor_helmet", () -> new BlueWalArmorItem.Helmet());
	public static final RegistryObject<Item> BLUE_WAL_ARMOR_CHESTPLATE = REGISTRY.register("blue_wal_armor_chestplate",
			() -> new BlueWalArmorItem.Chestplate());
	public static final RegistryObject<Item> BLUE_WAL_ARMOR_LEGGINGS = REGISTRY.register("blue_wal_armor_leggings",
			() -> new BlueWalArmorItem.Leggings());
	public static final RegistryObject<Item> BLUE_WAL_ARMOR_BOOTS = REGISTRY.register("blue_wal_armor_boots", () -> new BlueWalArmorItem.Boots());
	public static final RegistryObject<Item> PLASMA_CORE = REGISTRY.register("plasma_core", () -> new PlasmaCoreItem());
	public static final RegistryObject<Item> TALLION = REGISTRY.register("tallion", () -> new TallionItem());
	public static final RegistryObject<Item> TOTEM_OF_PEACE = REGISTRY.register("totem_of_peace", () -> new TotemOfPeaceItem());

	private static RegistryObject<Item> block(RegistryObject<Block> block, CreativeModeTab tab) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
	}
}
