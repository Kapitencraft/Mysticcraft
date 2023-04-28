package net.kapitencraft.mysticcraft.enchantments.abstracts;

import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.HashMap;
import java.util.Map;

public abstract class ExtendedCalculationEnchantment extends Enchantment implements ModEnchantment {

    private final CalculationType type;
    private final CalculationPriority priority;

    public static Map<ExtendedCalculationEnchantment, Integer> getAllEnchantments(ItemStack stack, boolean isWeapon) {
        Map<ExtendedCalculationEnchantment, Integer> map = new HashMap<>();
        for (Enchantment enchantment : stack.getAllEnchantments().keySet()) {
            if (enchantment instanceof ExtendedCalculationEnchantment extended) {
                if ((isWeapon && enchantment instanceof IWeaponEnchantment) || (!isWeapon && enchantment instanceof IArmorEnchantment)) {
                    map.put(extended, stack.getAllEnchantments().get(extended));
                }
            }
        }
        return map;
    }

    public static float runWithPriority(ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, MiscUtils.DamageType type, boolean isWeapon, DamageSource source) {
        Map<ExtendedCalculationEnchantment, Integer> enchantmentIntegerMap = getAllEnchantments(enchanted, isWeapon);
        for (CalculationPriority priority : CalculationPriority.values()) {
            for (ExtendedCalculationEnchantment enchantment : enchantmentIntegerMap.keySet()) {
                if (enchantment.priority == priority) {
                    damage = enchantment.tryExecute(enchantmentIntegerMap.get(enchantment), enchanted, attacker, attacked, damage, type, source);
                }
            }
        }
        return (float) damage;
    }

    protected ExtendedCalculationEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot[] slots, CalculationType type, CalculationPriority priority) {
        super(rarity, category, slots);
        this.type = type;
        this.priority = priority;
    }

    public CalculationPriority getPriority() {
        return priority;
    }

    public double tryExecute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, MiscUtils.DamageType type, DamageSource source) {
        if (this.type.contains(type)) {
            return this.execute(level, enchanted, attacker, attacked, damage, source);
        }
        return damage;
    }

    protected abstract double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, DamageSource source);

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

    public enum CalculationPriority {
        HIGHEST,
        HIGH,
        MEDIUM,
        LOW,
        LOWEST;
    }
}
