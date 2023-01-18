package net.kapitencraft.mysticcraft.misc.damage_source;

import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;

public class AbilityDamageSource extends EntityDamageSource implements IAbilitySource {
    private final float intScaling;
    public AbilityDamageSource(Entity p_19395_, float intScaling) {
        super("ability", p_19395_);
        this.intScaling = intScaling;
    }

    public float getScaling() {
        return intScaling;
    }
}
