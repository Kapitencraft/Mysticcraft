package net.kapitencraft.mysticcraft.enchantments.weapon;

import net.kapitencraft.mysticcraft.content.ChainLightningHelper;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IUltimateEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IWeaponEnchantment;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class ChainLightningEnchantment extends ExtendedCalculationEnchantment implements IUltimateEnchantment, IWeaponEnchantment {
    public ChainLightningEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, MiscHelper.WEAPON_SLOT, CalculationType.ALL, ProcessPriority.LOWEST);
    }

    @Override
    protected double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, DamageSource source) {
        if (MathHelper.chance(level * 0.001, attacker)) {
            ChainLightningHelper.spawnLightnings(level, attacked, attacker, (float) damage);
        }
        return damage;
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[] {(level * 0.1) + "%", (int) (Math.sqrt(level)) + "", level / 2 + "", 1 + level * 0.05 + "%"};
    }


    @Override
    protected boolean checkCompatibility(@NotNull Enchantment ench) {
        return !(ench instanceof IUltimateEnchantment);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
