package net.kapitencraft.mysticcraft.enchantments;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ArmorStatBoostEnchantment;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class GrowthEnchantment extends ArmorStatBoostEnchantment {
    public GrowthEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.WEARABLE, MiscUtils.ARMOR_EQUIPMENT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getArmorModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        if (MiscUtils.arrayContains(MiscUtils.ARMOR_EQUIPMENT, slot)) {
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscUtils.createCustomIndex(slot)], "growth", level, AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }
}
