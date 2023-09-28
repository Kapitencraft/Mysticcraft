package net.kapitencraft.mysticcraft.enchantments.armor;

import net.kapitencraft.mysticcraft.enchantments.abstracts.IArmorEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ProtectiveCoverEnchantment extends Enchantment implements IArmorEnchantment {
    public ProtectiveCoverEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }
}
