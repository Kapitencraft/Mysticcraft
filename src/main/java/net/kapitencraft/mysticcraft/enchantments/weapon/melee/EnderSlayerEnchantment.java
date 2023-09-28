package net.kapitencraft.mysticcraft.enchantments.weapon.melee;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.utils.MiscUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnderSlayerEnchantment extends ExtendedCalculationEnchantment {
    public EnderSlayerEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.WEAPON, MiscUtils.WEAPON_SLOT, CalculationType.ONLY_MELEE, ProcessPriority.HIGHEST);
    }

    @Override
    protected double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, DamageSource source) {
        return attacked instanceof EnderMan ? damage * (1 + level / 4f) : damage;
    }

    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[]{level / 4};
    }
}
