package net.kapitencraft.mysticcraft.mob_effects;

import net.kapitencraft.mysticcraft.data_gen.ModDamageTypes;
import net.kapitencraft.mysticcraft.registry.ModMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.jetbrains.annotations.NotNull;

public class NumbnessEffect extends MobEffect {
    public static final String NUMBNESS_ID = "numbnessAmount";
    public NumbnessEffect() {
        super(MobEffectCategory.HARMFUL, -10092442);
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }

    @Override
    public void removeAttributeModifiers(LivingEntity living, @NotNull AttributeMap attributeMap, int amplifier) {
        MobEffectInstance numbness = living.getEffect(ModMobEffects.NUMBNESS.get());
        if (numbness != null && numbness.getDuration() < 1) {
            living.hurt(living.damageSources().source(ModDamageTypes.NUMBNESS), living.getPersistentData().getFloat(NUMBNESS_ID));
            living.getPersistentData().putFloat(NUMBNESS_ID, 0);
        }
        super.removeAttributeModifiers(living, attributeMap, amplifier);
    }
}
