package net.kapitencraft.mysticcraft.mob_effects;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DisplacementEffect extends MobEffect {
    private static final Random RANDOM = new Random();

    public DisplacementEffect() {
        super(MobEffectCategory.HARMFUL, 0x8F00FF);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return RANDOM.nextDouble() < .01;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
        teleport(pLivingEntity);
    }

    private static void teleport(LivingEntity living) {
        Level level = living.level();
        double d0 = living.getX();
        double d1 = living.getY();
        double d2 = living.getZ();

        for(int i = 0; i < 16; ++i) {
            double d3 = living.getX() + (living.getRandom().nextDouble() - 0.5D) * 16.0D;
            double d4 = Mth.clamp(living.getY() + (double)(living.getRandom().nextInt(16) - 8), level.getMinBuildHeight(), level.getMinBuildHeight() + ((ServerLevel)level).getLogicalHeight() - 1);
            double d5 = living.getZ() + (living.getRandom().nextDouble() - 0.5D) * 16.0D;
            if (living.isPassenger()) {
                living.stopRiding();
            }

            Vec3 vec3 = living.position();
            level.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(living));
            EntityTeleportEvent.ChorusFruit event = ForgeEventFactory.onChorusFruitTeleport(living, d3, d4, d5);
            if (event.isCanceled()) return;
            if (living.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
                SoundEvent soundevent = living instanceof Fox ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
                level.playSound(null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                living.playSound(soundevent, 1.0F, 1.0F);
                break;
            }
        }

    }
}
