package net.kapitencraft.mysticcraft.enchantments;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FirmStandEnchantment extends StatBoostEnchantment {
    public FirmStandEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.WEARABLE, new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.LEGS});
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        return null;
    }
}
