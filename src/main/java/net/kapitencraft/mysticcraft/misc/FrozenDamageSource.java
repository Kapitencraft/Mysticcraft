package net.kapitencraft.mysticcraft.misc;

import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;

public class FrozenDamageSource extends EntityDamageSource {
    public FrozenDamageSource(Entity p_19395_) {
        super("frozen_from_entity", p_19395_);
    }
}
