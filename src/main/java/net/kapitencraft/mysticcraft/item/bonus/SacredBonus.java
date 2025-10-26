package net.kapitencraft.mysticcraft.item.bonus;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.io.serialization.RegistrySerializer;
import net.kapitencraft.kap_lib.item.bonus.Bonus;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;

public class SacredBonus implements Bonus<SacredBonus> {
    private static final SacredBonus INSTANCE = new SacredBonus();
    public static final RegistrySerializer<SacredBonus> SERIALIZER = RegistrySerializer.unit(INSTANCE);

    @Override
    public RegistrySerializer<SacredBonus> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public float onEntityHurt(LivingEntity attacked, LivingEntity attacker, MiscHelper.DamageType type, float damage) {
        if (attacker.getType().is(EntityTypeTags.UNDEAD)) {
            return damage * 1.1f;
        }
        return damage;
    }
}
