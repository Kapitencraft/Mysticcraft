package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.*;
import net.kapitencraft.mysticcraft.enchantments.OverloadEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public abstract class ModEnchantments {
    public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MysticcraftMod.MOD_ID);
    private static RegistryObject<Enchantment> register(String name, Supplier<Enchantment> supplier) {
        return REGISTRY.register(name, supplier);
    }

    public static final RegistryObject<Enchantment> TRANSYLVANIAN = register("transylvanian", TransylvanianEnchantment::new);
    public static final RegistryObject<Enchantment> HEALTH_MENDING = register("health_mending", HealthMendingEnchantment::new);
    public static final RegistryObject<Enchantment> TRUE_PROTECTION = register("true_protection", TrueProtectionEnchantment::new);
    public static final RegistryObject<Enchantment> MAGIC_PROTECTION = register("magic_protection", MagicProtectionEnchantment::new);
    public static final RegistryObject<Enchantment> ENDER_FRIEND = register("ender_friend", EnderFriendEnchantment::new);
    public static final RegistryObject<Enchantment> OVERLOAD = register("overload", OverloadEnchantment::new);
    public static final RegistryObject<Enchantment> ELVISH_MASTERY = register("elvish_mastery", ElvishMasteryEnchantment::new);
    public static final RegistryObject<Enchantment> LEGOLAS_EMULATION = register("legolas_emulation", LegolasEmulationEnchantment::new);
    public static final RegistryObject<Enchantment> FAST_ARROWS = register("fast_arrows", FastArrowsEnchantment::new);
    public static final RegistryObject<Enchantment> GIANT_KILLER = register("giant_killer", GiantKillerEnchantment::new);
    public static final RegistryObject<Enchantment> AIM = register("aim", AimEnchantment::new);
    public static final RegistryObject<Enchantment> FROZEN_ARROWS = register("frozen_arrows", FrozenArrowsEnchantment::new);
    public static final RegistryObject<Enchantment> INFERNO = register("inferno", InfernoEnchantment::new);
    public static final RegistryObject<Enchantment> BASALT_WALKER = register("basalt_walker", BasaltWalkerEnchantment::new);
    public static final RegistryObject<Enchantment> SNIPE = register("snipe", SnipeEnchantment::new);
    public static final RegistryObject<Enchantment> REJUVENATE = register("rejuvenate", RejuvenateEnchantment::new);
    public static final RegistryObject<Enchantment> GROWTH = register("growth", GrowthEnchantment::new);
    public static final RegistryObject<Enchantment> DOUBLE_JUMP = register("double_jump", DoubleJumpEnchantment::new);
    public static final RegistryObject<Enchantment> FIRM_STAND = register("firm_stand", FirmStandEnchantment::new);
    public static final RegistryObject<Enchantment> PROTECTIVE_COVER = register("protective_cover", ProtectiveCoverEnchantment::new);
    public static final RegistryObject<Enchantment> NECROTIC_TOUCH = register("necrotic_touch", NecroticTouchEnchantment::new);
    public static final RegistryObject<Enchantment> ULTIMATE_WISE = register("ultimate_wise", UltimateWiseEnchantment::new);
    public static final RegistryObject<Enchantment> THORNY = register("thorny", ThornyEnchantment::new);
    public static final RegistryObject<Enchantment> LUMBERJACK = register("lumberjack", LumberjackEnchantment::new);
    public static final RegistryObject<Enchantment> VEIN_MINER = register("vein_miner", VeinMinerEnchantment::new);
    public static final RegistryObject<Enchantment> EFFICIENT_JEWELLING = register("efficient_jewelling", EfficientJewellingEnchantment::new);
    public static final RegistryObject<Enchantment> POISONOUS_BLADE = register("poisonous_blade", PoisonousBladeEnchantment::new);
    public static final RegistryObject<Enchantment> GLACIAL_BLADE = register("glacial_blade", GlacialTouchEnchantment::new);
    public static final RegistryObject<Enchantment> LIGHTNING_LORD = register("lightning_lord", LightningLordEnchantment::new);
    public static final RegistryObject<Enchantment> TRIPLE_STRIKE = register("triple_strike", TripleStrikeEnchantment::new);
    public static final RegistryObject<Enchantment> BACK_STAB = register("back_stab", BackStabEnchantment::new);
    public static final RegistryObject<Enchantment> JUSTICE = register("justice", JusticeEnchantment::new);
    public static final RegistryObject<Enchantment> VENOMOUS = register("venomous", VenomousEnchantment::new);
    public static final RegistryObject<Enchantment> CRITICAL = register("critical", CriticalEnchantment::new);
    public static final RegistryObject<Enchantment> DIVINE_GIFT = register("divine_gift", DivineGiftEnchantment::new);
    public static final RegistryObject<Enchantment> ARMOR_SHREDDING = register("armor_shredding", ArmorShreddingEnchantment::new);
    public static final RegistryObject<Enchantment> TELEKINESIS = register("telekinesis", TelekinesisEnchantment::new);
    public static final RegistryObject<Enchantment> REPLENISH = register("replenish", ReplenishEnchantment::new);
    public static final RegistryObject<Enchantment> YEET_THORNS = register("yeet_thorns", YeetThornsEnchantment::new);
}