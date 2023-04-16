package net.kapitencraft.mysticcraft.misc.utils;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class MathUtils {

    public static double round(double no, int num) {
        return Math.floor(no * (num * 10)) / (num * 10);
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

    private record ConeHelper(Vec3 pos, double num, boolean right, boolean up) {
    }

    public static Vec2 getTargetRotation(Entity source, Entity target) {
        double d0 = target.getX() - source.getX();
        double d1 = target.getY() - source.getY();
        double d2 = target.getZ() - source.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        return new Vec2(Mth.wrapDegrees((float)(-(Mth.atan2(d1, d3) * (double)(180F / (float)Math.PI)))), Mth.wrapDegrees((float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F));
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

    private static class MathFunction3D {
        public double x, y, z;

        public MathFunction3D(double x, double y, double z) {
            this.x = x; this.y = y; this.z = z;
        }

        public MathFunction3D(Vec3 vec3) {
            double vecSquare = vec3.length() * vec3.length();
            double xSquare = vec3.x * vec3.x;
            double ySquare = vec3.y * vec3.y;
            double zSquare = vec3.z * vec3.z;
            this.x = -Math.sqrt(vecSquare - ySquare - zSquare);
            this.y = -Math.sqrt(vecSquare - xSquare - zSquare);
            this.z = -Math.sqrt(vecSquare - ySquare - xSquare);
        }
    }
}