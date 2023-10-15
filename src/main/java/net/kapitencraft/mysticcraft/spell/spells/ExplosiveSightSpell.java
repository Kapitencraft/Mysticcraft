package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.client.particle.CircleParticleOptions;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.ParticleHelper;
import net.kapitencraft.mysticcraft.item.misc.ManaExplosion;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ExplosiveSightSpell {

    private static final Component[] description = new Component[] {Component.literal("Explodes in a range of 150")};
    public static void execute(LivingEntity user, ItemStack ignoredStack) {
        ArrayList<Vec3> sight = MathHelper.lineOfSight(user, 150, 0.5);
        for (Vec3 vec3 : sight) {
            BlockPos pos = new BlockPos(vec3);
            if (user.level.getBlockState(pos).canOcclude() && !user.level.isClientSide()) {
                ManaExplosion manaExplosion = new ManaExplosion(user.level, user, pos.getX(), pos.getY(), pos.getZ(), 5, 0.2f, Spells.EXPLOSIVE_SIGHT.REGISTRY_NAME);
                manaExplosion.completeExplode();
                ParticleHelper.sendParticles(user.level, new CircleParticleOptions(new Vector3f(1, 0, 0), 6, 0.8), true, pos.getX(), pos.getY(), pos.getZ(), 1, 0, 0, 0, 0);
                break;
            }
        }
    }

    public static List<Component> getDescription() {
        return List.of(description);
    }
}
