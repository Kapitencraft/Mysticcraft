package net.kapitencraft.mysticcraft.enchantments.abstracts;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Consumer;

public abstract class ArmorStatBoostEnchantment extends StatBoostEnchantment implements IArmorEnchantment {
    protected ArmorStatBoostEnchantment(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot... p_44678_) {
        super(p_44676_, p_44677_, p_44678_);
    }

    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        if (enchanted.getItem() instanceof ArmorItem armorItem && armorItem.getSlot() == slot) {
            return getArmorModifiers(level, enchanted, slot);
        }
        return null;
    }

    public abstract Consumer<Multimap<Attribute, AttributeModifier>> getArmorModifiers(int level, ItemStack enchanted, EquipmentSlot slot);

    @Override
    public boolean hasModifiersForThatSlot(EquipmentSlot slot) {
        return CollectionHelper.arrayContains(MiscHelper.ARMOR_EQUIPMENT, slot);
    }
}
