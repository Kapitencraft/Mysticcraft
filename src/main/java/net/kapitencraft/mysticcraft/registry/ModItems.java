package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.kap_lib.item.creative_tab.TabGroup;
import net.kapitencraft.kap_lib.util.ExtraRarities;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.combat.armor.*;
import net.kapitencraft.mysticcraft.item.combat.shield.GoldenShield;
import net.kapitencraft.mysticcraft.item.combat.shield.IronShield;
import net.kapitencraft.mysticcraft.item.combat.shield.ModShieldItem;
import net.kapitencraft.mysticcraft.item.combat.spells.*;
import net.kapitencraft.mysticcraft.item.combat.spells.necron_sword.*;
import net.kapitencraft.mysticcraft.item.combat.totems.VoidTotemItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.RainbowSwordItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.dagger.DarkDagger;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.dagger.ShadowDagger;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.DoubleSword;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.GhostlySword;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.ManaSteelSwordItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.ModSwordItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.QuiverItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow.LongBowItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow.ModBowItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow.TallinBow;
import net.kapitencraft.mysticcraft.item.creative.BuildersWand;
import net.kapitencraft.mysticcraft.item.creative.ModDebugStickItem;
import net.kapitencraft.mysticcraft.item.material.*;
import net.kapitencraft.mysticcraft.item.material.containable.WalletItem;
import net.kapitencraft.mysticcraft.item.misc.CursedPearlItem;
import net.kapitencraft.mysticcraft.item.misc.MaterialModItem;
import net.kapitencraft.mysticcraft.item.misc.SplashPotionOfMilkItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroups;
import net.kapitencraft.mysticcraft.item.tools.HammerItem;
import net.kapitencraft.mysticcraft.item.tools.fishing_rods.LavaFishingRod;
import net.kapitencraft.mysticcraft.spell.Element;
import net.kapitencraft.mysticcraft.tech.item.TechWandItem;
import net.kapitencraft.mysticcraft.tech.item.upgrade.ParallelProcessingUpgradeItem;
import net.kapitencraft.mysticcraft.tech.item.upgrade.SpeedUpgradeItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
@SuppressWarnings("unused")
public interface ModItems {

    //region wrapper

    static <T extends Item> DeferredItem<T> register(String name, final Supplier<T> supplier, TabGroup group) {
        DeferredItem<T> registryObject = REGISTRY.register(name, supplier);
        if (group != null) group.add(registryObject);
        return registryObject;
    }

    static DeferredItem<MaterialModItem> registerMaterial(String name, Rarity rarity, TabGroup group) {
        return register(name, ()-> new MaterialModItem(rarity, true), group);
    }

    static <T extends Item> DeferredItem<MaterialModItem> registerNonStackableMaterial(String name, Rarity rarity, TabGroup group) {
        return register(name, ()-> new MaterialModItem(rarity, false), group);
    }

    static <T, V extends Item> HashMap<T, DeferredItem<V>> createRegistry(Function<T, V> provider, Function<T, String> nameProvider, List<T> values, TabGroup group) {
        HashMap<T, DeferredItem<V>> map = new HashMap<>();
        for (T t : values) {
            map.put(t, register(nameProvider.apply(t), ()-> provider.apply(t), group));
        }
        return map;
    }

    //endregion

    static Collection<DeferredHolder<Item, ? extends Item>> getEntries() {
        return REGISTRY.getEntries();
    }

    DeferredRegister.Items REGISTRY = DeferredRegister.createItems(MysticcraftMod.MOD_ID);

    //DeferredItem<BackpackItem> BACKPACK = register("backpack", BackpackItem::new, TabGroup.MATERIAL);
    DeferredItem<WalletItem> WALLET = register("wallet", WalletItem::new, TabGroups.MATERIAL);
    DeferredItem<VoidTotemItem> VOID_TOTEM_ITEM = register("void_totem", VoidTotemItem::new, TabGroups.COMBAT);
    DeferredItem<ModDebugStickItem> MOD_DEBUG_STICK = register("mod_debug_stick", ModDebugStickItem::new, TabGroups.UTILITIES);
    DeferredItem<BuildersWand> BUILDERS_WAND = register("builders_wand", BuildersWand::new, TabGroups.UTILITIES);

    //region Weaponry
    DeferredItem<RainbowSwordItem> RAINBOW_SWORD = register("rainbow_sword", RainbowSwordItem::new, TabGroups.COMBAT);
    DeferredItem<DarkDagger> DARK_DAGGER = register("dark_dagger", DarkDagger::new, TabGroups.COMBAT);
    DeferredItem<TallinBow> TALLIN_BOW = register("tallin_bow", TallinBow::new, ModBowItem.BOW_GROUP);
    DeferredItem<DoubleSword> DIAMOND_DOUBLE_SWORD = register("diamond_double_sword", () -> new DoubleSword(Tiers.DIAMOND, MiscHelper.rarity(Rarity.UNCOMMON), 4, 150, 100), DoubleSword.DOUBLE_SWORD_GROUP);
    DeferredItem<ManaSteelSwordItem> MANA_STEEL_SWORD = register("mana_steel_sword", ManaSteelSwordItem::new, ModSwordItem.SWORD_GROUP);
    DeferredItem<LongBowItem> LONGBOW = register("longbow", LongBowItem::new, ModBowItem.BOW_GROUP);
    DeferredItem<TheStaffOfDestruction> THE_STAFF_DESTRUCTION = register("staff_of_destruction", TheStaffOfDestruction::new, SpellItem.SPELL_GROUP);
    DeferredItem<GhostlySword> GHOSTLY_SWORD = register("ghostly_sword", GhostlySword::new, TabGroups.COMBAT);
    DeferredItem<NecronSword> NECRON_SWORD = register("necron_sword", NecronSword::new, NecronSword.NECRON_GROUP);
    DeferredItem<NecronSword> HYPERION = register("hyperion", Hyperion::new, NecronSword.NECRON_GROUP);
    DeferredItem<Scylla> SCYLLA = register("scylla", Scylla::new, NecronSword.NECRON_GROUP);
    DeferredItem<Astraea> ASTREA = register("astrea", Astraea::new, NecronSword.NECRON_GROUP);
    DeferredItem<NecronSword> VALKYRIE = register("valkyrie", Valkyrie::new, NecronSword.NECRON_GROUP);
    DeferredItem<ShadowDagger> SHADOW_DAGGER = register("shadow_dagger", ShadowDagger::new, TabGroups.COMBAT);
    //endregion

    //region wands
    DeferredItem<AspectOfTheEndItem> AOTE = register("aspect_of_the_end", ()-> new AspectOfTheEndItem(50), SpellItem.SPELL_GROUP);
    DeferredItem<AspectOfTheEndItem> AOTV = register("aspect_of_the_void", () -> new AspectOfTheEndItem(80), SpellItem.SPELL_GROUP);
    DeferredItem<HeatedScythe> HEATED_SCYTHE = register("heated_scythe", HeatedScythe::new, IFireScytheItem.FIRE_SCYTHE_GROUP);
    DeferredItem<FieryScythe> FIERY_SCYTHE = register("fiery_scythe", FieryScythe::new, IFireScytheItem.FIRE_SCYTHE_GROUP);
    DeferredItem<BurningScythe> BURNING_SCYTHE = register("burning_scythe", BurningScythe::new, IFireScytheItem.FIRE_SCYTHE_GROUP);
    DeferredItem<InfernalScythe> INFERNAL_SCYTHE = register("infernal_scythe", InfernalScythe::new, IFireScytheItem.FIRE_SCYTHE_GROUP);
    DeferredItem<FireLance> FIRE_LANCE = register("fire_lance", FireLance::new, SpellItem.SPELL_GROUP);
    DeferredItem<VoidStaffItem> VOID_STAFF = register("void_staff", VoidStaffItem::new, SpellItem.SPELL_GROUP);
    //endregion

    //region material
    DeferredItem<SpellScrollItem> SPELL_SCROLL = register("spell_scroll", SpellScrollItem::new, null);
    DeferredItem<MaterialModItem> ORB_OF_CONSUMPTION = registerNonStackableMaterial("orb_of_consumption", Rarity.EPIC, TabGroups.MATERIAL);
    DeferredItem<MaterialModItem> NECRONS_HANDLE = registerNonStackableMaterial("necrons_handle", ExtraRarities.LEGENDARY, TabGroups.MATERIAL);
    DeferredItem<SouldbindNucleus> SOULBOUND_CORE = register("soulbound_nucleus", SouldbindNucleus::new, TabGroups.MATERIAL);
    DeferredItem<UnbreakingCore> UNBREAKING_CORE = register("unbreaking_core", UnbreakingCore::new, TabGroups.MATERIAL);
    DeferredItem<MaterialModItem> MANA_STEEL_INGOT = registerMaterial("mana_steel_ingot", Rarity.RARE, TabGroups.MATERIAL);
    DeferredItem<MaterialModItem> MS_UPPER_BlADE = registerNonStackableMaterial("upper_mana_steel_blade", Rarity.RARE, TabGroups.MATERIAL);
    DeferredItem<MaterialModItem> MS_DOWN_BlADE = registerNonStackableMaterial("lower_mana_steel_blade", Rarity.RARE, TabGroups.MATERIAL);
    DeferredItem<MaterialModItem> MS_HANDLE = registerNonStackableMaterial("mana_steel_sword_handle", Rarity.RARE, TabGroups.MATERIAL);
    DeferredItem<MaterialModItem> HEART_OF_THE_NETHER = registerNonStackableMaterial("heart_of_the_nether", ExtraRarities.MYTHIC, TabGroups.MATERIAL);
    DeferredItem<MaterialModItem> CRIMSON_STEEL_INGOT = registerMaterial("crimson_steel_ingot", Rarity.EPIC, TabGroups.CRIMSON_MATERIAL);
    DeferredItem<MaterialModItem> TERROR_STEEL_INGOT = registerMaterial("terror_steel_ingot", Rarity.EPIC, TabGroups.TERROR_MATERIAL);
    DeferredItem<MaterialModItem> CRIMSON_STEEL_DUST = registerMaterial("crimson_steel_dust", Rarity.EPIC, TabGroups.CRIMSON_MATERIAL);
    DeferredItem<MaterialModItem> CRIMSONITE_CLUSTER = registerMaterial("crimsonite_cluster", Rarity.UNCOMMON, TabGroups.CRIMSON_MATERIAL);
    DeferredItem<MaterialModItem> CRIMSONITE_DUST = registerMaterial("crimsonite_dust", Rarity.RARE, TabGroups.CRIMSON_MATERIAL);
    DeferredItem<MaterialModItem> CRIMSONIUM_DUST = registerMaterial("crimsonium_dust", Rarity.RARE, TabGroups.CRIMSON_MATERIAL);
    DeferredItem<MaterialModItem> CRIMSONIUM_INGOT = registerMaterial("crimsonium_ingot", Rarity.RARE, TabGroups.CRIMSON_MATERIAL);
    DeferredItem<MaterialModItem> RAW_CRIMSONIUM = registerMaterial("raw_crimsonium", Rarity.UNCOMMON, TabGroups.CRIMSON_MATERIAL);
    DeferredItem<MaterialModItem> RAW_CRIMSONIUM_DUST = registerMaterial("raw_crimsonium_dust", Rarity.RARE, TabGroups.CRIMSON_MATERIAL);
    DeferredItem<MaterialModItem> HARDENED_TEAR = registerMaterial("hardened_tear", Rarity.UNCOMMON, TabGroups.MATERIAL);
    DeferredItem<MaterialModItem> LAPIS_DUST = registerMaterial("lapis_dust", Rarity.COMMON, TabGroups.MATERIAL);
    HashMap<Element, DeferredItem<ElementalShard>> ELEMENTAL_SHARDS = ElementalShard.registerElementShards();
    DeferredItem<RainbowElementalShard> RAINBOW_ELEMENTAL_SHARD = register("elemental_shard_of_rainbow", RainbowElementalShard::new, ElementalShard.ELEMENTS_GROUP);
    DeferredItem<MaterialModItem> FROZEN_BLAZE_ROD = registerMaterial("frozen_blaze_rod", Rarity.RARE, TabGroups.MATERIAL);
    DeferredItem<MaterialModItem> SOUL_STEEL_ALLOY = registerMaterial("soul_steel_alloy", Rarity.RARE, TabGroups.MATERIAL);
    DeferredItem<MaterialModItem> SOUL_STEEL_NUGGET = registerMaterial("soul_steel_nugget", Rarity.UNCOMMON, TabGroups.MATERIAL);
    DeferredItem<MaterialModItem> SHADOW_CRYSTAL = registerMaterial("shadow_crystal", Rarity.UNCOMMON, TabGroups.MATERIAL);
    DeferredItem<DyedLeatherItem> DYED_LEATHER = register("dyed_leather",  DyedLeatherItem::new, TabGroups.MATERIAL);
    //endregion

    //region Hammer
    DeferredItem<HammerItem> STONE_HAMMER = register("stone_hammer", ()-> new HammerItem(MiscHelper.rarity(Rarity.COMMON), Tiers.STONE, 4), HammerItem.HAMMER_GROUP);
    DeferredItem<HammerItem> IRON_HAMMER = register("iron_hammer", ()-> new HammerItem(MiscHelper.rarity(Rarity.COMMON), Tiers.IRON, 5), HammerItem.HAMMER_GROUP);
    DeferredItem<HammerItem> DIAMOND_HAMMER = register("diamond_hammer", ()-> new HammerItem(MiscHelper.rarity(Rarity.UNCOMMON), Tiers.DIAMOND, 5), HammerItem.HAMMER_GROUP);
    DeferredItem<HammerItem> NETHERITE_HAMMER = register("netherite_hammer", ()-> new HammerItem(MiscHelper.rarity(Rarity.UNCOMMON), Tiers.NETHERITE, 6), HammerItem.HAMMER_GROUP);
    //endregion

    DeferredItem<LavaFishingRod> LAVA_FISHING_ROD_TEST = register("lava_fishing_rod", () -> new LavaFishingRod(Rarity.RARE), TabGroups.MATERIAL);
    DeferredItem<IronShield> IRON_SHIELD = register("iron_shield", IronShield::new, ModShieldItem.SHIELD_GROUP);
    DeferredItem<GoldenShield> GOLDEN_SHIELD = register("golden_shield", GoldenShield::new, ModShieldItem.SHIELD_GROUP);

    DeferredItem<QuiverItem> AMETHYST_QUIVER = register("amethyst_quiver", ()-> new QuiverItem(MiscHelper.rarity(Rarity.RARE), 16), QuiverItem.QUIVER_GROUP);
    DeferredItem<LavaFishItem> BLAZING_SALMON = register("blazing_salmon", ()-> new LavaFishItem(1, 1.2f, new MobEffectInstance(ModMobEffects.BLAZING, 100, 1)), LavaFishItem.LAVA_FISH_GROUP);
    DeferredItem<LavaFishItem> MAGMA_COD = register("magma_cod", ()-> new LavaFishItem(2, 1.1f, new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 150, 1)), LavaFishItem.LAVA_FISH_GROUP);

    //region armor

    DeferredItem<TravelersBoots> TRAVELERS_BOOTS = register("travelers_boots", TravelersBoots::new, TabGroups.COMBAT);
    DeferredItem<WizardHatItem> WIZARD_HAT = register("wizard_hat", WizardHatItem::new, ModBowItem.BOW_GROUP);

    Map<ArmorItem.Type, DeferredItem<EnderKnightArmorItem>> ENDER_KNIGHT_ARMOR = AbstractArmorItem.createRegistry(REGISTRY, "ender_knight", EnderKnightArmorItem::new, EnderKnightArmorItem.TAB);
    Map<ArmorItem.Type, DeferredItem<FrozenBlazeArmorItem>> FROZEN_BLAZE_ARMOR = AbstractArmorItem.createRegistry(REGISTRY, "frozen_blaze", FrozenBlazeArmorItem::new, FrozenBlazeArmorItem.TAB);
    Map<ArmorItem.Type, DeferredItem<ShadowAssassinArmorItem>> SHADOW_ASSASSIN_ARMOR = AbstractArmorItem.createRegistry(REGISTRY, "shadow_assassin", ShadowAssassinArmorItem::new, ShadowAssassinArmorItem.TAB);
    Map<ArmorItem.Type, DeferredItem<CrimsonArmorItem>> CRIMSON_ARMOR = AbstractArmorItem.createRegistry(REGISTRY, "crimson", CrimsonArmorItem::new, CrimsonArmorItem.TAB);
    Map<ArmorItem.Type, DeferredItem<SoulMageArmorItem>> SOUL_MAGE_ARMOR = AbstractArmorItem.createRegistry(REGISTRY, "soul_mage", SoulMageArmorItem::new, SoulMageArmorItem.TAB);
    Map<ArmorItem.Type, DeferredItem<WizardCloakArmorItem>> WIZARD_CLOAK_ARMOR = AbstractArmorItem.createRegistry(REGISTRY, "wizard_cloak", WizardCloakArmorItem::new, WizardCloakArmorItem.TAB);
    Map<ArmorItem.Type, DeferredItem<TerrorArmorItem>> TERROR_ARMOR = AbstractArmorItem.createRegistry(REGISTRY, "terror", TerrorArmorItem::new, TerrorArmorItem.TAB);

    //endregion

    //region spawn eggs
    DeferredItem<Item> FROZEN_BLAZE_SPAWN_EGG = register("frozen_blaze_spawn_egg", ()-> new DeferredSpawnEggItem(ModEntityTypes.FROZEN_BLAZE, -16711681, -16763956, new Item.Properties()), TabGroups.SPAWN_EGGS);
    DeferredItem<Item> DRAGON_SPAWN_EGG = register("dragon_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.DRAGON, 0xFF4F0000, 0xFFFF0000, new Item.Properties()), TabGroups.SPAWN_EGGS);
    //endregion

    DeferredItem<BucketItem> BUCKET_OF_MANA = REGISTRY.register("bucket_of_mana", ()-> new BucketItem(ModFluids.SOURCE_MANA_FLUID.get(), MiscHelper.rarity(Rarity.EPIC).stacksTo(1)));
    DeferredItem<GemstoneItem> GEMSTONE = REGISTRY.register("gemstone", GemstoneItem::new);

    HashMap<PrecursorRelicItem.BossType, DeferredItem<PrecursorRelicItem>> PRECURSOR_RELICTS = PrecursorRelicItem.makeRegistry();

    DeferredItem<CursedPearlItem> CURSED_PEARL = register("cursed_pearl", CursedPearlItem::new, TabGroups.COMBAT);
    DeferredItem<SplashPotionOfMilkItem> SPLASH_POTION_OF_MILK = register("splash_potion_of_milk", SplashPotionOfMilkItem::new, TabGroups.COMBAT);

    //region tech
    DeferredItem<TechWandItem> TECH_WAND = register("tech_wand", TechWandItem::new, TabGroups.TECHNOLOGY);

    DeferredItem<ParallelProcessingUpgradeItem> PARALLEL_PROCESSING_UPGRADE = register("upgrade/parallel_processing", ParallelProcessingUpgradeItem::new, TabGroups.TECHNOLOGY);
    DeferredItem<SpeedUpgradeItem> SPEED_UPGRADE = register("upgrade/speed", SpeedUpgradeItem::new, TabGroups.TECHNOLOGY);
    //endregion
}