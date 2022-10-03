package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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
    public final String ABILITY_NAME = "Crystal Warp";

    private Component[] description = {Component.literal("Teleports you 10 blocks ahead"), Component.literal("and deals damage to all entities around")};

    public CrystalWarpSpell() {
        super(300, "crystal_warp", Spells.RELEASE);
    }

    @Override
    public void execute(Entity user, ItemStack stack) {
        ArrayList<Vec3> sight = MISCTools.LineOfSight(user, 10, 0.1);
        for (Vec3 vec3 : sight) {
            BlockPos pos = new BlockPos(vec3);
            if (!user.level.getBlockState(pos).canOcclude()) {
                ArrayList<Vec3> invSight = MISCTools.invertList(sight);
                for (int i = 0; i < invSight.indexOf(vec3); i++) {
                    Vec3 mem = new Vec3(invSight.get(i).x, invSight.get(i).y + 1, invSight.get(i).z);
                    if (user.level.getBlockState(new BlockPos(mem)).canOcclude()) {
                        user.teleportTo(invSight.get(i).x, invSight.get(i).y, invSight.get(i).z);
                    }

                }
            }
        }
        if (user.level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.ASH, (user.getX()), (user.getY()), (user.getZ()), 50, 4, 4, 3, 0.2);
        }
        final Vec3 center = new Vec3((user.getX()), (user.getY()), (user.getZ()));
        List<Entity> entFound = user.level.getEntitiesOfClass(Entity.class, new AABB(center, center).inflate(6 / 2d), e -> true).stream()
                .sorted(Comparator.comparingDouble(entCnd -> entCnd.distanceToSqr(center))).collect(Collectors.toList());
        for (Entity entityIterator : entFound) {
            if (!(entityIterator == user)) {
                entityIterator.hurt(new EntityDamageSource("magic", user), 5);
            }
        }
    }

    @Override
    public List<Component> getDescription() {
        return List.of(this.description);
    }

    @Override
    public String getName() {
        return ABILITY_NAME;
    }
}
