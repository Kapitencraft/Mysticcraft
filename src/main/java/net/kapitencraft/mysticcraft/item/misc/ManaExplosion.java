package net.kapitencraft.mysticcraft.item.misc;

import net.kapitencraft.mysticcraft.misc.damage_source.SpellDamageSource;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ManaExplosion extends ModExplosion {
    @Override
    Explosion.BlockInteraction blockInteraction() {
        return Explosion.BlockInteraction.KEEP;
    }

    public ManaExplosion(Level level, @NotNull Entity source, double x, double y, double z, float radius, Spell spell) {
        super(level, source, x, y, z, radius);
        this.setDamageSource(SpellDamageSource.createExplosion(source, spell));
    }

    @Override
    protected boolean shouldSendDamageValue() {
        return true;
    }
}
