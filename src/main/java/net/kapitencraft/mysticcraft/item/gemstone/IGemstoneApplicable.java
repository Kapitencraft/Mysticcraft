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
        GemstoneHelper helper = new GemstoneHelper(this.getDefaultSlots());
        helper.loadData(stack);
        return helper;
    }

    default int getGemstoneSlotAmount() {
        return getDefaultSlots().length;
    }
    default GemstoneSlot[] getGemstoneSlots(ItemStack stack) {
        return this.getHelper(stack).getGemstoneSlots(stack);
    }

    default HashMap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        return this.getHelper(stack).getAttributeModifiers(stack, slot);
    }

    GemstoneSlot[] getDefaultSlots();
    default void appendDisplay(ItemStack itemStack, List<Component> list) {
        this.getHelper(itemStack).getDisplay(itemStack, list);
    }


    default boolean putGemstone(GemstoneType gemstoneType, GemstoneType.Rarity rarity, int slotIndex, ItemStack stack) {
        return this.getHelper(stack).putGemstone(gemstoneType, rarity, slotIndex, stack);
    }
}
