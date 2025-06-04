package net.kapitencraft.mysticcraft.item.capability.gemstone;

import com.mojang.serialization.Codec;
import net.kapitencraft.kap_lib.item.capability.CapabilityProvider;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;

import java.util.List;

public class GemstoneCapabilityProvider extends CapabilityProvider<List<GemstoneSlot>, IGemstoneHandler> {
    private static final Codec<List<GemstoneSlot>> SLOTS_CODEC = GemstoneSlot.CODEC.listOf();

    public GemstoneCapabilityProvider(IGemstoneHandler object) {
        super(object, SLOTS_CODEC, CapabilityHelper.GEMSTONE);
    }

    @Override
    protected List<GemstoneSlot> fallback() {
        return List.of();
    }
}
