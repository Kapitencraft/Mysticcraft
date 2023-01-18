package net.kapitencraft.mysticcraft.event;

import net.kapitencraft.mysticcraft.item.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.minecraftforge.eventbus.api.Event;

public class GemstoneAppliedEvent extends Event {
    private final GemstoneType gemstoneType;
    private final GemstoneSlot slot;

    public GemstoneAppliedEvent(GemstoneType gemstoneType, GemstoneSlot slot) {
        this.gemstoneType = gemstoneType;
        this.slot = slot;
    }

    public GemstoneType getGemstone() {
        return this.gemstoneType;
    }

    public GemstoneSlot getSlot() {
        return this.slot;
    }
}
