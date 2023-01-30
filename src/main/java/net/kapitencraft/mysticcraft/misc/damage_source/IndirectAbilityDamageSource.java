package net.kapitencraft.mysticcraft.misc.damage_source;

import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class IndirectAbilityDamageSource extends IndirectEntityDamageSource implements IAbilitySource {
    private final float intScaling;
    private final String spellName;
    public IndirectAbilityDamageSource(Entity p_19407_, @Nullable Entity p_19408_, float intScaling, String spellName) {
        super("ability", p_19407_, p_19408_);
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
