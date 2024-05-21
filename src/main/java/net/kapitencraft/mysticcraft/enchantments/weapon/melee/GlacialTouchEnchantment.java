package net.kapitencraft.mysticcraft.enchantments.weapon.melee;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IWeaponEnchantment;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class GlacialTouchEnchantment extends ExtendedCalculationEnchantment implements IWeaponEnchantment {
    public GlacialTouchEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, MiscHelper.WEAPON_SLOT, CalculationType.ONLY_MELEE, ProcessPriority.LOWEST);
    }


    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    protected double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, DamageSource source) {
        int secondsRandom = MysticcraftMod.RANDOM_SOURCE.nextIntBetweenInclusive(1, level);
        MathHelper.add(attacked::getTicksFrozen, attacked::setTicksFrozen, secondsRandom * 20);
        return damage;
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[0];
    }
}
