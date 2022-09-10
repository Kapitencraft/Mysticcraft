package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CrystalWarpSpell extends Spell {

    public CrystalWarpSpell() {
        super(300, "crystalwarp", Spells.RELEASE);
    }

    @Override
    public void execute(Entity user, ItemStack stack) {
        ArrayList<Vec3> sight = MISCTools.LineOfSight(user, 10, 0.1);
        for (Vec3 vec3 : sight) {
            BlockPos pos = new BlockPos(vec3);
            if (!user.level.getBlockState(pos).canOcclude()) {
                int loc = sight.indexOf(vec3);
                ArrayList<Vec3> invsight = MISCTools.invertList(sight);
                for (int i = 0; i < invsight.indexOf(vec3); i++) {
                    Vec3 mem = new Vec3(invsight.get(i).x, invsight.get(i).y + 1, invsight.get(i).z);
                    if (user.level.getBlockState(new BlockPos(mem)).canOcclude()) {
                        user.teleportTo(invsight.get(i).x, invsight.get(i).y, invsight.get(i).z);
                    }

                }
            }
        }
        if (user.level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.ASH, (user.getX()), (user.getY()), (user.getZ()), 50, 4, 4, 3, 0.2);
        }
        final Vec3 center = new Vec3((user.getX()), (user.getY()), (user.getZ()));
        List<Entity> entfound = user.level.getEntitiesOfClass(Entity.class, new AABB(center, center).inflate(6 / 2d), e -> true).stream()
                .sorted(Comparator.comparingDouble(entcnd -> entcnd.distanceToSqr(center))).collect(Collectors.toList());
        for (Entity entityiterator : entfound) {
            if (!(entityiterator == user)) {
                entityiterator.hurt(new EntityDamageSource("magic", user), 5);
            }
        }
    }
}
