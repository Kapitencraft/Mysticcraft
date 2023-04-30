package net.kapitencraft.mysticcraft.item.gemstone;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IGemstoneApplicable {
    GemstoneHelper getHelper();
    default int getGemstoneSlotAmount() {
        return getHelper().getGemstoneSlotAmount();
    }
    default GemstoneSlot[] getGemstoneSlots(ItemStack stack) {
        return this.getHelper().getGemstoneSlots(stack);
    }

    default ArrayList<Attribute> getAttributesModified(ItemStack stack) {
        return getHelper().getAttributesModified(stack);
    }

    default HashMap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack) {
        return this.getHelper().getAttributeModifiers(stack);
    }

    default void removeGemstone(int slotIndex, ItemStack stack) {
        this.getHelper().removeGemstone(slotIndex, stack);
    }

    GemstoneSlot[] getDefaultSlots();
    default void appendDisplay(ItemStack itemStack, List<Component> list) {
        this.getHelper().getDisplay(itemStack, list);
    }


    default boolean putGemstone(GemstoneType gemstoneType, GemstoneType.Rarity rarity, int slotIndex, ItemStack stack) {
        return this.getHelper().putGemstone(gemstoneType, rarity, slotIndex, stack);
    }



    default void getModInfo(ItemStack stack, List<Component> list) {
        this.getHelper().getModInfo(stack, list);
    }

}
