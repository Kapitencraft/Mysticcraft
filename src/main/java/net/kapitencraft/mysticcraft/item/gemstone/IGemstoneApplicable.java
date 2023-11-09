package net.kapitencraft.mysticcraft.item.gemstone;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;

public interface IGemstoneApplicable {
    default GemstoneHelper getHelper(ItemStack stack) {
        return GemstoneHelper.get(stack);
    }
    default GemstoneSlot[] getGemstoneSlots(ItemStack stack) {
        return this.getHelper(stack).getGemstoneSlots();
    }

    default HashMap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        return this.getHelper(stack).getAttributeModifiers(stack, slot);
    }

    GemstoneSlot[] getDefaultSlots();
    default void appendDisplay(ItemStack itemStack, List<Component> list) {
        this.getHelper(itemStack).getDisplay(list);
    }


    default boolean putGemstone(GemstoneType gemstoneType, GemstoneType.Rarity rarity, int slotIndex, ItemStack stack) {
        return this.getHelper(stack).putGemstone(gemstoneType, rarity, slotIndex, stack);
    }
}
