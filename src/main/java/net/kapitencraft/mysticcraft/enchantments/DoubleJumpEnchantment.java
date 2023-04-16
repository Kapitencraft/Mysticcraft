package net.kapitencraft.mysticcraft.enchantments;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ArmorStatBoostEnchantment;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class DoubleJumpEnchantment extends ArmorStatBoostEnchantment {
    public DoubleJumpEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, EquipmentSlot.FEET);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getArmorModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        return new ImmutableMultimap.Builder<Attribute, AttributeModifier>().put(ModAttributes.DOUBLE_JUMP.get(), MysticcraftMod.createModifier(AttributeModifier.Operation.ADDITION, level, EquipmentSlot.FEET)).build();
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[] {level};
    }
}
