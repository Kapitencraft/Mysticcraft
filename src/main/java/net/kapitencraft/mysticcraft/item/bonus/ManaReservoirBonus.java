package net.kapitencraft.mysticcraft.item.bonus;

import net.kapitencraft.kap_lib.io.serialization.DataPackSerializer;
import net.kapitencraft.kap_lib.item.bonus.Bonus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.UUID;

public class ManaReservoirBonus implements Bonus<ManaReservoirBonus> {
    private static final UUID CHESTPLATE_BONUS_ID = UUID.fromString("95d00b68-2e46-47b7-b636-450674d3a3ba");

    @Override
    public DataPackSerializer<ManaReservoirBonus> getSerializer() {
        return null;
    }

    @Override
    public void additionalToNetwork(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public void addDisplay(List<Component> list) {

    }
}
