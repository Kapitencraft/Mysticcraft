package net.kapitencraft.mysticcraft.mob_effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.raid.Raider;

import java.util.List;

public class HeroOfThePillage extends MobEffect {
    public HeroOfThePillage() {
        super(MobEffectCategory.NEUTRAL, 1);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int p_19468_) {
        List<Raider> raiders = livingEntity.level.getEntitiesOfClass(Raider.class, livingEntity.getBoundingBox().inflate(15));
        for (Raider raider: raiders) {
            raider.setAggressive(false);
        }
    }
}
