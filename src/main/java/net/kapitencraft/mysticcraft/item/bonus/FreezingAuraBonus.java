package net.kapitencraft.mysticcraft.item.bonus;

import net.kapitencraft.kap_lib.bonus.Bonus;
import net.kapitencraft.kap_lib.core.helpers.MathHelper;
import net.kapitencraft.kap_lib.core.io.serialization.RegistrySerializer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FreezingAuraBonus implements Bonus<FreezingAuraBonus> {
    public static final FreezingAuraBonus INSTANCE = new FreezingAuraBonus();

    public static final RegistrySerializer<FreezingAuraBonus> SERIALIZER = RegistrySerializer.unit(INSTANCE);

    @Override
    public RegistrySerializer<FreezingAuraBonus> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public void onTick(int tickCount, @NotNull LivingEntity entity) {
        List<LivingEntity> entities = MathHelper.getLivingAround(entity, 3);
        entities.forEach(living -> {
            if (!(living.isDeadOrDying() || living == entity) && tickCount % 20 == 0) {
                living.hurt(living.damageSources().source(DamageTypes.FREEZE, entity), 2);
            }
        });
    }
}
