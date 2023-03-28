package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.enchantments.abstracts.CountEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class TripleStrikeEnchantment extends CountEnchantment {
    public TripleStrikeEnchantment() {
        super(Rarity.UNCOMMON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND}, "TripleStrikeMap", countType.EXCEPT, CalculationType.ALL);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getMinCost(int level) {
        return 5 + level * 20;
    }

    @Override
    public int getMaxCost(int p_44691_) {
        return this.getMinCost(p_44691_) * 2;
    }

    @Override
    protected int getCountAmount(int level) {
        return 3;
    }

    @Override
    protected double mainExecute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damageAmount, int curTick) {
        damageAmount *= (1 + 0.2 * level);
        return damageAmount;
    }

    @Override
    public double getValueMultiplier() {
        return 20;
    }

    @Override
    public boolean isPercentage() {
        return true;
    }
}
