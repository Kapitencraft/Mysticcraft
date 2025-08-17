package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.cooldown.Cooldown;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.helpers.ParticleHelper;
import net.kapitencraft.kap_lib.util.Color;
import net.kapitencraft.mysticcraft.client.particle.options.CircleParticleOptions;
import net.kapitencraft.mysticcraft.misc.content.mana.ManaAOE;
import net.kapitencraft.mysticcraft.registry.ModCooldowns;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContextParams;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExplosiveSightSpell implements Spell {
    private static final SpellTarget<BlockState> TARGET = SpellTarget.Type.BLOCK.always();

    @Override
    public void cast(SpellCastContext context) {
        LivingEntity caster = context.getCaster();
        BlockPos pos = context.getParamOrThrow(SpellCastContextParams.TARGET_BLOCK);
        if (!caster.level().isClientSide()) {
            Explosion explosion = new Explosion(caster.level(), caster, pos.getX(), pos.getY(), pos.getZ(), 10, false, Explosion.BlockInteraction.DESTROY_WITH_DECAY);
            explosion.explode();
            explosion.finalizeExplosion(true);
            ManaAOE.execute(caster, this, 0.2f, 10, 5);
            Vec3 vec3 = pos.getCenter();
            ParticleHelper.sendParticles(caster.level(), new CircleParticleOptions(new Color(1, 0, 0, 1), 31, 6), true, vec3.x, vec3.y, vec3.z, 1, 0, 0, 0, 0);
            MiscHelper.shakeGround((ServerLevel) caster.level(), vec3, 4f, 3f, 2f);
        }
    }

    @Override
    public int castDuration() {
        return 90;
    }

    @Override
    public double manaCost() {
        return 150;
    }

    @Override
    public @NotNull Type getType() {
        return Type.RELEASE;
    }

    @Override
    public @NotNull SpellTarget<?> getTarget() {
        return TARGET;
    }

    @Override
    public @Nullable Cooldown getCooldown() {
        return ModCooldowns.EXPLOSIVE_SIGHT.get();
    }

    @Override
    public boolean canApply(Item item) {
        return true;
    }
}
