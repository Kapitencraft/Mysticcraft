package net.kapitencraft.mysticcraft.item.misc;

import net.kapitencraft.mysticcraft.misc.damage_source.AbilityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ManaExplosion extends ModExplosion {
    private final float intScaling;
    private final String spellName;
    @Override
    Explosion.BlockInteraction blockInteraction() {
        return Explosion.BlockInteraction.KEEP;
    }

    public ManaExplosion(Level level, @Nullable Entity source, double x, double y, double z, float radius, float intScaling, String spellName) {
        super(level, source, x, y, z, radius);
        this.intScaling = intScaling;
        this.spellName = spellName;
        this.setDamageSource(new AbilityDamageSource(source, intScaling, spellName));
    }

    @Override
    protected boolean shouldSendDamageValue() {
        return true;
    }
}
