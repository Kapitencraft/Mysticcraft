package net.kapitencraft.mysticcraft.item.bonus;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.io.serialization.DataPackSerializer;
import net.kapitencraft.kap_lib.item.bonus.Bonus;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;

public class SacredBonus implements Bonus<SacredBonus> {
    public static final DataPackSerializer<SacredBonus> SERIALIZER = DataPackSerializer.unit(SacredBonus::new);

    @Override
    public DataPackSerializer<SacredBonus> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public float onEntityHurt(LivingEntity attacked, LivingEntity attacker, MiscHelper.DamageType type, float damage) {
        if (attacked instanceof Mob mob && mob.getMobType() == MobType.UNDEAD) return damage * 1.1f;
        return damage;
    }
}
