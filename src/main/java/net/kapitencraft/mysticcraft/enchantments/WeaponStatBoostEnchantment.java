package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class WeaponStatBoostEnchantment extends StatBoostEnchantment implements IWeaponEnchantment {

    protected WeaponStatBoostEnchantment(Rarity p_44676_) {
        super(p_44676_, EnchantmentCategory.WEAPON, MISCTools.WEAPON_SLOT);
    }

    @Override
    public boolean hasModifiersForThatSlot(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND;
    }
}
