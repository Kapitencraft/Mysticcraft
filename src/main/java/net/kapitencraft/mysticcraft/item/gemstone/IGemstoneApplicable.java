package net.kapitencraft.mysticcraft.item.gemstone;

import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IGemstoneApplicable {

    int getGemstoneSlotAmount();
    GemstoneSlot[] getGemstoneSlots();
    void setGemstoneSlots(GemstoneSlot[] slots);
    default HashMap<Attribute, Double> getAttributeModifiers(ItemStack itemStack) {
        this.loadData(itemStack);
        HashMap<Attribute, Double> attributeModifier = new HashMap<>();
        double gemstoneModifier;
        @Nullable Attribute attribute;
        @Nullable GemstoneType gemstoneType;
        for (@Nullable GemstoneSlot slot : this.getGemstoneSlots()) {
            if (slot != null) {
                gemstoneType = slot.getAppliedGemstone();
                if (gemstoneType != null) {
                    attribute = gemstoneType.modifiedAttribute;
                    gemstoneModifier = gemstoneType.BASE_VALUE * slot.getGemRarity().modMul * (1 + itemStack.getEnchantmentLevel(ModEnchantments.EFFICIENT_JEWELLING.get()) * 0.02);
                    if (attributeModifier.containsKey(attribute)) {
                        attributeModifier.put(attribute, attributeModifier.get(attribute) + gemstoneModifier);
                    } else {
                        attributeModifier.put(attribute, gemstoneModifier);
                    }
                }
            }
        }
        return attributeModifier;
    }
    default void addData(ItemStack current) {
        CompoundTag tag = new CompoundTag();
        for (int i = 0; i < this.getGemstoneSlots().length; i++) {
            GemstoneSlot slot = this.getGemstoneSlots()[i];
            tag.put("slot" + i, slot.toNBT());
        }
        tag.putBoolean("IsSlotData", true);
        current.setTag(tag);
    }
    default ArrayList<Attribute> getAttributesModified() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        @Nullable Attribute attribute;
        @Nullable GemstoneType gemstoneType;
        for (@Nullable GemstoneSlot slot : this.getGemstoneSlots()) {
            if (slot != null) {
                gemstoneType = slot.getAppliedGemstone();
                if (gemstoneType != null) {
                    attribute = gemstoneType.modifiedAttribute;
                    if (MISCTools.ArrayContains(attributes.toArray(), attribute)) {
                        attributes.add(attribute);
                    }
                }
            }
        }
        return attributes;
    }

    default boolean loadData(ItemStack current) {
        CompoundTag tag = current.getOrCreateTag();
        if (!(tag.contains("isSlotData") && tag.getBoolean("isSlotData"))) {
            return false;
        } else {
            GemstoneSlot[] slots = new GemstoneSlot[this.getGemstoneSlotAmount()];
            for (int i = 0; i < this.getGemstoneSlotAmount(); i++) {
                if (!(tag.get("slot" + i) == null)) {
                    slots[i] = GemstoneSlot.fromNBT((CompoundTag) tag.get("slot" + i));
                } else {
                    return false;
                }
            }
            this.putGemstones(slots);
            return true;
        }
    }
    default void getDisplay(ItemStack itemStack, List<Component> list) {
        this.loadData(itemStack);
        StringBuilder gemstoneText = new StringBuilder();
        if (itemStack.getItem() instanceof IGemstoneApplicable gemstoneApplicable) {
            for (@Nullable GemstoneSlot slot : gemstoneApplicable.getGemstoneSlots()) {
                if (slot != null) {
                    gemstoneText.append(slot.getDisplay());
                }
            }
        }
        if (!gemstoneText.toString().equals("")) {
            list.add(Component.literal(gemstoneText.toString()));
        }
    }

    default boolean putGemstones(GemstoneSlot[] slots) {
        if (slots.length == this.getGemstoneSlotAmount()) {
            this.setGemstoneSlots(slots);
            return true;
        }
        return false;
    }

    default boolean putGemstone(GemstoneType gemstoneType, GemstoneType.Rarity rarity, int slotIndex, ItemStack current) {
        addData(current);
        return this.getGemstoneSlots()[slotIndex].putGemstone(gemstoneType, rarity);
    }

    default void getModInfo(ItemStack stack, List<Component> list) {
        this.loadData(stack);
        if (this.getGemstoneSlots() != null) {
            boolean flag1 = false;
            for (@Nullable GemstoneSlot slot : this.getGemstoneSlots()) {
                flag1 = slot != null && slot.getAppliedGemstone() != null;
                if (flag1) {
                    break;
                }
            }
            if (flag1) {
                if (Screen.hasShiftDown()) {
                    list.add(Component.literal("Gemstone Modifications:").withStyle(ChatFormatting.GREEN));
                    for (Attribute ignored : this.getAttributesModified()) {
                        list.add(Component.translatable(ignored.getDescriptionId()).append(Component.literal(": " + this.getAttributeModifiers(stack).get(ignored))));
                    }
                } else {
                    list.add(Component.literal("press [SHIFT] for Gemstone Information").withStyle(ChatFormatting.GREEN));
                }
            }
        }
    }
}
