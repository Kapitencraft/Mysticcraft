package net.kapitencraft.mysticcraft.enchantments.tools;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantmentCategories;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class InfiniteQuiverEnchantment extends Enchantment {
    public InfiniteQuiverEnchantment() {
        super(Rarity.VERY_RARE, ModEnchantmentCategories.QUIVER, EquipmentSlot.values());
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
