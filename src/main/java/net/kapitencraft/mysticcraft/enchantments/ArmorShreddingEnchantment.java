package net.kapitencraft.mysticcraft.enchantments;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.abstracts.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ArmorShreddingEnchantment extends StatBoostEnchantment implements IWeaponEnchantment{
    public ArmorShreddingEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, MiscUtils.ARMOR_EQUIPMENT);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(ModAttributes.ARMOR_SHREDDER.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscUtils.createCustomIndex(slot)], "Armor Shredder Enchant Mod", level, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean hasModifiersForThatSlot(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND;
    }
}
