package net.kapitencraft.mysticcraft.item.capability.item_stat;

import com.mojang.serialization.Codec;
import net.kapitencraft.kap_lib.item.capability.CapabilityProvider;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;

import java.util.Map;

public class ItemStatCapabilityProvider extends CapabilityProvider<Map<ItemStatCapability.Type, Long>, ItemStatCapability> {
    public ItemStatCapabilityProvider(ItemStatCapability object) {
        super(object, Codec.unboundedMap(ItemStatCapability.Type.CODEC, Codec.LONG), CapabilityHelper.ITEM_STAT);
    }

    @Override
    protected Map<ItemStatCapability.Type, Long> fallback() {
        return Map.of();
    }
}
