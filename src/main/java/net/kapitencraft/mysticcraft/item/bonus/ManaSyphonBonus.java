package net.kapitencraft.mysticcraft.item.bonus;

import net.kapitencraft.kap_lib.io.serialization.DataPackSerializer;
import net.kapitencraft.kap_lib.item.bonus.Bonus;

public class ManaSyphonBonus implements Bonus<ManaSyphonBonus> {
    public static final DataPackSerializer<ManaSyphonBonus> SERIALIZER = DataPackSerializer.unit(ManaSyphonBonus::new);

    @Override
    public DataPackSerializer<ManaSyphonBonus> getSerializer() {
        return SERIALIZER;
    }
}
