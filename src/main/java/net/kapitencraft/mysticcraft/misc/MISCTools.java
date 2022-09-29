package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.api.APITools;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sin;

public class MISCTools {
    record Rotation(String relative, int vecRot, int vecNum) {
    }
    public final Rotation EAST = new Rotation("x+", 90, 1);
    public final Rotation WEST = new Rotation("x-", 270,3);
    public final Rotation SOUTH = new Rotation("z+", 180, 2);
    public final Rotation NORTH = new Rotation("z-", 360, 4);

    public static ArrayList<Vec3> LineOfSight(Entity entity, double range, double scaling) {
        Vec3 vec3 = entity.position();
        Vec2 vec2 = entity.getRotationVector();
        double relativeX = 0;
        double relativeY;
        double relativeZ = 0;
        boolean flag = vec2.x < 0;
        double B = flag ? vec2.x * -1 : vec2.x;
        double A = 90;
        double yaw = (double) vec2.y;
        yaw += 180;
        double C = 180 - A - B;
        double dist = scaling;
        double b;
        double c;
        ArrayList<Vec3> line = new ArrayList<>();
        int vector = (int) Math.ceil(yaw / 90);
        double relativeYaw = yaw % 90;
        relativeYaw = relativeYaw < 0 ? relativeYaw * -1 : relativeYaw;
        while (range >= dist) {
            b = dist * sin(B) / sin(A);
            c = dist * sin(C) / sin(A);
            relativeY = flag ? b * -1 : b;
            C = 180 - A - relativeYaw;
            b = c * sin(B) / sin(A);
            c = c * sin(C) / sin(A);
            switch (vector) {
                case 1 : relativeX +=c;
                    if (yaw < 90) {
                        relativeZ -=b;
                    } else {
                        relativeZ +=b;
                    }
                case 2 : relativeZ +=c;
                    if (yaw < 90) {
                        relativeX +=b;
                    } else {
                        relativeX -=b;
                    }
                case 3 : relativeX -=c;
                    if (yaw < 270) {
                        relativeZ +=b;
                    } else {
                        relativeZ -=b;
                    }
                case 4 : relativeZ -=c;
                    if (yaw < 90) {
                        relativeX -=b;
                    } else {
                        relativeX +=b;
                    }
            }
            line.add(new Vec3(vec3.x + relativeX, vec3.y + relativeY, vec3.z + relativeZ));
            dist += scaling;
        }
        return line;
    }

    public static  <V> ArrayList<V> invertList(ArrayList<V> list) {
        ArrayList<V> out = new ArrayList<>();
        for (int i = list.size(); i >= 0; i--) {

            out.add(list.get((i - 1)));
        }
        return out;
    }

    public static  @Nullable LivingEntity getAttacker(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Projectile projectile) {
            if (projectile.getOwner() instanceof LivingEntity living) {
                return living;
            }
        } else if (event.getSource().getEntity() instanceof LivingEntity living) {
            return living;
        }
        return null;
    }

    public static ArmorStand createDamageIndicator(LivingEntity entity, double amount, String type) {
        ArmorStand dmgInc = new ArmorStand(entity.level, entity.getX() + Math.random() - 0.5, entity.getY() - 1, entity.getZ() + Math.random() - 0.5);
        dmgInc.setNoGravity(true);
        dmgInc.setInvisible(true);
        dmgInc.setInvulnerable(true);
        dmgInc.setBoundingBox(new AABB(0,0,0,0,0,0));
        dmgInc.setCustomNameVisible(true);
        dmgInc.setCustomName(Component.literal(String.valueOf(amount)).withStyle(type.equals("heal") ? ChatFormatting.GREEN : ChatFormatting.RED));
        dmgInc.getPersistentData().putBoolean("isDamageIndicator", true);
        dmgInc.getPersistentData().putInt("time", 0);
        entity.level.addFreshEntity(dmgInc);
        return dmgInc;
    }

    public static @Nullable LivingEntity[] sortLowestDistance(Entity source, List<LivingEntity> list) {
        if (list.isEmpty()) {
            return null;
        }
        LivingEntity[] ret = APITools.entityListToArray(list);
        if (ret.length == 1) {
            return ret;
        }
        boolean isDone = false;
        LivingEntity temp;
        double distance1;
        double distance2;
        int i = 0;
        while (!isDone) {
            do {
                distance1 = source.distanceTo(list.get(i));
                distance2 = source.distanceTo(list.get(i + 1));
                i++;
            } while (distance1 < distance2 && i < list.size() - 1);
            i--;
            temp = list.get(i);
            ret[i] = list.get(i + 1);
            ret[i + 1] = temp;
            for (int j = 0; j <= ret.length - 2; j++) {
                if (source.distanceTo(list.get(j)) > source.distanceTo(list.get(j + 1))) {
                    break;
                } else if (j == ret.length - 1) {
                    isDone = true;
                }
            }
        }
        return ret;
    }
}
