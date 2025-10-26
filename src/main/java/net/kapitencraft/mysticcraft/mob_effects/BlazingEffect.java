package net.kapitencraft.mysticcraft.mob_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class BlazingEffect extends MobEffect {
    public BlazingEffect() {
        super(MobEffectCategory.HARMFUL, -52480);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity living, int i) {
        living.setRemainingFireTicks(i * 20);
        return true;
    }
}
