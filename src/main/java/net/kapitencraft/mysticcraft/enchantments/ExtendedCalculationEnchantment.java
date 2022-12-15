package net.kapitencraft.mysticcraft.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class ExtendedCalculationEnchantment extends Enchantment implements IWeaponEnchantment {

    protected ExtendedCalculationEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots) {
        super(rarity, category, slots);
    }

    public abstract double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage);


}
