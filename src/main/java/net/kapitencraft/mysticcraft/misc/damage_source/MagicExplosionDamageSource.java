package net.kapitencraft.mysticcraft.misc.damage_source;

import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;

public class MagicExplosionDamageSource extends EntityDamageSource implements IAbilitySource {
    private final float scaling;
    private final String spellType;

    public MagicExplosionDamageSource(float scaling, String spellType, Entity entity) {
        super("magic.explosion", entity);
        this.scaling = scaling;
        this.spellType = spellType;
    }

    @Override
    public float getScaling() {
        return 0;
    }

    @Override
    public String getSpellType() {
        return null;
    }
}
