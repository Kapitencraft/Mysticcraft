package net.kapitencraft.mysticcraft.enchantments.weapon.melee;

import net.kapitencraft.mysticcraft.enchantments.abstracts.EffectApplicationEnchantment;
import net.kapitencraft.mysticcraft.init.ModMobEffects;
import net.kapitencraft.mysticcraft.utils.MiscUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class StunningEnchantment extends EffectApplicationEnchantment {
    public StunningEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, MiscUtils.ARMOR_EQUIPMENT, CalculationType.ONLY_MELEE);
    }

    @Override
    protected MobEffect getEffect() {
        return ModMobEffects.STUN.get();
    }

    @Override
    protected int getChance(int level) {
        return 0;
    }

    @Override
    protected int getScale() {
        return 0;
    }

    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[0];
    }
}
