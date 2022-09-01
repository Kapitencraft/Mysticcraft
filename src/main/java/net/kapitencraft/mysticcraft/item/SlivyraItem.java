package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class SlivyraItem extends ModdedSwordItem{
    public SlivyraItem(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int timeleft) {
        if (user.getAttributes().hasAttribute(ModAttributes.MANA.get())) {
            double mana = user.getAttributeValue(ModAttributes.MANA.get());
            double mana_cost = user.getAttributeValue(ModAttributes.MANA_COST.get());
            if (mana >= mana_cost) {
                mana -= mana_cost;
                user.getAttribute(ModAttributes.MANA.get()).setBaseValue(mana);
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
                final Vec3 _center = new Vec3((user.getX()), (user.getY()), (user.getZ()));
                List<Entity> _entfound = user.level.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream()
                        .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
                for (Entity entityiterator : _entfound) {
                    if (!(entityiterator == user)) {
                        entityiterator.hurt(DamageSource.FLY_INTO_WALL, 15);
                        entityiterator.setDeltaMovement(new Vec3((7 - (entityiterator.getX() - user.getX())),
                                (7 - (entityiterator.getY() - user.getY())), (7 - (entityiterator.getZ() - user.getZ()))));
                    }
                }
            }
        }
    }
}
