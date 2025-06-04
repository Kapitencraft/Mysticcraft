package net.kapitencraft.mysticcraft.item.bonus;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.io.serialization.DataPackSerializer;
import net.kapitencraft.kap_lib.item.bonus.Bonus;
import net.minecraft.world.entity.LivingEntity;

public class AssassinBonus implements Bonus<AssassinBonus> {
    public static final DataPackSerializer<AssassinBonus> SERIALIZER = DataPackSerializer.unit(AssassinBonus::new);

    @Override
    public DataPackSerializer<AssassinBonus> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public float onEntityHurt(LivingEntity attacked, LivingEntity attacker, MiscHelper.DamageType type, float damage) {
        if (MathHelper.isBehind(attacker, attacked)) damage *= 2;
        return damage;
    }
}
