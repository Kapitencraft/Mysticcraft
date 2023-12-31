package net.kapitencraft.mysticcraft.enchantments.weapon.melee;

import net.kapitencraft.mysticcraft.enchantments.abstracts.EffectApplicationEnchantment;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class StunningEnchantment extends EffectApplicationEnchantment {
    public StunningEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, MiscHelper.ARMOR_EQUIPMENT, CalculationType.ONLY_MELEE);
    }

    @Override
    protected MobEffect getEffect() {
        return ModMobEffects.STUN.get();
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    protected int getChance(int level) {
        return level;
    }

    @Override
    protected int getScale() {
        return 20;
    }
}
