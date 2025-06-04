package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.dagger.ShadowDagger;
import net.kapitencraft.mysticcraft.registry.ModParticleTypes;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ShadowStepSpell implements Spell {

    private static boolean next(List<LivingEntity> list, LivingEntity user) {
        if (list.isEmpty()) return false;
        LivingEntity target = list.get(0);
        list.remove(0);
        teleportBehind(target, user);
        return true;
    }

    private static void teleportBehind(LivingEntity target, LivingEntity user) {
        Vec3 behind = target.getLookAngle().scale(-4);
        MiscHelper.teleport(user, target.position().add(behind));

        if (user instanceof ServerPlayer serverPlayer) {
            serverPlayer.lookAt(EntityAnchorArgument.Anchor.EYES, target, EntityAnchorArgument.Anchor.EYES);
        } else {
            user.lookAt(EntityAnchorArgument.Anchor.EYES, EntityAnchorArgument.Anchor.EYES.apply(target));
        }
        spawnParticles(user);
    }

    private static void spawnParticles(LivingEntity entity) {
        double d0 = -Mth.sin(entity.getYRot() * ((float)Math.PI / 180F));
        double d1 = Mth.cos(entity.getYRot() * ((float)Math.PI / 180F));
        if (entity.level() instanceof ServerLevel level) {
            level.sendParticles(ModParticleTypes.SHADOW_SWEEP.get(), entity.getX() + d0, entity.getY(0.5D), entity.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
        }
    }

    @Override
    public void cast(SpellCastContext context) {
        LivingEntity user = context.getCaster();
        List<LivingEntity> entities = MathHelper.getAllEntitiesInsideCone(LivingEntity.class, 15, 20, user.position(), user.getRotationVector(), user.level());
        List<LivingEntity> hit = new ArrayList<>();
        entities.stream().filter(living -> living != user).forEach(hit::add);
        if (next(hit, user)) MiscHelper.schedule(10, ()-> {
                    if (next(hit, user)) MiscHelper.schedule(10, ()-> next(hit, user));
                }
        );

    }

    @Override
    public double manaCost() {
        return 80;
    }

    @Override
    public Type getType() {
        return Type.RELEASE;
    }

    @Override
    public int getCooldownTime() {
        return 300;
    }

    @Override
    public boolean canApply(Item item) {
        return item instanceof ShadowDagger;
    }
}