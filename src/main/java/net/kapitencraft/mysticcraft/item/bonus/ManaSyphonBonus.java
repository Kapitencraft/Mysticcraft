package net.kapitencraft.mysticcraft.item.bonus;

import net.kapitencraft.kap_lib.io.serialization.RegistrySerializer;
import net.kapitencraft.kap_lib.item.bonus.Bonus;

public class ManaSyphonBonus implements Bonus<ManaSyphonBonus> {
    public static final ManaSyphonBonus INSTANCE = new ManaSyphonBonus();

    public static final RegistrySerializer<ManaSyphonBonus> SERIALIZER = RegistrySerializer.unit(INSTANCE);

    @Override
    public RegistrySerializer<ManaSyphonBonus> getSerializer() {
        return SERIALIZER;
    }
}
