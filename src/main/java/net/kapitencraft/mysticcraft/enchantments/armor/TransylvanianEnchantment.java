package net.kapitencraft.mysticcraft.enchantments.armor;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedAbilityEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IArmorEnchantment;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.misc.HealingHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.List;

public class TransylvanianEnchantment extends ExtendedAbilityEnchantment implements IArmorEnchantment {
    public TransylvanianEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, MiscHelper.ARMOR_EQUIPMENT);
    }

    @Override
    public void onTick(LivingEntity source, int level) {
        List<LivingEntity> livings = MathHelper.getLivingAround(source, level * 1.5);
        livings = livings.stream().filter(living -> living.is(source)).toList();
        HealingHelper.setEffectReason(source);
        source.heal(livings.size() / 2f);
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[] {"" + level * 1.5};
    }
}
