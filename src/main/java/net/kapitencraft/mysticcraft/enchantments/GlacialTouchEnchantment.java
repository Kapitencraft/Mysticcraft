package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class GlacialTouchEnchantment extends ExtendedCalculationEnchantment implements IWeaponEnchantment {
    public GlacialTouchEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, MiscUtils.WEAPON_SLOT, CalculationType.ALL, CalculationPriority.LOW);
    }


    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, DamageSource source) {
        if (!MiscUtils.increaseEffectDuration(attacked, MobEffects.MOVEMENT_SLOWDOWN, level * 5)) {
            attacked.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
        }
        return damage;
    }

    @Override
    public boolean isPercentage() {
        return false;
    }

    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[] {level*5};
    }
}
