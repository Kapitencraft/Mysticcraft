package net.kapitencraft.mysticcraft.item.bonus;

import net.kapitencraft.kap_lib.io.serialization.DataPackSerializer;
import net.kapitencraft.kap_lib.item.bonus.Bonus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

import java.util.List;

public class SoulMageArmorBonus implements Bonus<SoulMageArmorBonus> {
    public static final DataPackSerializer<SoulMageArmorBonus> SERIALIZER = DataPackSerializer.unit(SoulMageArmorBonus::new);

    @Override
    public DataPackSerializer<SoulMageArmorBonus> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public void additionalToNetwork(FriendlyByteBuf buf) {

    }

    @Override
    public void addDisplay(List<Component> currentTooltip) {
        currentTooltip.add(Component.translatable("set_bonus.mana_syphon"));
    }
}
