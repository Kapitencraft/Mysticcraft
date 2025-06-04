package net.kapitencraft.mysticcraft.item.capability.elytra;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.kapitencraft.kap_lib.item.capability.CapabilityProvider;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;

public class ElytraCapabilityProvider extends CapabilityProvider<Pair<ElytraData, Integer>, IElytraData> {
    private static final Codec<Pair<ElytraData, Integer>> CODEC = Codec.pair(ElytraData.CODEC, Codec.INT);

    protected ElytraCapabilityProvider(IElytraData object) {
        super(object, CODEC, CapabilityHelper.ELYTRA);
    }

    @Override
    protected Pair<ElytraData, Integer> fallback() {
        return Pair.of(ElytraData.UNBREAKING, 1);
    }
}
