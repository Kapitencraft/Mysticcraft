package net.kapitencraft.mysticcraft.item.gemstone;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.HashMap;
import java.util.Map;

public class GemstoneMap {

    public final int size;
    private GemstoneSlot[] gemstoneSlots;

    public GemstoneMap(int size) {
        this.size = size;
        gemstoneSlots = new GemstoneSlot[this.size];
    }

    public Map<Attribute, AttributeModifier> createAttributeMods() {
        Map<Attribute, AttributeModifier> mods = new HashMap<>();
        for (GemstoneSlot slot : this.gemstoneSlots) {

        }
        return mods;
    }

    public void put(GemstoneSlot slot, int index) {
        this.gemstoneSlots[index] = slot;
    }
}
