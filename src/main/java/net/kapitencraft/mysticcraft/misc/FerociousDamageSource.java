package net.kapitencraft.mysticcraft.misc;

import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;

public class FerociousDamageSource extends EntityDamageSource {
    public final  double ferocity;
    public final float damage;

    public FerociousDamageSource(String msg, Entity attacker, Double ferocity, float damage) {
        super(msg, attacker);
        this.ferocity = ferocity;
        this.damage = damage;
    }
}
