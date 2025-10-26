package net.kapitencraft.mysticcraft.item.bonus;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.io.serialization.RegistrySerializer;
import net.kapitencraft.kap_lib.item.bonus.Bonus;
import net.minecraft.world.entity.LivingEntity;

public class AssassinBonus implements Bonus<AssassinBonus> {
    public static final AssassinBonus INSTANCE = new AssassinBonus();

    public static final RegistrySerializer<AssassinBonus> SERIALIZER = RegistrySerializer.unit(INSTANCE);

    @Override
    public RegistrySerializer<AssassinBonus> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public float onEntityHurt(LivingEntity attacked, LivingEntity attacker, MiscHelper.DamageType type, float damage) {
        if (MathHelper.isBehind(attacker, attacked)) damage *= 2;
        return damage;
    }
}
