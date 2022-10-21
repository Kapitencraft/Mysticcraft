package net.kapitencraft.mysticcraft.event;

import net.kapitencraft.mysticcraft.item.gemstone.Gemstone;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.minecraftforge.eventbus.api.Event;

public class GemstoneAppliedEvent extends Event {
    private final Gemstone gemstone;
    private final GemstoneSlot slot;

    public GemstoneAppliedEvent(Gemstone gemstone, GemstoneSlot slot) {
        this.gemstone = gemstone;
        this.slot = slot;
    }

    public Gemstone getGemstone() {
        return this.gemstone;
    }

    public GemstoneSlot getSlot() {
        return this.slot;
    }
}
