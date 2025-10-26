package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.mysticcraft.network.packets.S2C.BreathParticlesPacket;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector3f;

import java.util.List;

public class FrostWindSpell extends Spell {
    protected FrostWindSpell() {
        super(0, 40, Type.HOLD, SpellTarget.SELF, null);
    }

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
            PacketDistributor.sendToPlayersInDimension(serverLevel, new BreathParticlesPacket(new DustParticleOptions(new Vector3f(0, .5f, 1f), 1), caster.getId()));
    }
    @Override
    public boolean canApply(Item item) {
        return true;
    }
}
