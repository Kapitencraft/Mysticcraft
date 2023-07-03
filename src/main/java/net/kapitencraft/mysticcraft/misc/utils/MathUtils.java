package net.kapitencraft.mysticcraft.misc.utils;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.List;

public class MathUtils {

    public static double round(double no, int num) {
        return Math.floor(no * (num * 10)) / (num * 10);
    }

    public static <T extends Entity> List<T> getEntitiesAround(Class<T> tClass, Entity source, double range) {
        Level level = source.getLevel();
        return level.getEntitiesOfClass(tClass, source.getBoundingBox().inflate(range));
    }

    public static List<LivingEntity> getLivingAround(Entity source, double range) {
        return getEntitiesAround(LivingEntity.class, source, range);
    }

    public static Vec3 calculateViewVector(float horizontalAxis, float verticalAxis) {
        float f = horizontalAxis * ((float)Math.PI / 180F);
        float f1 = -verticalAxis * ((float)Math.PI / 180F);
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        float f4 = Mth.cos(f);
        float f5 = Mth.sin(f);
        return setLength(new Vec3(f3 * f4, -f5, f2 * f4), 1);
    }

    public static List<Entity> getAllEntitiesInsideCone(float span, double range, Vec3 sourcePos, Vec2 sourceRot, Level level) {
        if (range > 0) {
            AABB testAABB = new AABB(sourcePos.subtract(2, 2, 2), sourcePos.add(2, 2, 2)).inflate(range/2);
            List<Entity> list = new ArrayList<>();
            Vec3 endRight = calculateViewVector(sourceRot.x, sourceRot.y - span).scale(range);
            Vec3 endMid = calculateViewVector(sourceRot.x, sourceRot.y + span).scale(range).subtract(endRight).scale(0.5).add(endRight);
            Vec3 axisVec = sourcePos.subtract(endMid);
            float halfSpan = span / 2;
            for (Entity entity : level.getEntitiesOfClass(Entity.class, testAABB)) {
                Vec3 apexToTarget = sourcePos.subtract(getPosition(entity));
                boolean isInInfiniteCone = apexToTarget.dot(axisVec) / apexToTarget.length() / axisVec.length() > Mth.cos(halfSpan);
                if (isInInfiniteCone && apexToTarget.dot(axisVec) / axisVec.length() < axisVec.length()) {
                    list.add(entity);
                }
            }
            return list;
        }
        throw new IllegalArgumentException("range should be higher thant 0");
    }

    public static Vec3 getPosition(Entity entity) {
        return new Vec3(entity.getX(), entity.getY(), entity.getZ());
    }
    public static Vec3 getEyePosition(Entity entity) {return getPosition(entity).add(0, entity.getEyeHeight(), 0);}

    public static Vec2 createTargetRotation(Entity source, Entity target) {
        return createTargetRotationFromPos(getPosition(source), getPosition(target));
    }

    public static Vec2 createTargetRotationFromPos(Vec3 source, Vec3 target) {
        double d0 = target.x - source.x;
        double d1 = target.y - source.y;
        double d2 = target.z - source.z;
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        return new Vec2(Mth.wrapDegrees((float)(-(Mth.atan2(d1, d3) * (double)(180F / (float)Math.PI)))), Mth.wrapDegrees((float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F));
    }

    public static Vec2 createTargetRotationFromEyeHeight(Entity source, Entity target) {
        return createTargetRotationFromPos(getEyePosition(source), getEyePosition(target));
    }

    public static boolean isBehind(Entity source, Entity target) {
        Vec3 vec32 = getPosition(source);
        Vec3 vec31 = vec32.vectorTo(target.position()).normalize();
        vec31 = new Vec3(vec31.x, 0.0D, vec31.z);
        return !(vec31.dot(target.getViewVector(1)) < 0.0D);
    }

    public static Vec3 minimiseLength(Vec3 source, double minimum) {
        if (source.length() > minimum) {
            return source;
        } else {
            double scale = minimum / source.length();
            return source.scale(scale);
        }
    }

    public static Vec3 maximiseLength(Vec3 source, double maximum) {
        if (source.length() < maximum) {
            return source;
        } else {
            double scale = maximum / source.length();
            return source.scale(scale);
        }
    }

    public static Vec3 setLength(Vec3 source, double value) {
        if (source.length() > value) {
            return maximiseLength(source, value);
        }
        return minimiseLength(source, value);
    }

    public static Vec3 getRandomOffsetForPos(Entity target, double dist, double maxOffset) {
        maxOffset *=2;
        RandomSource source = RandomSource.create();
        Vec2 rot = target.getRotationVector();
        Vec3 targetPos = calculateViewVector(rot.x, rot.y).scale(dist);
        Vec3 secPos = removeByScale(calculateViewVector(rot.x - 90, rot.y).scale(maxOffset * source.nextFloat()), 0.5);
        Vec3 thirdPos = removeByScale(calculateViewVector(rot.x, rot.y - 90).scale(maxOffset * source.nextFloat()), 0.5);
        return targetPos.add(secPos).add(thirdPos);
    }

    public static Vec3 removeByScale(Vec3 vec3, double scale) {
        double x = vec3.x;
        double y = vec3.y;
        double z = vec3.z;
        double halfX = (x - (x * scale));
        double halfY = (y - (y * scale));
        double halfZ = (z - (z * scale));
        return new Vec3(halfX, halfY, halfZ);
    }

    public static Vector3i intToRGB(int in) {
        int r = in >> 16 & 255;
        int g = in >> 8 & 255;
        int b = in & 255;
        return new Vector3i(r, g, b);
    }
}