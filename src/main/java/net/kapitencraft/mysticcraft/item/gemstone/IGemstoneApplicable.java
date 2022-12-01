package net.kapitencraft.mysticcraft.item.gemstone;

import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
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
    default HashMap<Attribute, Double> getAttributeModifiers(ItemStack itemStack) {
        HashMap<Attribute, Double> attributeModifier = new HashMap<>();
        double gemstoneModifier;
        @org.jetbrains.annotations.Nullable Attribute attribute;
        @org.jetbrains.annotations.Nullable Gemstone gemstone;
        for (@org.jetbrains.annotations.Nullable GemstoneSlot slot : this.getGemstoneSlots()) {
            if (slot != null) {
                gemstone = slot.getAppliedGemstone();
                if (gemstone != null) {
                    attribute = gemstone.modifiedAttribute;
                    gemstoneModifier = gemstone.BASE_VALUE * gemstone.getRarity().modMul * (1 + itemStack.getEnchantmentLevel(ModEnchantments.EFFICIENT_JEWELLING.get()) * 0.02);
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
    default ArrayList<Attribute> getAttributesModified() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        @Nullable Attribute attribute;
        @Nullable Gemstone gemstone;
        for (@Nullable GemstoneSlot slot : this.getGemstoneSlots()) {
            if (slot != null) {
                gemstone = slot.getAppliedGemstone();
                if (gemstone != null) {
                    attribute = gemstone.modifiedAttribute;
                    if (MISCTools.ArrayContains(attributes.toArray(), attribute)) {
                        attributes.add(attribute);
                    }
                }
            }
        }
        return attributes;
    }

    default void getDisplay(ItemStack itemStack, List<Component> list) {
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

    default void getModInfo(ItemStack stack, List<Component> list) {
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
                        list.add(Component.literal(ignored.toString() + ": " + this.getAttributeModifiers(stack).get(ignored)));
                    }
                } else {
                    list.add(Component.literal("press [SHIFT] for Gemstone Information"));
                }
            }
        }

    }
}
