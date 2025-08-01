package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.kap_lib.util.ExtraRarities;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.gui.GUISlotBlockItem;
import net.kapitencraft.mysticcraft.item.combat.armor.*;
import net.kapitencraft.mysticcraft.item.combat.shield.GoldenShield;
import net.kapitencraft.mysticcraft.item.combat.shield.IronShield;
import net.kapitencraft.mysticcraft.item.combat.shield.ModShieldItem;
import net.kapitencraft.mysticcraft.item.combat.spells.*;
import net.kapitencraft.mysticcraft.item.combat.spells.necron_sword.Astraea;
import net.kapitencraft.mysticcraft.item.combat.spells.necron_sword.NecronSword;
import net.kapitencraft.mysticcraft.item.combat.spells.necron_sword.Scylla;
import net.kapitencraft.mysticcraft.item.combat.totems.VoidTotemItem;
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
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.tools.HammerItem;
import net.kapitencraft.mysticcraft.item.tools.fishing_rods.LavaFishingRod;
import net.kapitencraft.mysticcraft.spell.Element;
import net.kapitencraft.mysticcraft.tech.item.TechWandItem;
import net.kapitencraft.mysticcraft.tech.item.upgrade.ParallelProcessingUpgradeItem;
import net.kapitencraft.mysticcraft.tech.item.upgrade.SpeedUpgradeItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
@SuppressWarnings("unused")
public interface ModItems {

    static <T extends Item> RegistryObject<T> register(String name, final Supplier<T> supplier, TabGroup group) {
        RegistryObject<T> registryObject = REGISTRY.register(name, supplier);
        if (group != null) group.add(registryObject);
        return registryObject;
    }

    static RegistryObject<MaterialModItem> registerMaterial(String name, Rarity rarity, TabGroup group) {
        return register(name, ()-> new MaterialModItem(rarity, true), group);
    }

    static <T extends Item> RegistryObject<MaterialModItem> registerNonStackableMaterial(String name, Rarity rarity, TabGroup group) {
        return register(name, ()-> new MaterialModItem(rarity, false), group);
    }

    static <T, V extends Item> HashMap<T, RegistryObject<V>> createRegistry(Function<T, V> provider, Function<T, String> nameProvider, List<T> values, TabGroup group) {
        HashMap<T, RegistryObject<V>> map = new HashMap<>();
        for (T t : values) {
            map.put(t, register(nameProvider.apply(t), ()-> provider.apply(t), group));
        }
        return map;
    }


    static Collection<RegistryObject<Item>> getEntries() {
        return REGISTRY.getEntries();
    }

    DeferredRegister<Item> REGISTRY = MysticcraftMod.registry(ForgeRegistries.ITEMS);


    //RegistryObject<BackpackItem> BACKPACK = register("backpack", BackpackItem::new, TabGroup.MATERIAL);
    RegistryObject<WalletItem> WALLET = register("wallet", WalletItem::new, TabGroup.MATERIAL);
    RegistryObject<EssenceItem> ESSENCE = register("essence", EssenceItem::new, null);
    RegistryObject<VoidTotemItem> VOID_TOTEM_ITEM = register("void_totem", VoidTotemItem::new, TabGroup.COMBAT);
    RegistryObject<ModDebugStickItem> MOD_DEBUG_STICK = register("mod_debug_stick", ModDebugStickItem::new, TabGroup.UTILITIES);
    RegistryObject<BuildersWand> BUILDERS_WAND = register("builders_wand", BuildersWand::new, TabGroup.UTILITIES);

    //region Weaponry
    RegistryObject<LongBowItem> LONGBOW = register("longbow", LongBowItem::new, ModBowItem.BOW_GROUP);
    RegistryObject<WizardHatItem> WIZARD_HAT = register("wizard_hat", WizardHatItem::new, ModBowItem.BOW_GROUP);
    RegistryObject<TheStaffOfDestruction> THE_STAFF_DESTRUCTION = register("staff_of_destruction", TheStaffOfDestruction::new, SpellItem.SPELL_GROUP);
    RegistryObject<GhostlySword> GHOSTLY_SWORD = register("ghostly_sword", GhostlySword::new, TabGroup.COMBAT);
    RegistryObject<NecronSword> NECRON_SWORD = register("necron_sword", ()-> new NecronSword(NecronSword.BASE_DAMAGE, NecronSword.BASE_INTEL, NecronSword.BASE_FEROCITY, NecronSword.BASE_STRENGHT), NecronSword.NECRON_GROUP);
    RegistryObject<NecronSword> HYPERION = register("hyperion", () -> new NecronSword(NecronSword.BASE_DAMAGE, 350, NecronSword.BASE_FEROCITY, NecronSword.BASE_STRENGHT), NecronSword.NECRON_GROUP);
    RegistryObject<Scylla> SCYLLA = register("scylla", Scylla::new, NecronSword.NECRON_GROUP);
    RegistryObject<Astraea> ASTREA = register("astrea", Astraea::new, NecronSword.NECRON_GROUP);
    RegistryObject<NecronSword> VALKYRIE = register("valkyrie", () -> new NecronSword(NecronSword.REFINED_BASE_DAMAGE, 60, 60, 145), NecronSword.NECRON_GROUP);
    RegistryObject<ShadowDagger> SHADOW_DAGGER = register("shadow_dagger", ShadowDagger::new, TabGroup.COMBAT);
    //endregion

    //region wands
    RegistryObject<AspectOfTheEndItem> AOTE = register("aspect_of_the_end", ()-> new AspectOfTheEndItem(50), SpellItem.SPELL_GROUP);
    RegistryObject<AspectOfTheEndItem> AOTV = register("aspect_of_the_void", () -> new AspectOfTheEndItem(80), SpellItem.SPELL_GROUP);
    RegistryObject<HeatedScythe> HEATED_SCYTHE = register("heated_scythe", HeatedScythe::new, IFireScytheItem.FIRE_SCYTHE_GROUP);
    RegistryObject<FieryScythe> FIERY_SCYTHE = register("fiery_scythe", FieryScythe::new, IFireScytheItem.FIRE_SCYTHE_GROUP);
    RegistryObject<BurningScythe> BURNING_SCYTHE = register("burning_scythe", BurningScythe::new, IFireScytheItem.FIRE_SCYTHE_GROUP);
    RegistryObject<InfernalScythe> INFERNAL_SCYTHE = register("infernal_scythe", InfernalScythe::new, IFireScytheItem.FIRE_SCYTHE_GROUP);
    RegistryObject<FireLance> FIRE_LANCE = register("fire_lance", FireLance::new, SpellItem.SPELL_GROUP);
    RegistryObject<VoidStaffItem> VOID_STAFF = register("void_staff", VoidStaffItem::new, SpellItem.SPELL_GROUP);
    //endregion

    RegistryObject<LavaFishingRod> LAVA_FISHING_ROD_TEST = register("lava_fishing_rod", () -> new LavaFishingRod(Rarity.RARE), TabGroup.MATERIAL);
    RegistryObject<SpellScrollItem> SPELL_SCROLL = register("spell_scroll", SpellScrollItem::new, null);
    RegistryObject<MaterialModItem> ORB_OF_CONSUMPTION = registerNonStackableMaterial("orb_of_consumption", Rarity.EPIC, TabGroup.MATERIAL);
    RegistryObject<MaterialModItem> NECRONS_HANDLE = registerNonStackableMaterial("necrons_handle", ExtraRarities.LEGENDARY, TabGroup.MATERIAL);
    RegistryObject<SouldbindNucleus> SOULBOUND_CORE = register("soulbound_nucleus", SouldbindNucleus::new, TabGroup.MATERIAL);
    RegistryObject<UnbreakingCore> UNBREAKING_CORE = register("unbreaking_core", UnbreakingCore::new, TabGroup.MATERIAL);
    RegistryObject<MaterialModItem> MANA_STEEL_INGOT = registerMaterial("mana_steel_ingot", Rarity.RARE, TabGroup.MATERIAL);
    RegistryObject<MaterialModItem> MS_UPPER_BlADE = registerNonStackableMaterial("upper_mana_steel_blade", Rarity.RARE, TabGroup.MATERIAL);
    RegistryObject<MaterialModItem> MS_DOWN_BlADE = registerNonStackableMaterial("lower_mana_steel_blade", Rarity.RARE, TabGroup.MATERIAL);
    RegistryObject<MaterialModItem> MS_HANDLE = registerNonStackableMaterial("mana_steel_sword_handle", Rarity.RARE, TabGroup.MATERIAL);
    RegistryObject<MaterialModItem> HEART_OF_THE_NETHER = registerNonStackableMaterial("heart_of_the_nether", ExtraRarities.MYTHIC, TabGroup.MATERIAL);
    RegistryObject<MaterialModItem> CRIMSON_STEEL_INGOT = registerMaterial("crimson_steel_ingot", Rarity.EPIC, TabGroup.CRIMSON_MATERIAL);
    RegistryObject<MaterialModItem> TERROR_STEEL_INGOT = registerMaterial("terror_steel_ingot", Rarity.EPIC, TabGroup.TERROR_MATERIAL);
    RegistryObject<MaterialModItem> CRIMSON_STEEL_DUST = registerMaterial("crimson_steel_dust", Rarity.EPIC, TabGroup.CRIMSON_MATERIAL);
    RegistryObject<MaterialModItem> CRIMSONITE_CLUSTER = registerMaterial("crimsonite_cluster", Rarity.UNCOMMON, TabGroup.CRIMSON_MATERIAL);
    RegistryObject<MaterialModItem> CRIMSONITE_DUST = registerMaterial("crimsonite_dust", Rarity.RARE, TabGroup.CRIMSON_MATERIAL);
    RegistryObject<MaterialModItem> CRIMSONIUM_DUST = registerMaterial("crimsonium_dust", Rarity.RARE, TabGroup.CRIMSON_MATERIAL);
    RegistryObject<MaterialModItem> CRIMSONIUM_INGOT = registerMaterial("crimsonium_ingot", Rarity.RARE, TabGroup.CRIMSON_MATERIAL);
    RegistryObject<MaterialModItem> RAW_CRIMSONIUM = registerMaterial("raw_crimsonium", Rarity.UNCOMMON, TabGroup.CRIMSON_MATERIAL);
    RegistryObject<MaterialModItem> RAW_CRIMSONIUM_DUST = registerMaterial("raw_crimsonium_dust", Rarity.RARE, TabGroup.CRIMSON_MATERIAL);
    RegistryObject<MaterialModItem> HARDENED_TEAR = registerMaterial("hardened_tear", Rarity.UNCOMMON, TabGroup.MATERIAL);

    //region Hammer
    RegistryObject<HammerItem> STONE_HAMMER = register("stone_hammer", ()-> new HammerItem(MiscHelper.rarity(Rarity.COMMON), Tiers.STONE, 10), HammerItem.HAMMER_GROUP);
    RegistryObject<HammerItem> IRON_HAMMER = register("iron_hammer", ()-> new HammerItem(MiscHelper.rarity(Rarity.COMMON), Tiers.IRON, 11), HammerItem.HAMMER_GROUP);
    RegistryObject<HammerItem> DIAMOND_HAMMER = register("diamond_hammer", ()-> new HammerItem(MiscHelper.rarity(Rarity.UNCOMMON), Tiers.DIAMOND, 11), HammerItem.HAMMER_GROUP);
    RegistryObject<HammerItem> NETHERITE_HAMMER = register("netherite_hammer", ()-> new HammerItem(MiscHelper.rarity(Rarity.UNCOMMON), Tiers.NETHERITE, 13), HammerItem.HAMMER_GROUP);
    //endregion

    HashMap<Element, RegistryObject<ElementalShard>> ELEMENTAL_SHARDS = ElementalShard.registerElementShards();
    RegistryObject<RainbowElementalShard> RAINBOW_ELEMENTAL_SHARD = register("elemental_shard_of_rainbow", RainbowElementalShard::new, ElementalShard.ELEMENTS_GROUP);
    RegistryObject<MaterialModItem> FROZEN_BLAZE_ROD = registerMaterial("frozen_blaze_rod", Rarity.RARE, TabGroup.MATERIAL);
    RegistryObject<TallinBow> TALLIN_BOW = register("tallin_bow", TallinBow::new, ModBowItem.BOW_GROUP);
    RegistryObject<DoubleSword> DIAMOND_DOUBLE_SWORD = register("diamond_double_sword", () -> new DoubleSword(Tiers.DIAMOND, MiscHelper.rarity(Rarity.UNCOMMON)), DoubleSword.DOUBLE_SWORD_GROUP);
    RegistryObject<ManaSteelSwordItem> MANA_STEEL_SWORD = register("mana_steel_sword", ManaSteelSwordItem::new, ModSwordItem.SWORD_GROUP);
    RegistryObject<IronShield> IRON_SHIELD = register("iron_shield", IronShield::new, ModShieldItem.SHIELD_GROUP);
    RegistryObject<GoldenShield> GOLDEN_SHIELD = register("golden_shield", GoldenShield::new, ModShieldItem.SHIELD_GROUP);
    RegistryObject<DyedLeatherItem> DYED_LEATHER = register("dyed_leather",  DyedLeatherItem::new, TabGroup.MATERIAL);

    RegistryObject<QuiverItem> AMETHYST_QUIVER = register("amethyst_quiver", ()-> new QuiverItem(MiscHelper.rarity(Rarity.RARE), 16), QuiverItem.QUIVER_GROUP);
    RegistryObject<LavaFishItem> BLAZING_SALMON = register("blazing_salmon", ()-> new LavaFishItem(1, 1.2f, new MobEffectInstance(ModMobEffects.BLAZING.get(), 100, 1)), LavaFishItem.LAVA_FISH_GROUP);
    RegistryObject<LavaFishItem> MAGMA_COD = register("magma_cod", ()-> new LavaFishItem(2, 1.1f, new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 150, 1)), LavaFishItem.LAVA_FISH_GROUP);

    RegistryObject<MaterialModItem> SOUL_STEEL_ALLOY = registerMaterial("soul_steel_alloy", Rarity.RARE, TabGroup.MATERIAL);
    RegistryObject<MaterialModItem> SOUL_STEEL_NUGGET = registerMaterial("soul_steel_nugget", Rarity.UNCOMMON, TabGroup.MATERIAL);
    RegistryObject<MaterialModItem> SHADOW_CRYSTAL = registerMaterial("shadow_crystal", Rarity.UNCOMMON, TabGroup.MATERIAL);
    RegistryObject<DarkDagger> DARK_DAGGER = register("dark_dagger", DarkDagger::new, TabGroup.COMBAT);
    Map<ArmorItem.Type, RegistryObject<EnderKnightArmorItem>> ENDER_KNIGHT_ARMOR = AbstractArmorItem.createRegistry(REGISTRY, "ender_knight", EnderKnightArmorItem::new);
    Map<ArmorItem.Type, RegistryObject<FrozenBlazeArmorItem>> FROZEN_BLAZE_ARMOR = AbstractArmorItem.createRegistry(REGISTRY, "frozen_blaze", FrozenBlazeArmorItem::new);
    Map<ArmorItem.Type, RegistryObject<ShadowAssassinArmorItem>> SHADOW_ASSASSIN_ARMOR = AbstractArmorItem.createRegistry(REGISTRY, "shadow_assassin", ShadowAssassinArmorItem::new);
    Map<ArmorItem.Type, RegistryObject<CrimsonArmorItem>> CRIMSON_ARMOR = AbstractArmorItem.createRegistry(REGISTRY, "crimson", CrimsonArmorItem::new);
    Map<ArmorItem.Type, RegistryObject<SoulMageArmorItem>> SOUL_MAGE_ARMOR = AbstractArmorItem.createRegistry(REGISTRY, "soul_mage", SoulMageArmorItem::new);
    Map<ArmorItem.Type, RegistryObject<WizardCloakArmorItem>> WIZARD_CLOAK_ARMOR = AbstractArmorItem.createRegistry(REGISTRY, "wizard_cloak", WizardCloakArmorItem::new);
    Map<ArmorItem.Type, RegistryObject<TerrorArmorItem>> TERROR_ARMOR = AbstractArmorItem.createRegistry(REGISTRY, "terror", TerrorArmorItem::new);

    RegistryObject<Item> FROZEN_BLAZE_SPAWN_EGG = REGISTRY.register("frozen_blaze_spawn_egg", ()-> new ForgeSpawnEggItem(ModEntityTypes.FROZEN_BLAZE, -16711681, -16763956, new Item.Properties()));

    RegistryObject<GUISlotBlockItem> MISSING_GEMSTONE_SLOT = register("missing_slot", ()-> new GUISlotBlockItem().putTooltip(List.of(Component.literal("This Gemstone Applicable has no gemstone at that slot").withStyle(ChatFormatting.RED))), null);
    RegistryObject<GUISlotBlockItem> EMPTY_APPLICABLE_SLOT = register("empty_applicable", ()-> new GUISlotBlockItem().putTooltip(List.of(Component.literal("There is no Gemstone Applicable in it's slot").withStyle(ChatFormatting.RED))), null);
    RegistryObject<BucketItem> BUCKET_OF_MANA = REGISTRY.register("bucket_of_mana", ()-> new BucketItem(ModFluids.SOURCE_MANA_FLUID, MiscHelper.rarity(Rarity.EPIC).stacksTo(1)));
    RegistryObject<GemstoneItem> GEMSTONE = REGISTRY.register("gemstone", GemstoneItem::new);

    //HashMap<GuildUpgrades, RegistryObject<GuildUpgradeItem>> GUILD_UPGRADES = GuildUpgrades.createRegistry();
    HashMap<PrecursorRelicItem.BossType, RegistryObject<PrecursorRelicItem>> PRECURSOR_RELICTS = PrecursorRelicItem.makeRegistry();

    RegistryObject<CursedPearlItem> CURSED_PEARL = register("cursed_pearl", CursedPearlItem::new, TabGroup.COMBAT);

    //region tech

    RegistryObject<TechWandItem> TECH_WAND = register("tech_wand", TechWandItem::new, TabGroup.TECHNOLOGY);

    RegistryObject<ParallelProcessingUpgradeItem> PARALLEL_PROCESSING_UPGRADE = register("upgrade/parallel_processing", ParallelProcessingUpgradeItem::new, TabGroup.TECHNOLOGY);
    RegistryObject<SpeedUpgradeItem> SPEED_UPGRADE = register("upgrade/speed", SpeedUpgradeItem::new, TabGroup.TECHNOLOGY);
    //endregion
}