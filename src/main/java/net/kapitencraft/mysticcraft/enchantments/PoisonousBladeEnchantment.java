package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class PoisonousBladeEnchantment extends ExtendedCalculationEnchantment implements IWeaponEnchantment {
    public PoisonousBladeEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}, CalculationType.ONLY_MELEE);
    }

    @Override
    public double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage) {
        if (!MiscUtils.increaseEffectDuration(attacked, MobEffects.POISON, level * 5)) {
            attacked.addEffect(new MobEffectInstance(MobEffects.POISON, 20, 1));
        }
        return damage;
    }

    @Override
    public double getValueMultiplier() {
        return 5;
    }

    @Override
    public boolean isPercentage() {
        return false;
    }
}
