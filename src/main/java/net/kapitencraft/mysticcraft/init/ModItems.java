package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.gui.GUISlotBlockItem;
import net.kapitencraft.mysticcraft.item.WizardHatItem;
import net.kapitencraft.mysticcraft.item.armor.EnderKnightArmorItem;
import net.kapitencraft.mysticcraft.item.armor.FrozenBlazeArmorItem;
import net.kapitencraft.mysticcraft.item.armor.ShadowAssassinArmorItem;
import net.kapitencraft.mysticcraft.item.armor.SoulMageArmorItem;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneType;
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
    public static final RegistryObject<Item> LONGBOW = REGISTRY.register("longbow", LongBowItem::new);
    public static final RegistryObject<Item> WIZARD_HAT = REGISTRY.register("wizard_hat", WizardHatItem::new);
    public static final RegistryObject<Item> THE_STAFF_DESTRUCTION = REGISTRY.register("staff_of_destruction", TheStaffOfDestruction::new);
    public static final RegistryObject<Item> HYPERION = REGISTRY.register("hyperion", Hyperion::new);
    public static final RegistryObject<Item> SCYLLA = REGISTRY.register("scylla", Scylla::new);
    public static final RegistryObject<Item> ASTREA = REGISTRY.register("astrea", Astraea::new);
    public static final RegistryObject<Item> VALKYRIE = REGISTRY.register("valkyrie", Valkyrie::new);
    public static final RegistryObject<Item> AOTE = REGISTRY.register("aspect_of_the_end", AspectOfTheEndItem::new);
    public static final RegistryObject<Item> HEART_OF_THE_NETHER = REGISTRY.register("heart_of_the_nether", ()-> new Item(new Item.Properties().rarity(FormattingCodes.MYTHIC).stacksTo(1)));
    public static final RegistryObject<Item> SPELL_SHARD = REGISTRY.register("spell_shard", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> HEATED_SCYTHE = REGISTRY.register("heated_scythe", HeatedScythe::new);
    public static final RegistryObject<Item> FIERY_SCYTHE = REGISTRY.register("fiery_scythe", FieryScythe::new);
    public static final RegistryObject<Item> BURNING_SCYTHE = REGISTRY.register("burning_scythe", BurningScythe::new);
    public static final RegistryObject<Item> INFERNAL_SCYTHE = REGISTRY.register("infernal_scythe", InfernalScythe::new);
    public static final RegistryObject<Item> FIRE_LANCE = REGISTRY.register("fire_lance", FireLance::new);
    public static final RegistryObject<Item> STAFF_OF_THE_WILD = REGISTRY.register("staff_of_the_wild", StaffOfTheWild::new);
    public static final RegistryObject<Item> IMPLOSION_SCROLL = REGISTRY.register("implosion_scroll", ()-> new SpellScrollItem(Spells.IMPLOSION.getSpell()));
    public static final RegistryObject<Item> MANA_STEEL_INGOT = REGISTRY.register("mana_steel_ingot", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> UPPER_BlADE_MS = REGISTRY.register("upper_mana_steel_sword_part", () -> new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
    public static final RegistryObject<Item> DOWN_BlADE_MS = REGISTRY.register("lower_mana_steel_sword_part", () -> new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
    public static final RegistryObject<Item> MS_HANDLE = REGISTRY.register("mana_steel_sword_handle", () -> new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1)));
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
    public static final HashMap<EquipmentSlot, RegistryObject<Item>> ENDER_KNIGHT_ARMOR = EnderKnightArmorItem.createRegistry(REGISTRY, "ender_knight");
    public static final HashMap<EquipmentSlot, RegistryObject<Item>> FROZEN_BLAZE_ARMOR = FrozenBlazeArmorItem.createRegistry(REGISTRY, "frozen_blaze");
    public static final HashMap<EquipmentSlot, RegistryObject<Item>> SHADOW_ASSASSIN_ARMOR = ShadowAssassinArmorItem.createRegistry(REGISTRY, "shadow_assassin");
    public static final HashMap<EquipmentSlot, RegistryObject<Item>> SOUL_MAGE_ARMOR = SoulMageArmorItem.createRegistry(REGISTRY, "soul_mage");
    public static final RegistryObject<Item> FROZEN_BLAZE_SPAWN_EGG = REGISTRY.register("frozen_blaze_spawn_egg", ()-> new ForgeSpawnEggItem(ModEntityTypes.FROZEN_BLAZE, -16711681, -16763956, new Item.Properties()));
    public static final RegistryObject<Item> MISSING_GEMSTONE_SLOT = REGISTRY.register("missing_slot", ()-> new GUISlotBlockItem().putTooltip(List.of(Component.literal("This Gemstone Applicable has no gemstone at that slot").withStyle(ChatFormatting.RED))));
    public static final RegistryObject<Item> EMPTY_APPLICABLE_SLOT = REGISTRY.register("empty_applicable", ()-> new GUISlotBlockItem().putTooltip(List.of(Component.literal("There is no Gemstone Applicable in it's slot").withStyle(ChatFormatting.RED))));
    public static final RegistryObject<BucketItem> BUCKET_OF_MANA = REGISTRY.register("bucket_of_mana", ()-> new BucketItem(ModFluids.SOURCE_MANA_FLUID, new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final HashMap<GemstoneType, HashMap<GemstoneType.Rarity, RegistryObject<GemstoneItem>>> GEMSTONES = GemstoneType.createRegistry(REGISTRY);
}