package net.kapitencraft.mysticcraft.enchantments.weapon.melee;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IWeaponEnchantment;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.misc.attribute.TimedModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.UUID;

public class VenomousEnchantment extends ExtendedCalculationEnchantment implements IWeaponEnchantment {
    public static final UUID ID = UUID.fromString("c3f0728b-ecd3-487b-9e5b-a55ae35241c0");
    public static final String TIMER_ID = "VenomousTimer";
    public VenomousEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, MiscHelper.WEAPON_SLOT, CalculationType.ALL, ProcessPriority.LOWEST);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }


    @Override
    public double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, DamageSource source) {
        AttributeInstance speed = attacked.getAttribute(Attributes.MOVEMENT_SPEED);
        assert speed != null;
        try {
            speed.addTransientModifier(TimedModifier.addModifier(TIMER_ID, -0.05 * level, AttributeModifier.Operation.MULTIPLY_TOTAL, 100, attacked, Attributes.MOVEMENT_SPEED));
        } catch (Throwable ignored) {}
        return damage;
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[] {level * 5 + "%"};
    }
}
