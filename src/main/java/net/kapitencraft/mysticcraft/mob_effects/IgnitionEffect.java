package net.kapitencraft.mysticcraft.mob_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class IgnitionEffect extends MobEffect {
    public IgnitionEffect() {
        super(MobEffectCategory.HARMFUL, -52480);
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity living, int i) {
        living.setSecondsOnFire(i);
    }
}
