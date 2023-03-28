package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.misc.utils.MathUtils;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BackStabEnchantment extends ExtendedCalculationEnchantment implements IWeaponEnchantment {
    public BackStabEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, MiscUtils.WEAPON_SLOT, CalculationType.ONLY_MELEE);
    }

    @Override
    public double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage) {
        if (MathUtils.isBehind(attacker, attacked)) {
            return damage * (1 + level * 0.25);
        }
        return damage;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public double getValueMultiplier() {
        return 25;
    }

    @Override
    public boolean isPercentage() {
        return true;
    }
}
