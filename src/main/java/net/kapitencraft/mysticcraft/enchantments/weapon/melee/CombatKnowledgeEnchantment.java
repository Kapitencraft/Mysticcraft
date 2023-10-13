package net.kapitencraft.mysticcraft.enchantments.weapon.melee;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class CombatKnowledgeEnchantment extends ExtendedCalculationEnchantment {

    public CombatKnowledgeEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, MiscHelper.WEAPON_SLOT, CalculationType.ONLY_MELEE, ProcessPriority.HIGHEST);
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    protected double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, DamageSource source) {
        return MathHelper.chance(level * 0.1, attacker) ? Float.MAX_VALUE : damage;
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[]{level * 0.1 + "%"};
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}