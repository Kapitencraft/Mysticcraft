package net.kapitencraft.mysticcraft.event;

import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

public class RegisterRarityEvent extends Event {

    private final List<Rarity> rarities;

    public RegisterRarityEvent(List<Rarity> rarities) {
        this.rarities = rarities;
    }

    public void addRarity(Rarity rarity) {
        this.rarities.add(rarity);
    }
}
