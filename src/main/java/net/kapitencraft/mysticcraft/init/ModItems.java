package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.gui.GUISlotBlockItem;
import net.kapitencraft.mysticcraft.item.WizardHatItem;
import net.kapitencraft.mysticcraft.item.armor.EnderKnightArmorItem;
import net.kapitencraft.mysticcraft.item.armor.FrozenBlazeArmorItem;
import net.kapitencraft.mysticcraft.item.bow.LongBowItem;
import net.kapitencraft.mysticcraft.item.bow.TallinBow;
import net.kapitencraft.mysticcraft.item.gemstone.Gemstone;
import net.kapitencraft.mysticcraft.item.gemstone.gemstones.AlmandineGemstone;
import net.kapitencraft.mysticcraft.item.gemstone.gemstones.JasperGemstone;
import net.kapitencraft.mysticcraft.item.gemstone.gemstones.RubyGemstone;
import net.kapitencraft.mysticcraft.item.gemstone.gemstones.SapphireGemstone;
import net.kapitencraft.mysticcraft.item.spells.SlivyraItem;
import net.kapitencraft.mysticcraft.item.spells.StaffOfTheWildItem;
import net.kapitencraft.mysticcraft.item.spells.TheStaffOfDestruction;
import net.kapitencraft.mysticcraft.item.sword.ManaSteelSword;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;

public abstract class ModItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MysticcraftMod.MOD_ID);
    public static final RegistryObject<Item> LONGBOW = REGISTRY.register("longbow", LongBowItem::new);
    public static final RegistryObject<Item> WIZARD_HAT = REGISTRY.register("wizard_hat", WizardHatItem::new);
    public static final RegistryObject<Item> THE_STAFF_DESTRUCTION = REGISTRY.register("staff_of_destruction", TheStaffOfDestruction::new);
    public static final RegistryObject<Item> SLIVYRA = REGISTRY.register("slivyra", SlivyraItem::new);
    public static final RegistryObject<Item> SPELL_SHARD = REGISTRY.register("spell_shard", () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.SPELL_AND_GEMSTONE).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MANA_STEEL_INGOT = REGISTRY.register("mana_steel_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).rarity(Rarity.RARE)));

    public static final HashMap<Gemstone.Rarity, RegistryObject<Item>> ALMANDINE_GEMSTONES = AlmandineGemstone.registerItems(REGISTRY, "almandine");
    public static final HashMap<Gemstone.Rarity, RegistryObject<Item>> JASPER_GEMSTONES = JasperGemstone.registerItems(REGISTRY, "jasper");
    public static final HashMap<Gemstone.Rarity, RegistryObject<Item>> RUBY_GEMSTONES = RubyGemstone.registerItems(REGISTRY, "ruby");
    public static final HashMap<Gemstone.Rarity, RegistryObject<Item>> SAPPHIRE_GEMSTONES = SapphireGemstone.registerItems(REGISTRY, "sapphire");

    public static final RegistryObject<Item> STAFF_OF_THE_WILD = REGISTRY.register("staff_of_the_wild", StaffOfTheWildItem::new);
    public static final RegistryObject<Item> TALLIN_BOW = REGISTRY.register("tallin_bow", TallinBow::new);
    public static final RegistryObject<Item> MANA_STEEL_SWORD = REGISTRY.register("mana_steel_sword", ManaSteelSword::new);

    public static final HashMap<EquipmentSlot, RegistryObject<Item>> ENDER_KNIGHT_ARMOR = EnderKnightArmorItem.createRegistry(REGISTRY);
    public static final HashMap<EquipmentSlot, RegistryObject<Item>> FROZEN_BLAZE_ARMOR = FrozenBlazeArmorItem.createRegistry(REGISTRY);

    public static final RegistryObject<Item> FROZEN_BLAZE_SPAWN_EGG = REGISTRY.register("frozen_blaze_spawn_egg", ()-> new ForgeSpawnEggItem(ModEntityTypes.FROZEN_BLAZE, -16711681, -16763956, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> GUI_SLOT_BLOCK_ITEM = REGISTRY.register("gui_slot_block", GUISlotBlockItem::new);
}