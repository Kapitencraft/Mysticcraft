package net.kapitencraft.mysticcraft.enchantments.weapon.melee;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ManaSyphonEnchantment extends ExtendedCalculationEnchantment {
    public ManaSyphonEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, MiscHelper.WEAPON_SLOT, CalculationType.ALL, ProcessPriority.LOWEST);
    }

    @Override
    protected double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, DamageSource source) {
        AttributeInstance mana = attacker.getAttribute(ModAttributes.MANA.get());
        double maxMana = AttributeHelper.getSaveAttributeValue(ModAttributes.MAX_MANA.get(), attacker);
        if (mana != null) {
            MathHelper.mul(mana::getBaseValue, mana::setBaseValue, maxMana * 0.0025 * level);
        }
        return damage;
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[]{level * 0.0025 + "%"};
    }
}
