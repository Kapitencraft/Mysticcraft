package net.kapitencraft.mysticcraft.event;

import net.kapitencraft.mysticcraft.item.gemstone.Gemstone;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.minecraftforge.common.MinecraftForge;

public class ModEventFactory {

    public static void onGemstoneApplied(Gemstone gemstone, GemstoneSlot gemstoneSlot) {
        GemstoneAppliedEvent event = new GemstoneAppliedEvent(gemstone, gemstoneSlot);
        MinecraftForge.EVENT_BUS.post(event);
    }
}
