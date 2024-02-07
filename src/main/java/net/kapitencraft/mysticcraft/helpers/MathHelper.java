package net.kapitencraft.mysticcraft.helpers;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.api.Reference;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MathHelper {

    public static double round(double no, int num) {
        return  Math.floor(no * Math.pow(10, num)) / (Math.pow(10, num));
    }

    public static double defRound(double no) {
        return round(no, 2);
    }

    public static Vector3f color(int r, int g, int b) {
        return new Vector3f(r / 255f, g / 255f, b / 255f);
    }

    public static float calculateDamage(float damage, double armorValue, double armorToughnessValue) {
        double f = MysticcraftMod.DAMAGE_CALCULATION_VALUE - armorToughnessValue / 4.0F;
        double defencePercentage = armorValue / (armorValue + f);
        return (float) (damage * (1f - defencePercentage));
    }

    public static Vec3 getHandHoldingItemAngle(HumanoidArm arm, Entity entity) {
        return entity.position().add(entity.calculateViewVector(0.0F, entity.getYRot() + (float)(arm == HumanoidArm.RIGHT ? 80 : -80)).scale(0.5D));
    }

    public static Vec3 rotateHorizontalVec(Vec3 source, Vec3 target, int angle) {
        double x = (target.x - source.x) * Math.cos(angle) - (target.y - source.y) * Math.sin(angle) + source.x;
        double z = (target.x - source.x) * Math.sin(angle) + (target.y - source.y) * Math.cos(angle) + source.y;
        return new Vec3(x, 0, z);
    }


    public static <T extends Entity> List<T> getEntitiesAround(Class<T> tClass, Entity source, double range) {
        Level level = source.getLevel();
        return getEntitiesAround(tClass, level, source.getBoundingBox(), range);
    }

    public static void add(Supplier<Integer> getter, Consumer<Integer> setter, int change) {
        setter.accept(getter.get() + change);
    }

    public static void up1(Reference<Integer> reference) {
        add(reference::getIntValue, reference::setValue, 1);
    }

    public static void mul(Supplier<Integer> getter, Consumer<Integer> setter, int mul) {
        setter.accept(getter.get() * mul);
    }

    public static void mul(Supplier<Double> getter, Consumer<Double> setter, double mul) {
        setter.accept(getter.get() * mul);
    }

    public static void mul(Supplier<Float> getter, Consumer<Float> setter, float mul) {
        setter.accept(getter.get() * mul);
    }


    public static ArrayList<Vec3> lineOfSight(Entity entity, double range, double scaling) {
        return lineOfSight(entity.getRotationVector(), entity.getEyePosition(), range, scaling);
    }

    public static ArrayList<Vec3> lineOfSight(Vec2 vec, Vec3 pos, double range, double scaling) {
        ArrayList<Vec3> line = new ArrayList<>();
        Vec3 vec3;
        for (double i = 0; i <= range; i+=scaling) {
            vec3 = calculateViewVector(vec.x, vec.y).scale(i).add(pos.x, pos.y, pos.z);
            line.add(vec3);
        }
        return line;
    }

    public static int count(Collection<Integer> collection) {
        int count = 0;
        for (Integer integer : collection) {
            count += integer;
        }
        return count;
    }

    public static int getHighest(Collection<Integer> collection) {
        int value = 0;
        for (int t : collection) {
            if (t > value) {
                value = t;
            }
        }
        return value;
    }

    public static List<BlockPos> makeLine(BlockPos a, BlockPos b, LineSize size) {
        List<BlockPos> list = new ArrayList<>();
        BlockPos diff = b.subtract(a);
        double horizontal = Mth.sqrt((diff.getX() * diff.getX()) + (diff.getZ() * diff.getZ()) + (diff.getY() * diff.getY()));
        int numPoints = (int) (size == LineSize.THIN ? horizontal * 20 : horizontal * 50);
        MiscHelper.repeat(numPoints, integer -> {
            double t = integer / (numPoints - 1.);
            list.add(makeLinePos(t, a, diff));
        });
        return list;
    }

    public static float makePercentage(int value, int maxValue) {
        return value * 1f / maxValue;
    }

    public static double makePercentage(double value, double maxValue) {
        return value / maxValue;
    }

    public static void forCube(BlockPos cube, Consumer<BlockPos> consumer) {
        MiscHelper.repeat(cube.getX(), integer -> MiscHelper.repeat(cube.getY(), integer1 -> MiscHelper.repeat(cube.getZ(), integer2 -> {
            consumer.accept(new BlockPos(integer, integer1, integer2));
        })));
    }

    public static Stream<BlockPos> between(BlockPos start, BlockPos end) {
        List<BlockPos> pos = new ArrayList<>();
        for (int x = Math.min(start.getX(), end.getX()); x < Math.max(start.getX(), end.getX()); x++) {
            for (int y = Math.min(start.getY(), end.getY()); y < Math.max(start.getY(), end.getY()); y++) {
                for (int z = Math.min(start.getZ(), end.getZ()); z < Math.max(start.getZ(), end.getZ()); z++) {
                    pos.add(new BlockPos(x, y, z));
                }
            }
        }
        return pos.stream();
    }

    private static BlockPos makeLinePos(double t, BlockPos a, BlockPos diff) {
        return new BlockPos(a.getX() + diff.getX() * t, a.getY() + diff.getY() * t, a.getZ() + diff.getZ() * t);
    }

    public enum LineSize {
        THIN,
        THICK
    }

    public static Vec3 moveTowards(Vec3 source, Vec3 target, double range, boolean percentage) {
        Vec3 change = source.subtract(target);
        double dist = source.distanceTo(target);
        return percentage ? setLength(change, dist * range) : setLength(change, range);
    }

    public static <T> T pickRandom(List<T> list) {
        return list.get(Mth.nextInt(MysticcraftMod.RANDOM_SOURCE, 0, list.size() - 1));
    }

    public static boolean chance(double chance, @Nullable Entity entity) {
        if (entity instanceof LivingEntity living) {
            return chance(chance, living);
        } else {
            return chance(chance, null);
        }
    }

    public static boolean chance(double chance, @Nullable LivingEntity living) {
        return Math.random() <= chance * (living != null ? (1 + living.getAttributeValue(Attributes.LUCK) / 100) : 1);
    }

    public static int cooldown(LivingEntity living, int defaultTime) {
        return (int) (defaultTime * (1 - living.getAttributeValue(ModAttributes.COOLDOWN_REDUCTION.get()) / 100));
    }

    public static <T extends Entity> List<T> getEntitiesAround(Class<T> tClass, Level level, AABB source, double range) {
        return level.getEntitiesOfClass(tClass, source.inflate(range));
    }

    public static <T extends Entity> T getClosestEntity(Class<T> tClass, Entity source, double range) {
        List<T> entities = getEntitiesAround(tClass, source, range);
        return entities.stream().filter(t -> t.is(source)).sorted(Comparator.comparingDouble(value -> value.distanceTo(source))).toList().get(0);
    }

    public static LivingEntity getClosestLiving(Entity source, double range) {
        return getClosestEntity(LivingEntity.class, source, range);
    }

    public static List<LivingEntity> getLivingAround(Entity source, double range) {
        return getEntitiesAround(LivingEntity.class, source, range);
    }

    public static Vec3 calculateViewVector(float horizontalHeightXAxis, float verticalYAxis) {
        float f = horizontalHeightXAxis * ((float)Math.PI / 180F);
        float f1 = -verticalYAxis * ((float)Math.PI / 180F);
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
                Vec3 apexToTarget = sourcePos.subtract(entity.position());
                boolean isInInfiniteCone = apexToTarget.dot(axisVec) / apexToTarget.length() / axisVec.length() > Mth.cos(halfSpan);
                if (isInInfiniteCone && apexToTarget.dot(axisVec) / axisVec.length() < axisVec.length()) {
                    list.add(entity);
                }
            }
            return list;
        }
        throw new IllegalArgumentException("range should be greater than 0");
    }

    public static List<Entity> getAllEntitiesInsideCylinder(float radius, Vec3 sourcePos, Vec2 rot, double range, Level level) {
        List<Entity> toReturn = new ArrayList<>();
        ArrayList<Vec3> lineOfSight = lineOfSight(rot, sourcePos, range, 0.1);
        lineOfSight.forEach(vec3 -> {
            AABB aabb = new AABB(vec3.add(radius, radius, radius), vec3.subtract(radius, radius, radius));
            List<Entity> entities = level.getEntitiesOfClass(Entity.class, aabb, entity -> vec3.distanceTo(entity.position()) < radius);
            toReturn.addAll(entities.stream().filter(entity -> !toReturn.contains(entity)).toList());
        });
        return toReturn;
    }

    public static Vec2 createTargetRotation(Entity source, Entity target) {
        return createTargetRotationFromPos(source.position(), target.position());
    }

    public static Vec2 createTargetRotationFromPos(Vec3 source, Vec3 target) {
        double d0 = target.x - source.x;
        double d1 = target.y - source.y;
        double d2 = target.z - source.z;
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        return new Vec2(Mth.wrapDegrees((float)(-(Mth.atan2(d1, d3) * (double)(180F / (float)Math.PI)))), Mth.wrapDegrees((float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F));
    }

    public static Vec2 createTargetRotationFromEyeHeight(Entity source, Entity target) {
        return createTargetRotationFromPos(source.getEyePosition(), target.getEyePosition());
    }

    public static boolean isBehind(Entity source, Entity target) {
        Vec3 vec32 = source.position();
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

    public static int RGBtoInt(Vector3i in) {
        int r = in.x;
        int g = in.y;
        int b = in.z;
        return RGBtoInt(r, g, b);
    }

    public static int RGBtoInt(Vector3f in) {
        return RGBtoInt(fromFloat(in, 255));
    }

    public static int RGBtoInt(int r, int g, int b) {
        int returnable = (r << 8) + g;
        return (returnable << 8) + b;
    }

    public static int RGBtoInt(float r, float g, float b) {
        return RGBtoInt(new Vector3f(r, g, b));
    }
    public static Vector3i fromFloat(Vector3f floatValue, int mul) {
        return new Vector3i((int) (floatValue.x * mul), (int) (floatValue.y * mul), (int) (floatValue.z * mul));
    }
}