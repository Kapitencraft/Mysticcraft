package net.kapitencraft.mysticcraft.item.bonus;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.io.serialization.DataPackSerializer;
import net.kapitencraft.kap_lib.item.bonus.Bonus;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FreezingAuraBonus implements Bonus<FreezingAuraBonus> {
    public static final DataPackSerializer<FreezingAuraBonus> SERIALIZER = DataPackSerializer.unit(FreezingAuraBonus::new);

    @Override
    public DataPackSerializer<FreezingAuraBonus> getSerializer() {
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
