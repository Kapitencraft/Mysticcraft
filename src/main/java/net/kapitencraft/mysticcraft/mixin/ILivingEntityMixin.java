package net.kapitencraft.mysticcraft.mixin;

import net.minecraft.world.damagesource.DamageSource;

public interface ILivingEntityMixin {
    float getDamageAfterArmorAbsorb(DamageSource source, float value);
}