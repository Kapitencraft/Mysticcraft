package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.ElvishMasteryEnchant;
import net.kapitencraft.mysticcraft.enchantments.FastArrowsEnchant;
import net.kapitencraft.mysticcraft.enchantments.GiantKillerEnchant;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MysticcraftMod.MOD_ID);

    public static RegistryObject<Enchantment> ELVISH_MASTERY = REGISTRY.register("elvish_mastery", () -> new ElvishMasteryEnchant(Enchantment.Rarity.RARE, EnchantmentCategory.BOW, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> FAST_ARROWS = REGISTRY.register("fast_arrows", () -> new FastArrowsEnchant(Enchantment.Rarity.RARE, EnchantmentCategory.BOW, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> GIANT_KILLER = REGISTRY.register("giant_killer", () -> new GiantKillerEnchant(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));
    public static RegistryObject<Enchantment> SNIPE = REGISTRY.register("snipe", () -> new GiantKillerEnchant(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.BOW, EquipmentSlot.MAINHAND));


}
