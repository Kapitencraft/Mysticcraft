package net.kapitencraft.mysticcraft.event.custom;

import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;

import java.util.HashMap;
import java.util.function.Predicate;

public class RegisterGemstoneTypePlacementsEvent extends Event implements IModBusEvent {
    private final HashMap<GemstoneType, Predicate<Holder<Biome>>> map;

    public RegisterGemstoneTypePlacementsEvent(HashMap<GemstoneType, Predicate<Holder<Biome>>> map) {
        this.map = map;
    }

    public void addValidation(Predicate<Holder<Biome>> value, GemstoneType... keys) {
        for (GemstoneType key : keys) {
            if (map.containsKey(key)) throw new IllegalStateException("double registration of '" + key.getSerializedName() + "'");
            map.put(key, value);
        }
    }
}
