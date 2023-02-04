package net.kapitencraft.mysticcraft.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class GiantKillerEnchantment extends ExtendedCalculationEnchantment implements IWeaponEnchantment {

    public GiantKillerEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage) {
        double MoreHpPercent = attacked.getHealth() - attacker.getHealth() / attacked.getMaxHealth() - attacker.getMaxHealth();
        return  (float) (damage * (1 + MoreHpPercent * level * 0.01));
    }
}
