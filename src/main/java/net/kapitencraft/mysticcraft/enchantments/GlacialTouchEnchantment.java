package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class GlacialTouchEnchantment extends ExtendedCalculationEnchantment {
    public GlacialTouchEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, MISCTools.WEAPON_SLOT);
    }

    @Override
    public double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage) {
        if (!MISCTools.increaseEffectDuration(attacked, MobEffects.MOVEMENT_SLOWDOWN, level * 5)) {
            attacked.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
        }
        return damage;
    }
}
