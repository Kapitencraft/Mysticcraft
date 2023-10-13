package net.kapitencraft.mysticcraft.enchantments.armor;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IToolEnchantment;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ThornyEnchantment extends ExtendedCalculationEnchantment implements IToolEnchantment {

    @Override
    public int getMaxLevel() {
        return 5;
    }

    public ThornyEnchantment() {
        super(Rarity.RARE, FormattingCodes.SHIELD, MiscHelper.WEAPON_SLOT, CalculationType.ALL, ProcessPriority.HIGHEST);
    }

    @Override
    public double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, DamageSource source) {
        attacker.hurt(DamageSource.thorns(attacked), level);
        return damage;
    }


    @Override
    public String[] getDescriptionMods(int level) {
        return new String[] {""};
    }
}
