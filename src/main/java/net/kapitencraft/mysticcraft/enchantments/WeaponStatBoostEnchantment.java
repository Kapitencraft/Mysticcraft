package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.enchantments.abstracts.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class WeaponStatBoostEnchantment extends StatBoostEnchantment implements IWeaponEnchantment {

    protected WeaponStatBoostEnchantment(Rarity p_44676_) {
        super(p_44676_, EnchantmentCategory.WEAPON, MiscUtils.WEAPON_SLOT);
    }

    @Override
    public boolean hasModifiersForThatSlot(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND;
    }
}
