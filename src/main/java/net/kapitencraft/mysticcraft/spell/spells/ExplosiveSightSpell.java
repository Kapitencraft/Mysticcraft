package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.util.Color;
import net.kapitencraft.mysticcraft.client.particle.options.CircleParticleOptions;
import net.kapitencraft.mysticcraft.helpers.ParticleHelper;
import net.kapitencraft.mysticcraft.misc.content.mana.ManaAOE;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContextParams;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;

public class ExplosiveSightSpell implements Spell {

    @Override
    public void cast(SpellCastContext context) {
        LivingEntity caster = context.getCaster();
        BlockPos pos = context.getParamOrThrow(SpellCastContextParams.ORIGIN_BLOCK);
        Explosion explosion = new Explosion(caster.level(), caster, pos.getX(), pos.getY(), pos.getZ(), 10, false, Explosion.BlockInteraction.DESTROY_WITH_DECAY);
        explosion.explode();
        explosion.finalizeExplosion(true);
        Vec3 vec3 = context.getParamOrThrow(SpellCastContextParams.ORIGIN);
        MathHelper.moveTowards(vec3, caster.getEyePosition(), 3, false);
        ManaAOE.execute(caster, this, 0.2f, 10, 5);
        ParticleHelper.sendParticles(caster.level(), new CircleParticleOptions(new Color(1, 0, 0, 1), 31, 6), true, vec3.x, vec3.y, vec3.z, 1, 0, 0, 0, 0);

    }

    @Override
    public double manaCost() {
        return 150;
    }

    @Override
    public Type getType() {
        return Type.RELEASE;
    }

    @Override
    public int getCooldownTime() {
        return 600;
    }

    @Override
    public boolean canApply(Item item) {
        return true;
    }
}
