package net.kapitencraft.mysticcraft.enchantments.weapon.melee;

import net.kapitencraft.mysticcraft.enchantments.abstracts.EffectApplicationEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IWeaponEnchantment;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class PoisonousBladeEnchantment extends EffectApplicationEnchantment implements IWeaponEnchantment {
    public PoisonousBladeEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND}, CalculationType.ONLY_MELEE);
    }


    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    protected MobEffect getEffect() {
        return MobEffects.POISON;
    }

    @Override
    protected int getChance(int level) {
        return 100;
    }

    @Override
    protected int getScale() {
        return 5;
    }
}
