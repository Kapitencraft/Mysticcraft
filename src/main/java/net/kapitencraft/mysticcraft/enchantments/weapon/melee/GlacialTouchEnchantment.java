package net.kapitencraft.mysticcraft.enchantments.weapon.melee;

import net.kapitencraft.mysticcraft.enchantments.abstracts.EffectApplicationEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IWeaponEnchantment;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class GlacialTouchEnchantment extends EffectApplicationEnchantment implements IWeaponEnchantment {
    public GlacialTouchEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, MiscHelper.WEAPON_SLOT, CalculationType.ALL);
    }


    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    protected MobEffect getEffect() {
        return MobEffects.MOVEMENT_SLOWDOWN;
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
