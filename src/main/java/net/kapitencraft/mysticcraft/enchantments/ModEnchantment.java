package net.kapitencraft.mysticcraft.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class ModEnchantment extends Enchantment {

    private final Type type;
    protected ModEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots, Type type) {
        super(rarity, category, slots);
        this.type = type;
    }

    public abstract double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage);

    protected enum Type {
        DAMAGE_CALC(),
        STAT_MOD();

        private Type() {

        }
    }
}
