package net.kapitencraft.mysticcraft.item.bonus;

import net.kapitencraft.kap_lib.io.serialization.DataPackSerializer;
import net.kapitencraft.kap_lib.item.bonus.Bonus;

public class SoulMageArmorBonus implements Bonus<SoulMageArmorBonus> {
    public static final DataPackSerializer<SoulMageArmorBonus> SERIALIZER = DataPackSerializer.unit(SoulMageArmorBonus::new);

    @Override
    public DataPackSerializer<SoulMageArmorBonus> getSerializer() {
        return SERIALIZER;
    }
}
