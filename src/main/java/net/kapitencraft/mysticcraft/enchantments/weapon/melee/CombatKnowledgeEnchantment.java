package net.kapitencraft.mysticcraft.enchantments.weapon.melee;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.utils.MathUtils;
import net.kapitencraft.mysticcraft.utils.MiscUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class CombatKnowledgeEnchantment extends ExtendedCalculationEnchantment {

    public CombatKnowledgeEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, MiscUtils.WEAPON_SLOT, CalculationType.ONLY_MELEE, ProcessPriority.HIGHEST);
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    protected double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, DamageSource source) {
        return MathUtils.chance(level * 0.1, attacker) ? Float.MAX_VALUE : damage;
    }

    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[]{level * 0.1};
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}