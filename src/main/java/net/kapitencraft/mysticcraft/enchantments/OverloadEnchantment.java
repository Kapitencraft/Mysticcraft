package net.kapitencraft.mysticcraft.enchantments;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class OverloadEnchantment extends StatBoostEnchantment implements ModEnchantment {
    public OverloadEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.BOW, MiscUtils.WEAPON_SLOT);
    }

    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[]{level * -2, level, level * 10};
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(ModAttributes.DRAW_SPEED.get(), MysticcraftMod.createModifier(AttributeModifier.Operation.ADDITION, -2 * level, slot));
        builder.put(ModAttributes.CRIT_DAMAGE.get(), MysticcraftMod.createModifier(AttributeModifier.Operation.ADDITION, level, slot));
        return builder.build();
    }

    @Override
    public boolean hasModifiersForThatSlot(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
