package net.kapitencraft.mysticcraft.item.capability.gemstone;

import net.kapitencraft.mysticcraft.api.MapStream;
import net.kapitencraft.mysticcraft.event.ModEventFactory;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class GemstoneData {
    private final Map<RegistryObject<Item>, GemstoneSlot.Builder> data = new HashMap<>();

    public GemstoneData() {
        ModEventFactory.onGemstoneDataCreated(this);
    }

    public int getAmount() {
        return data.size();
    }

    public void register(RegistryObject<Item> object, GemstoneSlot.Builder builder) {
        if (!data.containsKey(object)) {
            data.put(object, builder);
        } else {
            throw new IllegalStateException("item '" + object.getId() + "' has been registered twice");
        }
    }

    public GemstoneSlot[] get(Item item) {
        return createStream().toMap().get(item).build();
    }

    private MapStream<Item, GemstoneSlot.Builder> createStream() {
        return MapStream.of(data).mapKeys(RegistryObject::get);
    }

    public boolean has(Item item) {
        return createStream().toMap().containsKey(item);
    }
}