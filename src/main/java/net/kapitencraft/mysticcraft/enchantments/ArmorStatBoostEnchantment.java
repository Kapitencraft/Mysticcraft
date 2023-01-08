package net.kapitencraft.mysticcraft.enchantments;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class ArmorStatBoostEnchantment extends StatBoostEnchantment implements IArmorEnchantment {
    protected ArmorStatBoostEnchantment(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot[] p_44678_) {
        super(p_44676_, p_44677_, p_44678_);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        if (enchanted.getItem() instanceof ArmorItem armorItem && armorItem.getSlot() == slot) {
            return getArmorModifiers(level, enchanted, slot);
        }
        return null;
    }

    public abstract Multimap<Attribute, AttributeModifier> getArmorModifiers(int level, ItemStack enchanted, EquipmentSlot slot);

    @Override
    public boolean hasModifiersForThatSlot(EquipmentSlot slot) {
        return MISCTools.ArrayContains(MISCTools.ARMOR_EQUIPMENT, slot);
    }
}
