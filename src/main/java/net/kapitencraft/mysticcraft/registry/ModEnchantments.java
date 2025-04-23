package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.EfficientJewellingEnchantment;
import net.kapitencraft.mysticcraft.enchantments.ManaSyphonEnchantment;
import net.kapitencraft.mysticcraft.enchantments.UltimateWiseEnchantment;
import net.kapitencraft.mysticcraft.enchantments.tools.CapacityEnchantment;
import net.kapitencraft.mysticcraft.enchantments.tools.FlashEnchantment;
import net.kapitencraft.mysticcraft.enchantments.tools.InfiniteQuiverEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
@SuppressWarnings("unused")
public interface ModEnchantments {
    DeferredRegister<Enchantment> REGISTRY = MysticcraftMod.registry(ForgeRegistries.ENCHANTMENTS);

    private static RegistryObject<Enchantment> register(String name, Supplier<Enchantment> supplier) {
        return REGISTRY.register(name, supplier);
    }

    RegistryObject<Enchantment> FLASH = register("flash", FlashEnchantment::new);
    RegistryObject<Enchantment> MANA_SYPHON = register("mana_syphon", ManaSyphonEnchantment::new);
    RegistryObject<Enchantment> INFINITE_QUIVER = register("infinite_quiver", InfiniteQuiverEnchantment::new);
    RegistryObject<Enchantment> CAPACITY = register("capacity", CapacityEnchantment::new);
    RegistryObject<Enchantment> ULTIMATE_WISE = register("ultimate_wise", UltimateWiseEnchantment::new);
    RegistryObject<Enchantment> EFFICIENT_JEWELLING = register("efficient_jewelling", EfficientJewellingEnchantment::new);
}