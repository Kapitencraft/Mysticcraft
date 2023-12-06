package net.kapitencraft.mysticcraft.enchantments.armor;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IUltimateEnchantment;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BerserkerEnchantment extends ExtendedCalculationEnchantment implements IUltimateEnchantment {
    protected BerserkerEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots, CalculationType type, ProcessPriority priority) {
        super(rarity, category, slots, type, priority);
    }

    @Override
    protected double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, DamageSource source) {
        return 0;
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[0];
    }
}
