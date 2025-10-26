package net.kapitencraft.mysticcraft.event.custom;

import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;

import java.util.List;

public class RegisterRarityEvent extends Event implements IModBusEvent {

    private final List<Rarity> rarities;

    public RegisterRarityEvent(List<Rarity> rarities) {
        this.rarities = rarities;
    }

    public void addRarity(Rarity rarity) {
        this.rarities.add(rarity);
    }
}
