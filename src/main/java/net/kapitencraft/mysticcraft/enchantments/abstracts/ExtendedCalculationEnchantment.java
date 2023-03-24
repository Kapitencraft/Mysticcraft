package net.kapitencraft.mysticcraft.enchantments.abstracts;

import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class ExtendedCalculationEnchantment extends Enchantment {

    private final CalculationType type;

    protected ExtendedCalculationEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots, CalculationType type) {
        super(rarity, category, slots);
        this.type = type;
    }

    public double tryExecute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, MiscUtils.DamageType type) {
        if (this.type.contains(type)) {
            return this.execute(level, enchanted, attacker, attacked, damage);
        }
        return damage;
    }

    protected abstract double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage);

    public enum CalculationType {
        ONLY_MAGIC(MiscUtils.DamageType.MAGIC),
        ONLY_MELEE(MiscUtils.DamageType.MELEE),
        ONLY_RANGED(MiscUtils.DamageType.RANGED),
        ALL(MiscUtils.DamageType.MAGIC, MiscUtils.DamageType.RANGED, MiscUtils.DamageType.MELEE),
        ALL_RANGED(MiscUtils.DamageType.RANGED, MiscUtils.DamageType.MAGIC);

        private final MiscUtils.DamageType[] types;

        CalculationType(MiscUtils.DamageType... types) {
            this.types = types;
        }

        public boolean contains(MiscUtils.DamageType type) {
            for (MiscUtils.DamageType type1 : this.types) {
                if (type1 == type) {
                    return true;
                }
            }
            return false;
        }
    }
}
