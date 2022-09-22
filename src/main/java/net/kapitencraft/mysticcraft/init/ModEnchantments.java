package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.AimEnchantment;
import net.kapitencraft.mysticcraft.enchantments.ElvishMasteryEnchant;
import net.kapitencraft.mysticcraft.enchantments.FastArrowsEnchant;
import net.kapitencraft.mysticcraft.enchantments.GiantKillerEnchant;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModEnchantments {
    public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MysticcraftMod.MOD_ID);

    public static RegistryObject<Enchantment> ELVISH_MASTERY = REGISTRY.register("elvish_mastery", ElvishMasteryEnchant::new);
    public static RegistryObject<Enchantment> FAST_ARROWS = REGISTRY.register("fast_arrows", FastArrowsEnchant::new);
    public static RegistryObject<Enchantment> GIANT_KILLER = REGISTRY.register("giant_killer", GiantKillerEnchant::new);
    public static RegistryObject<Enchantment> AIM = REGISTRY.register("aim", AimEnchantment::new);

}
