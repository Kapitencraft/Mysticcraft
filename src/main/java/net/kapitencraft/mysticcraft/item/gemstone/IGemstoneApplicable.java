package net.kapitencraft.mysticcraft.item.gemstone;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IGemstoneApplicable {
    int getGemstoneSlotAmount();
    GemstoneSlot getGemstoneSlot(int slotLoc);
    GemstoneSlot[] getGemstoneSlots();

    HashMap<Attribute, Double> getAttributeModifiers();
    ArrayList<Attribute> getAttributesModified();

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
}
