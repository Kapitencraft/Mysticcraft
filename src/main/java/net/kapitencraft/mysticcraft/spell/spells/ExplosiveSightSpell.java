package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.util.Color;
import net.kapitencraft.mysticcraft.client.particle.options.CircleParticleOptions;
import net.kapitencraft.mysticcraft.helpers.ParticleHelper;
import net.kapitencraft.mysticcraft.misc.content.mana.ManaAOE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ExplosiveSightSpell {

    private static final Component[] description = new Component[] {Component.literal("Explodes in a range of 150")};
    public static boolean execute(LivingEntity user, ItemStack ignoredStack) {
        Vec3 viewVec = user.calculateViewVector(user.getXRot(), user.getYRot());
        Vec3 end = viewVec.scale(150).add(user.getEyePosition());
        BlockHitResult result = user.level().clip(new ClipContext(viewVec.add(user.getEyePosition()), end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, user));
        BlockPos pos = result.getBlockPos();
        Explosion explosion = new Explosion(user.level(), user, pos.getX(), pos.getY(), pos.getZ(), 10, false, Explosion.BlockInteraction.DESTROY_WITH_DECAY);
        explosion.explode();
        explosion.finalizeExplosion(true);
        Vec3 vec3 = result.getLocation();
        MathHelper.moveTowards(vec3, user.getEyePosition(), 3, false);
        ManaAOE.execute(user, "explosive_sight", 0.2f, 10, 5);
        ParticleHelper.sendParticles(user.level(), new CircleParticleOptions(new Color(1, 0, 0, 1), 31, 6), true, vec3.x, vec3.y, vec3.z, 1, 0, 0, 0, 0);
        return true;
    }

    public static List<Component> getDescription() {
        return List.of(description);
    }
}
