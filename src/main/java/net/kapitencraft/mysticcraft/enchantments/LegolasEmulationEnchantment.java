package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class LegolasEmulationEnchantment extends Enchantment implements ModEnchantment {
    public LegolasEmulationEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public double getValueMultiplier() {
        return 0;
    }

    @Override
    public boolean isPercentage() {
        return false;
    }
}