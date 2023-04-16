package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.ThornsEnchantment;
import org.jetbrains.annotations.NotNull;

public class YeetThornsEnchantment extends ExtendedCalculationEnchantment implements IArmorEnchantment {

    public YeetThornsEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, MiscUtils.ARMOR_EQUIPMENT, CalculationType.ALL, CalculationPriority.LOW);
    }

    @Override
    public double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, DamageSource source) {
        attacker.setDeltaMovement((attacker.getX() - attacked.getX()) / level * 10, 0.4 * level, (attacker.getZ() - attacked.getZ()) / level * 10);
        return damage;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
        return !(enchantment instanceof ThornsEnchantment);
    }

    @Override
    public boolean isPercentage() {
        return false;
    }

    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[] {level*10};
    }
}
