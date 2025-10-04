package net.kapitencraft.mysticcraft.entity.ai.sensor;

import net.kapitencraft.mysticcraft.tags.ModTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;

public class DragonAttackablesSensor extends NearestVisibleLivingEntitySensor {
    @Override
    protected boolean isMatchingEntity(LivingEntity pAttacker, LivingEntity pTarget) {
        return pTarget.getType().is(ModTags.Entities.DRAGON_TARGETS);
    }

    @Override
    protected MemoryModuleType<LivingEntity> getMemory() {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }
}
