package net.kapitencraft.mysticcraft.init;

import com.google.common.collect.HashMultimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.gui.GUISlotBlockItem;
import net.kapitencraft.mysticcraft.guild.GuildUpgrade;
import net.kapitencraft.mysticcraft.guild.GuildUpgrades;
import net.kapitencraft.mysticcraft.item.ElementalShard;
import net.kapitencraft.mysticcraft.item.QuiverItem;
import net.kapitencraft.mysticcraft.item.combat.armor.*;
import net.kapitencraft.mysticcraft.item.combat.shield.GoldenShield;
import net.kapitencraft.mysticcraft.item.combat.shield.IronShield;
import net.kapitencraft.mysticcraft.item.combat.shield.ModShieldItem;
import net.kapitencraft.mysticcraft.item.combat.spells.*;
import net.kapitencraft.mysticcraft.item.combat.spells.necron_sword.*;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.ManaSteelSwordItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow.LongBowItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow.TallinBow;
import net.kapitencraft.mysticcraft.item.creative.BuildersWand;
import net.kapitencraft.mysticcraft.item.creative.ModDebugStickItem;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.item.material.DyedLeatherItem;
import net.kapitencraft.mysticcraft.item.material.LavaFishItem;
import net.kapitencraft.mysticcraft.item.material.PrecursorRelicItem;
import net.kapitencraft.mysticcraft.item.material.UnbreakingCore;
import net.kapitencraft.mysticcraft.item.tools.HammerItem;
import net.kapitencraft.mysticcraft.item.tools.fishing_rods.LavaFishingRod;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.Provider;
import net.kapitencraft.mysticcraft.spell.Element;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.kapitencraft.mysticcraft.utils.MiscUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public interface ModItems {

    static <T extends Item> RegistryObject<T> register(String name, Supplier<T> supplier, TabTypes... types) {
        RegistryObject<T> registryObject = REGISTRY.register(name, supplier);
        for (TabTypes types1 : types) {
            tabTypes.put(types1, registryObject);
        }
        return registryObject;
    }

    HashMultimap<TabTypes, RegistryObject<? extends Item>> tabTypes = HashMultimap.create();

    static <T, V extends Item> HashMap<T, RegistryObject<V>> createRegistry(Provider<V, T> provider, Provider<String, T> nameProvider, List<T> values, TabTypes... types) {
        HashMap<T, RegistryObject<V>> map = new HashMap<>();
        for (T t : values) {
            map.put(t, register(nameProvider.provide(t), ()-> provider.provide(t), types));
        }
        return map;
    }


    static Collection<RegistryObject<Item>> getEntries() {
        return REGISTRY.getEntries();
    }

    static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }

    DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MysticcraftMod.MOD_ID);

    RegistryObject<Item> MOD_DEBUG_STICK = register("mod_debug_stick", ModDebugStickItem::new, TabTypes.TOOLS_AND_UTILITIES);
    RegistryObject<Item> BUILDERS_WAND = register("builders_wand", BuildersWand::new, TabTypes.TOOLS_AND_UTILITIES);
    RegistryObject<LongBowItem> LONGBOW = register("longbow", LongBowItem::new, TabTypes.WEAPONS_AND_TOOLS);
    RegistryObject<Item> WIZARD_HAT = register("wizard_hat", WizardHatItem::new, TabTypes.SPELL_AND_GEMSTONE);
    RegistryObject<Item> THE_STAFF_DESTRUCTION = register("staff_of_destruction", TheStaffOfDestruction::new, TabTypes.SPELL_AND_GEMSTONE, TabTypes.WEAPONS_AND_TOOLS);
    RegistryObject<Item> NECRON_SWORD = register("necron_sword", NecronSwordDefaultImpl::new, TabTypes.WEAPONS_AND_TOOLS);
    RegistryObject<Item> HYPERION = register("hyperion", Hyperion::new, TabTypes.SPELL_AND_GEMSTONE, TabTypes.WEAPONS_AND_TOOLS);
    RegistryObject<Item> SCYLLA = register("scylla", Scylla::new, TabTypes.WEAPONS_AND_TOOLS);
    RegistryObject<Item> ASTREA = register("astrea", Astraea::new, TabTypes.WEAPONS_AND_TOOLS);
    RegistryObject<Item> VALKYRIE = register("valkyrie", Valkyrie::new, TabTypes.WEAPONS_AND_TOOLS);
    RegistryObject<Item> AOTE = register("aspect_of_the_end", ()-> new AspectOfTheEndItem(50), TabTypes.SPELL_AND_GEMSTONE);
    RegistryObject<Item> AOTV = register("aspect_of_the_void", AspectOfTheVoidItem::new, TabTypes.SPELL_AND_GEMSTONE);
    RegistryObject<Item> HEATED_SCYTHE = register("heated_scythe", HeatedScythe::new, TabTypes.SPELL_AND_GEMSTONE, TabTypes.WEAPONS_AND_TOOLS);
    RegistryObject<Item> FIERY_SCYTHE = register("fiery_scythe", FieryScythe::new, TabTypes.SPELL_AND_GEMSTONE, TabTypes.WEAPONS_AND_TOOLS);
    RegistryObject<Item> BURNING_SCYTHE = register("burning_scythe", BurningScythe::new, TabTypes.SPELL_AND_GEMSTONE, TabTypes.WEAPONS_AND_TOOLS);
    RegistryObject<Item> INFERNAL_SCYTHE = register("infernal_scythe", InfernalScythe::new, TabTypes.SPELL_AND_GEMSTONE, TabTypes.WEAPONS_AND_TOOLS);
    RegistryObject<Item> FIRE_LANCE = register("fire_lance", FireLance::new, TabTypes.SPELL_AND_GEMSTONE, TabTypes.WEAPONS_AND_TOOLS);
    RegistryObject<Item> STAFF_OF_THE_WILD = register("staff_of_the_wild", StaffOfTheWildItem::new, TabTypes.SPELL_AND_GEMSTONE);
    RegistryObject<Item> LAVA_FISHING_ROD_TEST = register("lava_fishing_rod", () -> new LavaFishingRod(Rarity.RARE), TabTypes.WEAPONS_AND_TOOLS);
    HashMap<Spells, RegistryObject<Item>> SCROLLS = Spells.registerAll();
    RegistryObject<Item> NECRONS_HANDLE = register("necrons_handle", () ->  new Item(MiscUtils.rarity(FormattingCodes.LEGENDARY)), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> UNBREAKING_CORE = register("unbreaking_core", UnbreakingCore::new, TabTypes.MOD_MATERIALS);
    RegistryObject<Item> MANA_STEEL_INGOT = register("mana_steel_ingot", () -> new Item(MiscUtils.rarity(Rarity.RARE)), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> UPPER_BlADE_MS = register("upper_mana_steel_sword_part", () -> new Item(MiscUtils.rarity(Rarity.RARE).stacksTo(1)), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> DOWN_BlADE_MS = register("lower_mana_steel_sword_part", () -> new Item(MiscUtils.rarity(Rarity.RARE).stacksTo(1)), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> MS_HANDLE = register("mana_steel_sword_handle", () -> new Item(MiscUtils.rarity(Rarity.RARE).stacksTo(1)), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> HEART_OF_THE_NETHER = register("heart_of_the_nether", ()-> new Item(MiscUtils.rarity(FormattingCodes.MYTHIC).stacksTo(1)), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> CRIMSON_STEEL_INGOT = register("crimson_steel_ingot", ()-> new Item(MiscUtils.rarity(Rarity.EPIC)), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> CRIMSON_STEEL_DUST = register("crimson_steel_dust", ()-> new Item(MiscUtils.rarity(Rarity.EPIC)), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> CRIMSONITE_CLUSTER = register("crimsonite_cluster", ()-> new Item(MiscUtils.rarity(Rarity.UNCOMMON)), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> CRIMSONITE_DUST = register("crimsonite_dust", ()-> new Item(MiscUtils.rarity(Rarity.RARE)), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> CRIMSONIUM_DUST = register("crimsonium_dust", ()-> new Item(MiscUtils.rarity(Rarity.RARE)), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> CRIMSONIUM_INGOT = register("crimsonium_ingot", ()-> new Item(MiscUtils.rarity(Rarity.RARE)), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> RAW_CRIMSONIUM = register("raw_crimsonium", ()-> new Item(MiscUtils.rarity(Rarity.UNCOMMON)), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> RAW_CRIMSONIUM_DUST = register("raw_crimsonium_dust", ()-> new Item(MiscUtils.rarity(Rarity.RARE)), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> HARDENED_TEAR = register("hardened_tear", ()-> new Item(MiscUtils.rarity(Rarity.UNCOMMON)), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> STONE_HAMMER = register("stone_hammer", ()-> new HammerItem(MiscUtils.rarity(Rarity.COMMON), 354), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> IRON_HAMMER = register("iron_hammer", ()-> new HammerItem(MiscUtils.rarity(Rarity.UNCOMMON), 530), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> DIAMOND_HAMMER = register("diamond_hammer", ()-> new HammerItem(MiscUtils.rarity(Rarity.RARE), 846), TabTypes.MOD_MATERIALS);
    HashMap<Element, RegistryObject<Item>> ELEMENTAL_SHARDS = ElementalShard.registerElementShards();
    RegistryObject<Item> FROZEN_BLAZE_ROD = register("frozen_blaze_rod", () -> new Item(MiscUtils.rarity(Rarity.RARE)), TabTypes.MOD_MATERIALS);
    RegistryObject<Item> TALLIN_BOW = register("tallin_bow", TallinBow::new, TabTypes.WEAPONS_AND_TOOLS);
    RegistryObject<Item> MANA_STEEL_SWORD = register("mana_steel_sword", ManaSteelSwordItem::new, TabTypes.WEAPONS_AND_TOOLS);
    RegistryObject<ModShieldItem> IRON_SHIELD = register("iron_shield", IronShield::new, TabTypes.WEAPONS_AND_TOOLS);
    RegistryObject<ModShieldItem> GOLDEN_SHIELD = register("golden_shield", GoldenShield::new, TabTypes.WEAPONS_AND_TOOLS);
    RegistryObject<Item> DYED_LEATHER = register("dyed_leather",  DyedLeatherItem::new, TabTypes.MOD_MATERIALS);

    RegistryObject<QuiverItem> AMETHYST_QUIVER = register("amethyst_quiver", ()-> new QuiverItem(MiscUtils.rarity(Rarity.RARE), 16), TabTypes.WEAPONS_AND_TOOLS);
    RegistryObject<Item> BLAZING_SALMON = register("blazing_salmon", ()-> new LavaFishItem(1, 1.2f, new MobEffectInstance(ModMobEffects.IGNITION.get(), 100, 1)), TabTypes.MOD_MATERIALS, TabTypes.FOOD_AND_DRINK);
    RegistryObject<Item> MAGMA_COD = register("magma_cod", ()-> new LavaFishItem(2, 1.1f, new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 150, 1)), TabTypes.MOD_MATERIALS, TabTypes.FOOD_AND_DRINK);

    HashMap<EquipmentSlot, RegistryObject<Item>> ENDER_KNIGHT_ARMOR = ModArmorItem.createRegistry("ender_knight", EnderKnightArmorItem::new);
    HashMap<EquipmentSlot, RegistryObject<Item>> FROZEN_BLAZE_ARMOR = ModArmorItem.createRegistry("frozen_blaze", FrozenBlazeArmorItem::new);
    HashMap<EquipmentSlot, RegistryObject<Item>> SHADOW_ASSASSIN_ARMOR = ModArmorItem.createRegistry("shadow_assassin", ShadowAssassinArmorItem::new);
    HashMap<EquipmentSlot, RegistryObject<Item>> CRIMSON_ARMOR = ModArmorItem.createRegistry("crimson", CrimsonArmorItem::new);
    HashMap<EquipmentSlot, RegistryObject<Item>> SOUL_MAGE_ARMOR = ModArmorItem.createRegistry("soul_mage", SoulMageArmorItem::new);
    HashMap<EquipmentSlot, RegistryObject<Item>> WARPED_ARMOR = ModArmorItem.createRegistry("warped", WarpedArmorItem::new);

    RegistryObject<Item> FROZEN_BLAZE_SPAWN_EGG = register("frozen_blaze_spawn_egg", ()-> new ForgeSpawnEggItem(ModEntityTypes.FROZEN_BLAZE, -16711681, -16763956, new Item.Properties()), TabTypes.SPAWN_EGGS);

    RegistryObject<Item> MISSING_GEMSTONE_SLOT = register("missing_slot", ()-> new GUISlotBlockItem().putTooltip(List.of(Component.literal("This Gemstone Applicable has no gemstone at that slot").withStyle(ChatFormatting.RED))));
    RegistryObject<Item> EMPTY_APPLICABLE_SLOT = register("empty_applicable", ()-> new GUISlotBlockItem().putTooltip(List.of(Component.literal("There is no Gemstone Applicable in it's slot").withStyle(ChatFormatting.RED))));
    RegistryObject<BucketItem> BUCKET_OF_MANA = register("bucket_of_mana", ()-> new BucketItem(ModFluids.SOURCE_MANA_FLUID, MiscUtils.rarity(Rarity.EPIC).stacksTo(1)), TabTypes.MOD_MATERIALS);
    HashMap<GemstoneType, HashMap<GemstoneType.Rarity, RegistryObject<GemstoneItem>>> GEMSTONES = GemstoneType.createRegistry();
    HashMap<GuildUpgrade, RegistryObject<Item>> GUILD_UPGRADES = GuildUpgrades.createRegistry();
    HashMap<PrecursorRelicItem.BossType, RegistryObject<PrecursorRelicItem>> PRECURSOR_RELICTS = PrecursorRelicItem.makeRegistry();

    enum TabTypes {
        SPELL_AND_GEMSTONE,
        MOD_MATERIALS,
        WEAPONS_AND_TOOLS,
        SPAWN_EGGS,
        FOOD_AND_DRINK,
        TOOLS_AND_UTILITIES
    }
}