package net.kapitencraft.mysticcraft.event;

import net.kapitencraft.mysticcraft.event.custom.RegisterRarityEvent;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.ModLoader;
import net.neoforged.fml.event.IModBusEvent;

import java.util.List;

public class ModEventFactory {

    public static <T extends Event & IModBusEvent> void fireModEvent(T event) {
        ModLoader.postEvent(event);
    }

    public static void onRarityRegister(List<Rarity> rarities) {
        RegisterRarityEvent event = new RegisterRarityEvent(rarities);
        fireModEvent(event);
    }
}