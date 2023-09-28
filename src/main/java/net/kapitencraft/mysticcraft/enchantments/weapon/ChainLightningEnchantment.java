package net.kapitencraft.mysticcraft.enchantments.weapon;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IUltimateEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IWeaponEnchantment;
import net.kapitencraft.mysticcraft.enchantments.extras.ChainLightningHelper;
import net.kapitencraft.mysticcraft.utils.MathUtils;
import net.kapitencraft.mysticcraft.utils.MiscUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ChainLightningEnchantment extends ExtendedCalculationEnchantment implements IUltimateEnchantment, IWeaponEnchantment {
    public ChainLightningEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, MiscUtils.WEAPON_SLOT, CalculationType.ALL, ProcessPriority.LOWEST);
    }

    @Override
    protected double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, DamageSource source) {
        if (MathUtils.chance(level * 0.001, attacker)) {
            ChainLightningHelper.spawnLightnings(level, attacked, attacker, (float) damage);
        }
        return damage;
    }

    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[] {level * 0.1, (int) (Math.sqrt(level)), level / 2, 1 + level * 0.05};
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
