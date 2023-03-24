package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class VenomousEnchantment extends ExtendedCalculationEnchantment implements IWeaponEnchantment {
    public VenomousEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, MiscUtils.WEAPON_SLOT, CalculationType.ALL);
    }

    @Override
    public double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage) {
        return 0;
    }
}
