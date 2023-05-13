package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.gui.GUISlotBlockItem;
import net.kapitencraft.mysticcraft.item.*;
import net.kapitencraft.mysticcraft.item.armor.*;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.item.shield.GoldenShield;
import net.kapitencraft.mysticcraft.item.shield.IronShield;
import net.kapitencraft.mysticcraft.item.spells.*;
import net.kapitencraft.mysticcraft.item.spells.necron_sword.Astraea;
import net.kapitencraft.mysticcraft.item.spells.necron_sword.Hyperion;
import net.kapitencraft.mysticcraft.item.spells.necron_sword.Scylla;
import net.kapitencraft.mysticcraft.item.spells.necron_sword.Valkyrie;
import net.kapitencraft.mysticcraft.item.weapon.melee.cleaver.CleaverItem;
import net.kapitencraft.mysticcraft.item.weapon.melee.sword.ManaSteelSwordItem;
import net.kapitencraft.mysticcraft.item.weapon.ranged.bow.LongBowItem;
import net.kapitencraft.mysticcraft.item.weapon.ranged.bow.TallinBow;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.List;

public abstract class ModItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MysticcraftMod.MOD_ID);
    public static final RegistryObject<Item> MOD_DEBUG_STICK = REGISTRY.register("mod_debug_stick", ModDebugStickItem::new);
    public static final RegistryObject<Item> LONGBOW = REGISTRY.register("longbow", LongBowItem::new);
    public static final RegistryObject<Item> WIZARD_HAT = REGISTRY.register("wizard_hat", WizardHatItem::new);
    public static final RegistryObject<Item> THE_STAFF_DESTRUCTION = REGISTRY.register("staff_of_destruction", TheStaffOfDestruction::new);
    public static final RegistryObject<Item> HYPERION = REGISTRY.register("hyperion", Hyperion::new);
    public static final RegistryObject<Item> SCYLLA = REGISTRY.register("scylla", Scylla::new);
    public static final RegistryObject<Item> ASTREA = REGISTRY.register("astrea", Astraea::new);
    public static final RegistryObject<Item> VALKYRIE = REGISTRY.register("valkyrie", Valkyrie::new);
    public static final RegistryObject<Item> LAVA_FISHING_ROD_TEST = REGISTRY.register("lava_fishing_rod", ()-> new LavaFishingRod(Rarity.RARE));
    public static final RegistryObject<Item> AOTE = REGISTRY.register("aspect_of_the_end", ()-> new AspectOfTheEndItem(50));
    public static final RegistryObject<Item> AOTV = REGISTRY.register("aspect_of_the_void", AspectOfTheVoidItem::new);
    public static final RegistryObject<Item> HEATED_SCYTHE = REGISTRY.register("heated_scythe", HeatedScythe::new);
    public static final RegistryObject<Item> FIERY_SCYTHE = REGISTRY.register("fiery_scythe", FieryScythe::new);
    public static final RegistryObject<Item> BURNING_SCYTHE = REGISTRY.register("burning_scythe", BurningScythe::new);
    public static final RegistryObject<Item> INFERNAL_SCYTHE = REGISTRY.register("infernal_scythe", InfernalScythe::new);
    public static final RegistryObject<Item> FIRE_LANCE = REGISTRY.register("fire_lance", FireLance::new);
    public static final RegistryObject<Item> STAFF_OF_THE_WILD = REGISTRY.register("staff_of_the_wild", StaffOfTheWildItem::new);
    public static final HashMap<Spells, RegistryObject<Item>> SCROLLS = Spells.registerAll(REGISTRY);
    public static final RegistryObject<Item> MANA_STEEL_INGOT = REGISTRY.register("mana_steel_ingot", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> UPPER_BlADE_MS = REGISTRY.register("upper_mana_steel_sword_part", () -> new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
    public static final RegistryObject<Item> DOWN_BlADE_MS = REGISTRY.register("lower_mana_steel_sword_part", () -> new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
    public static final RegistryObject<Item> MS_HANDLE = REGISTRY.register("mana_steel_sword_handle", () -> new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
    public static final RegistryObject<Item> HEART_OF_THE_NETHER = REGISTRY.register("heart_of_the_nether", ()-> new Item(new Item.Properties().rarity(FormattingCodes.MYTHIC).stacksTo(1)));
    public static final RegistryObject<Item> CRIMSON_STEEL_INGOT = REGISTRY.register("crimson_steel_ingot", ()-> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> CRIMSON_STEEL_DUST = REGISTRY.register("crimson_steel_dust", ()-> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> CRIMSONITE_CLUSTER = REGISTRY.register("crimsonite_cluster", ()-> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> CRIMSONITE_DUST = REGISTRY.register("crimsonite_dust", ()-> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CRIMSONIUM_DUST = REGISTRY.register("crimsonium_dust", ()-> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CRIMSONIUM_INGOT = REGISTRY.register("crimsonium_ingot", ()-> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> RAW_CRIMSONIUM = REGISTRY.register("raw_crimsonium", ()-> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> RAW_CRIMSONIUM_DUST = REGISTRY.register("raw_crimsonium_dust", ()-> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> STONE_HAMMER = REGISTRY.register("stone_hammer", ()-> new HammerItem(new Item.Properties().rarity(Rarity.COMMON), 354));
    public static final RegistryObject<Item> IRON_HAMMER = REGISTRY.register("iron_hammer", ()-> new HammerItem(new Item.Properties().rarity(Rarity.UNCOMMON), 530));
    public static final RegistryObject<Item> DIAMOND_HAMMER = REGISTRY.register("diamond_hammer", ()-> new HammerItem(new Item.Properties().rarity(Rarity.RARE), 846));
    public static final RegistryObject<Item> SPELL_SHARD = REGISTRY.register("spell_shard", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> FROZEN_BLAZE_ROD = REGISTRY.register("frozen_blaze_rod", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> TALLIN_BOW = REGISTRY.register("tallin_bow", TallinBow::new);
    public static final RegistryObject<Item> MANA_STEEL_SWORD = REGISTRY.register("mana_steel_sword", ManaSteelSwordItem::new);
    public static final RegistryObject<Item> DIAMOND_CLEAVER = REGISTRY.register("diamond_cleaver", ()-> new CleaverItem(Tiers.DIAMOND, 5, new Item.Properties().rarity(Rarity.RARE)) {
        @Override
        protected double getArmorShredderBonus() {
            return 2;
        }

        @Override
        public double getStrenght() {
            return 70;
        }

        @Override
        public double getCritDamage() {
            return 20;
        }
    });
    public static final RegistryObject<Item> IRON_SHIELD = REGISTRY.register("iron_shield", IronShield::new);
    public static final RegistryObject<Item> GOLDEN_SHIELD = REGISTRY.register("golden_shield", GoldenShield::new);
    public static final RegistryObject<Item> DYED_LEATHER = REGISTRY.register("dyed_leather",  DyedLeatherItem::new);

    public static final RegistryObject<Item> BLAZING_SALMON = REGISTRY.register("blazing_salmon", ()-> new LavaFishItem(1, 1.2f, new MobEffectInstance(ModMobEffects.IGNITION.get(), 100, 1)));
    public static final RegistryObject<Item> MAGMA_COD = REGISTRY.register("magma_cod", ()-> new LavaFishItem(2, 1.1f, new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 150, 1)));

    public static final HashMap<EquipmentSlot, RegistryObject<Item>> ENDER_KNIGHT_ARMOR = ModArmorItem.createRegistry(REGISTRY, "ender_knight", EnderKnightArmorItem::new);
    public static final HashMap<EquipmentSlot, RegistryObject<Item>> FROZEN_BLAZE_ARMOR = ModArmorItem.createRegistry(REGISTRY, "frozen_blaze", FrozenBlazeArmorItem::new);
    public static final HashMap<EquipmentSlot, RegistryObject<Item>> SHADOW_ASSASSIN_ARMOR = ModArmorItem.createRegistry(REGISTRY, "shadow_assassin", ShadowAssassinArmorItem::new);
    public static final HashMap<EquipmentSlot, RegistryObject<Item>> CRIMSON_ARMOR = ModArmorItem.createRegistry(REGISTRY, "crimson", CrimsonArmorItem::new);
    public static final HashMap<EquipmentSlot, RegistryObject<Item>> SOUL_MAGE_ARMOR = ModArmorItem.createRegistry(REGISTRY, "soul_mage", SoulMageArmorItem::new);

    public static final RegistryObject<Item> FROZEN_BLAZE_SPAWN_EGG = REGISTRY.register("frozen_blaze_spawn_egg", ()-> new ForgeSpawnEggItem(ModEntityTypes.FROZEN_BLAZE, -16711681, -16763956, new Item.Properties()));

    public static final RegistryObject<Item> MISSING_GEMSTONE_SLOT = REGISTRY.register("missing_slot", ()-> new GUISlotBlockItem().putTooltip(List.of(Component.literal("This Gemstone Applicable has no gemstone at that slot").withStyle(ChatFormatting.RED))));
    public static final RegistryObject<Item> EMPTY_APPLICABLE_SLOT = REGISTRY.register("empty_applicable", ()-> new GUISlotBlockItem().putTooltip(List.of(Component.literal("There is no Gemstone Applicable in it's slot").withStyle(ChatFormatting.RED))));
    public static final RegistryObject<BucketItem> BUCKET_OF_MANA = REGISTRY.register("bucket_of_mana", ()-> new BucketItem(ModFluids.SOURCE_MANA_FLUID, new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final HashMap<GemstoneType, HashMap<GemstoneType.Rarity, RegistryObject<GemstoneItem>>> GEMSTONES = GemstoneType.createRegistry(REGISTRY);
}