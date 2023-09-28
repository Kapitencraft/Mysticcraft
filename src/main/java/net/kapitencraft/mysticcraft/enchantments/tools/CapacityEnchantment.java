package net.kapitencraft.mysticcraft.enchantments.tools;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantmentCategories;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class CapacityEnchantment extends Enchantment {
    public CapacityEnchantment() {
        super(Rarity.COMMON, ModEnchantmentCategories.STORAGE_ITEM, EquipmentSlot.values());
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
