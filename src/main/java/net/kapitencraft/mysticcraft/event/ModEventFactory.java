package net.kapitencraft.mysticcraft.event;

import net.kapitencraft.mysticcraft.item.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.minecraftforge.common.MinecraftForge;

public class ModEventFactory {

    public static void onGemstoneApplied(GemstoneType gemstoneType, GemstoneSlot gemstoneSlot) {
        GemstoneAppliedEvent event = new GemstoneAppliedEvent(gemstoneType, gemstoneSlot);
        MinecraftForge.EVENT_BUS.post(event);
    }
}
