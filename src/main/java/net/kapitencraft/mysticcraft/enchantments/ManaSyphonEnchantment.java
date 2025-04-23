package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.kap_lib.enchantments.abstracts.ExtendedCalculationEnchantment;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class ManaSyphonEnchantment extends Enchantment implements ExtendedCalculationEnchantment {
    public ManaSyphonEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, MiscHelper.WEAPON_SLOT);
    }

    @Override
    public double execute(int level, ItemStack enchanted, LivingEntity attacker, LivingEntity attacked, double damage, DamageSource source, float attackStrengthScale) {
        AttributeInstance mana = attacker.getAttribute(ExtraAttributes.MANA.get());
        double maxMana = AttributeHelper.getSaveAttributeValue(ExtraAttributes.MAX_MANA.get(), attacker);
        if (mana != null) {
            MathHelper.mul(mana::getBaseValue, mana::setBaseValue, maxMana * 0.0025 * level);
        }
        return damage;
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[]{level * 0.0025 + "%"};
    }

    @Override
    public @NotNull CalculationType type() {
        return CalculationType.ALL;
    }

    @Override
    public @NotNull ProcessPriority priority() {
        return ProcessPriority.LOWEST;
    }
}
