package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.ThornsEnchantment;

public class YeetThornsEnchantment extends ExtendedCalculationEnchantment{
    @Override
    public Type getType() {
        return Type.ARMOR;
    }

    public YeetThornsEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, MISCTools.ARMOR_EQUIPMENT);
    }

    @Override
    public double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage) {
        attacked.setDeltaMovement((attacker.getX() - attacked.getX()) / level / 0.1, 0.4 * level, (attacker.getZ() - attacked.getZ()) / level / 0.1);
        return damage;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment) {
        return !(enchantment instanceof ThornsEnchantment);
    }
}
