package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.*;
import net.kapitencraft.mysticcraft.enchantments.ReplenishEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModEnchantments {
    public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MysticcraftMod.MOD_ID);

    public static final RegistryObject<Enchantment> ELVISH_MASTERY = REGISTRY.register("elvish_mastery", ElvishMasteryEnchantment::new);
    public static final RegistryObject<Enchantment> FAST_ARROWS = REGISTRY.register("fast_arrows", FastArrowsEnchantment::new);
    public static final RegistryObject<Enchantment> GIANT_KILLER = REGISTRY.register("giant_killer", GiantKillerEnchantment::new);
    public static final RegistryObject<Enchantment> AIM = REGISTRY.register("aim", AimEnchantment::new);
    public static final RegistryObject<Enchantment> SNIPE = REGISTRY.register("snipe", SnipeEnchantment::new);
    public static final RegistryObject<Enchantment> REJUVENATE = REGISTRY.register("rejuvenate", RejuvenateEnchantment::new);
    public static final RegistryObject<Enchantment> GROWTH = REGISTRY.register("growth", GrowthEnchantment::new);
    public static final RegistryObject<Enchantment> FIRM_STAND = REGISTRY.register("firm_stand", FirmStandEnchantment::new);
    public static final RegistryObject<Enchantment> PROTECTIVE_COVER = REGISTRY.register("protective_cover", ProtectiveCoverEnchantment::new);
    public static final RegistryObject<Enchantment> NECROTIC_TOUCH = REGISTRY.register("necrotic_touch", NecroticTouchEnchantment::new);
    public static final RegistryObject<Enchantment> ULTIMATE_WISE = REGISTRY.register("ultimate_wise", UltimateWiseEnchantment::new);
    public static final RegistryObject<Enchantment> LUMBERJACK = REGISTRY.register("lumberjack", LumberjackEnchantment::new);
    public static final RegistryObject<Enchantment> VEIN_MINER = REGISTRY.register("vein_miner", VeinMinerEnchantment::new);
    public static final RegistryObject<Enchantment> EFFICIENT_JEWELLING = REGISTRY.register("efficient_jewelling", EfficientJewellingEnchantment::new);
    public static final RegistryObject<Enchantment> POISONOUS_BLADE = REGISTRY.register("poisonous_blade", PoisonousBladeEnchantment::new);
    public static final RegistryObject<Enchantment> GLACIAL_BLADE = REGISTRY.register("glacial_blade", GlacialTouchEnchantment::new);
    public static final RegistryObject<Enchantment> LIGHTNING_LORD = REGISTRY.register("lightning_lord", LightningLordEnchantment::new);
    public static final RegistryObject<Enchantment> TRIPLE_STRIKE = REGISTRY.register("triple_strike", TripleStrikeEnchantment::new);
    public static final RegistryObject<Enchantment> JUSTICE = REGISTRY.register("justice", JusticeEnchantment::new);
    public static final RegistryObject<Enchantment> VENOMOUS = REGISTRY.register("venomous", VenomousEnchantment::new);
    public static final RegistryObject<Enchantment> CRITICAL = REGISTRY.register("critical", CriticalEnchantment::new);
    public static final RegistryObject<Enchantment> DIVINE_GIFT = REGISTRY.register("divine_gift", DivineGiftEnchantment::new);
    public static final RegistryObject<Enchantment> ARMOR_SHREDDING = REGISTRY.register("armor_shredding", ArmorShreddingEnchantment::new);
    public static final RegistryObject<Enchantment> TELEKINESIS = REGISTRY.register("telekinesis", TelekinesisEnchantment::new);
    public static final RegistryObject<Enchantment> REPLENISH = REGISTRY.register("replenish", ReplenishEnchantment::new);
    public static final RegistryObject<Enchantment> YEET_THORNS = REGISTRY.register("yeet_thorns", YeetThornsEnchantment::new);
}