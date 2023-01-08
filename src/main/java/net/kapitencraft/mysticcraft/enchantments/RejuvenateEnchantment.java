package net.kapitencraft.mysticcraft.enchantments;

import com.google.common.collect.ImmutableMultimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class RejuvenateEnchantment extends ArmorStatBoostEnchantment {
    public RejuvenateEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.WEARABLE, MISCTools.ARMOR_EQUIPMENT);
    }

    public int getMaxLevel() {
        return 10;
    }

    @Override
    public ImmutableMultimap<Attribute, AttributeModifier> getArmorModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(ModAttributes.HEALTH_REGEN.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MISCTools.createCustomIndex(slot)], "rejuvenate", level * 2, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }
}