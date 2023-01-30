package net.kapitencraft.mysticcraft.misc.damage_source;

import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;

public class AbilityDamageSource extends EntityDamageSource implements IAbilitySource {
    private final float intScaling;
    private final String spellName;
    public AbilityDamageSource(Entity p_19395_, float intScaling, String spellName) {
        super("ability", p_19395_);
        this.intScaling = intScaling;
        this.spellName = spellName;
    }

    public float getScaling() {
        return intScaling;
    }

    public String getSpellType() {
        return spellName;
    }
}
