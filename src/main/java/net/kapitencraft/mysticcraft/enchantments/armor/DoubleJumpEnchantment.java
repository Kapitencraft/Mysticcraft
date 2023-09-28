package net.kapitencraft.mysticcraft.enchantments.armor;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ArmorStatBoostEnchantment;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.utils.AttributeUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Consumer;

public class DoubleJumpEnchantment extends ArmorStatBoostEnchantment {
    public DoubleJumpEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, EquipmentSlot.FEET);
    }

    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getArmorModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        return multimap -> multimap.put(ModAttributes.DOUBLE_JUMP.get(), AttributeUtils.createModifier("Double Jump Enchantment", AttributeModifier.Operation.ADDITION, level));
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
