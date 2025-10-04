package net.kapitencraft.mysticcraft.entity.dragon;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.client.particle.flame.FlamesForColors;
import net.kapitencraft.mysticcraft.data_gen.ModDamageTypes;
import net.kapitencraft.mysticcraft.network.ModMessages;
import net.kapitencraft.mysticcraft.network.packets.S2C.BreathParticlesPacket;
import net.kapitencraft.mysticcraft.registry.ModMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;

@ParametersAreNonnullByDefault
public class BreathFire extends Behavior<Dragon> {

    public BreathFire() {
        super(Map.of(
                MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT,
                ModMemoryModuleTypes.DRAGON_FIRE_BREATH_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT
        ), 60);
    }

    @Override
    protected void tick(ServerLevel pLevel, Dragon pOwner, long pGameTime) {
        pOwner.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent((p_289393_) -> {
            pOwner.getLookControl().setLookAt(p_289393_.position());
        });
        List<LivingEntity> cone = MathHelper.getAllEntitiesInsideCone(LivingEntity.class, 15, 10, pOwner.getEyePosition(), pOwner.getRotationVector(), pLevel);
        cone.stream().filter(pOwner::canTargetEntity).forEach(living -> {
            living.hurt(new DamageSource(MiscHelper.lookupDamageTypeHolder(pLevel, ModDamageTypes.SCORCH), pOwner), 10); //deal 10 base fire damage per tick
        });
        ModMessages.sendToAllConnectedPlayers(p -> new BreathParticlesPacket(FlamesForColors.ORANGE, pOwner.getId()), pLevel);
    }

    @Override
    protected void stop(ServerLevel pLevel, Dragon pEntity, long pGameTime) {
        pEntity.getBrain().setMemoryWithExpiry(ModMemoryModuleTypes.DRAGON_FIRE_BREATH_COOLDOWN.get(), Unit.INSTANCE, 100);
    }

    @Override
    protected boolean canStillUse(ServerLevel pLevel, Dragon pEntity, long pGameTime) {
        return true;
    }
}
