package net.kapitencraft.mysticcraft.enchantments.armor;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IUltimateEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.utils.AttributeUtils;
import net.kapitencraft.mysticcraft.utils.MiscUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Consumer;

public class TankEnchantment extends StatBoostEnchantment implements IUltimateEnchantment {
    public TankEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, MiscUtils.ARMOR_EQUIPMENT);
    }

    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[0];
    }

    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        return multimap -> {
            multimap.put(Attributes.MOVEMENT_SPEED, AttributeUtils.createModifier("Tank Enchantment", AttributeModifier.Operation.MULTIPLY_BASE, -level));
            multimap.put(Attributes.ARMOR, AttributeUtils.createModifier("Tank Enchantment", AttributeModifier.Operation.MULTIPLY_BASE, level * 2));
        };
    }
}
