package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ThornyEnchantment extends ExtendedCalculationEnchantment implements IToolEnchantment {

    @Override
    public int getMaxLevel() {
        return 5;
    }

    public ThornyEnchantment() {
        super(Rarity.RARE, FormattingCodes.SHIELD, MiscUtils.WEAPON_SLOT, CalculationType.ALL);
    }

    @Override
    public double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage) {
        attacker.hurt(DamageSource.thorns(attacked), level);
        return damage;
    }
}
