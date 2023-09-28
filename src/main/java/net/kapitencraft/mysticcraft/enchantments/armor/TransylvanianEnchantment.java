package net.kapitencraft.mysticcraft.enchantments.armor;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedAbilityEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IArmorEnchantment;
import net.kapitencraft.mysticcraft.misc.HealingHelper;
import net.kapitencraft.mysticcraft.utils.MathUtils;
import net.kapitencraft.mysticcraft.utils.MiscUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.List;

public class TransylvanianEnchantment extends ExtendedAbilityEnchantment implements IArmorEnchantment {
    public TransylvanianEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, MiscUtils.ARMOR_EQUIPMENT);
    }

    @Override
    public void onTick(LivingEntity source, int tick) {
        List<LivingEntity> livings = MathUtils.getLivingAround(source, 5);
        livings = livings.stream().filter((living -> living.is(source))).toList();
        HealingHelper.setEffectReason(source);
        source.heal(livings.size() / 2f);
    }

    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[0];
    }
}
