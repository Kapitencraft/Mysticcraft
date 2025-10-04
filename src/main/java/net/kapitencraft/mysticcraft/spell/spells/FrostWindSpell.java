package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.mysticcraft.network.ModMessages;
import net.kapitencraft.mysticcraft.network.packets.S2C.BreathParticlesPacket;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;

public class FrostWindSpell implements Spell {
    @Override
    public void cast(SpellCastContext context) throws SpellExecutionFailedException {
        LivingEntity caster = context.getCaster();
        List<LivingEntity> cone = MathHelper.getAllEntitiesInsideCone(LivingEntity.class, 15, context.getLevel() + 5, caster.getEyePosition(), caster.getRotationVector(), context.getWorld());
        cone.forEach(living -> {
            if (living == caster || living.isAlliedTo(caster)) return;
            living.invulnerableTime = 0;
            living.hurt(caster.damageSources().freeze(), 2);
        });
        if (caster.level() instanceof ServerLevel serverLevel)
            ModMessages.sendToAllConnectedPlayers(s -> new BreathParticlesPacket(new DustParticleOptions(new Vector3f(0, .5f, 1f), 1), caster.getId()), serverLevel);
    }

    @Override
    public double manaCost() {
        return 0;
    }

    @Override
    public int castDuration() {
        return 40;
    }

    @Override
    public @NotNull Type getType() {
        return Type.HOLD;
    }

    @Override
    public @NotNull SpellTarget<?> getTarget() {
        return SpellTarget.SELF;
    }

    @Override
    public boolean canApply(Item item) {
        return true;
    }
}
