package net.kapitencraft.mysticcraft.item.gemstone;

import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.ArrayList;
import java.util.HashMap;

public interface IGemstoneApplicable {
    int getGemstoneSlotAmount();
    GemstoneSlot getGemstoneSlot(int slotLoc);
    GemstoneSlot[] getGemstoneSlots();

    HashMap<Attribute, Double> getAttributeModifiers();
    ArrayList<Attribute> getAttributesModified();

}
