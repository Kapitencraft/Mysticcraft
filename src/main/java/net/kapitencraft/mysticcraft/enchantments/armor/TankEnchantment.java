package net.kapitencraft.mysticcraft.enchantments.armor;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IUltimateEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Consumer;

public class TankEnchantment extends StatBoostEnchantment implements IUltimateEnchantment {
    public TankEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR, MiscHelper.ARMOR_EQUIPMENT);
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[] {-level + "%", "+" + level * 2};
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        return multimap -> {
            ArmorItem armorItem = (ArmorItem) enchanted.getItem();
            multimap.put(Attributes.MOVEMENT_SPEED, AttributeHelper.createModifierForSlot("Tank Enchantment", AttributeModifier.Operation.MULTIPLY_BASE, -level, armorItem.getSlot()));
            multimap.put(Attributes.ARMOR, AttributeHelper.createModifierForSlot("Tank Enchantment", AttributeModifier.Operation.MULTIPLY_BASE, level * 2, armorItem.getSlot()));
        };
    }
}
