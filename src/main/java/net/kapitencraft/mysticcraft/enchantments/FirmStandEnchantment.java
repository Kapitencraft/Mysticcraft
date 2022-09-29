package net.kapitencraft.mysticcraft.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FirmStandEnchantment extends Enchantment {
    public FirmStandEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.WEARABLE, new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.LEGS});
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

}
