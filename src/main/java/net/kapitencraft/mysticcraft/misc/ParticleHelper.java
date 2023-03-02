package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

public class ParticleHelper {
    private static final String COOLDOWN_ID = "cooldown";
    private static final String TICKS_ALIVE_ID = "ticksAlive";
    private static final String MAX_TICKS_ALIVE_ID = "maxTicksAlive";
    private static final String Y_OFFSET_ID = "YOffset";
    private static final String CURRENT_ROTATION_ID = "curRotation";
    private static final String MAX_HEIGHT_ID = "maxHeight";
    private static final String ROTATION_PER_TICK_ID = "rotPerTick";
    private static final String PARTICLE_TYPE_ID = "particleType";
    private static final ParticleHelperQueueWorker worker = new ParticleHelperQueueWorker();
    public static void tickHelper(Entity living) {
        if (!worker.hasTarget(living.getUUID())) {
            loadAllHelpers(living);
        }
        worker.tick(living.tickCount, living.getUUID());
    }

    public static void clearAllHelpers(@Nullable String helperReason, LivingEntity target) {
        worker.remove(target.getUUID(), helperReason);
        MysticcraftMod.sendInfo("clearing Helpers: " + (helperReason == null ? "null" : helperReason));
        CompoundTag tag = target.getPersistentData();
        int i = 0;
        while (tag.contains("ParticleHelper" + i, 10)) {
            if (helperReason == null || ((CompoundTag) tag.get("ParticleHelper" + i)).getString("helperReason").equals(helperReason)) {
                tag.remove("ParticleHelper" + i);
            }
        }

    }

    public static CompoundTag createArrowHeadProperties(int maxSteps, int maxParticle, SimpleParticleType particleType, SimpleParticleType particleType1) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("maxSteps", maxSteps);
        tag.putInt("maxParticle", maxParticle);
        tag.putString("type1", toTag(particleType));
        tag.putString("type2", toTag(particleType1));
        return tag;
    }

    public static CompoundTag createOrbitProperties(int cooldown, int maxTicksAlive, float initRotation, float maxHeight, float rotPerTick, ParticleType<SimpleParticleType> particleType) {
        CompoundTag tag = new CompoundTag();
        tag.putInt(COOLDOWN_ID, cooldown);
        tag.putInt(TICKS_ALIVE_ID, 0);
        tag.putInt(MAX_TICKS_ALIVE_ID, maxTicksAlive);
        tag.putFloat(Y_OFFSET_ID, 0);
        tag.putFloat(CURRENT_ROTATION_ID, initRotation);
        tag.putFloat(MAX_HEIGHT_ID, maxHeight);
        tag.putFloat(ROTATION_PER_TICK_ID, rotPerTick);
        tag.putString(PARTICLE_TYPE_ID, toTag((SimpleParticleType) particleType));
        return tag;
    }

    public static String toTag(SimpleParticleType type) {
        ResourceLocation location = BuiltInRegistries.PARTICLE_TYPE.getKey(type);
        return (location == null ? BuiltInRegistries.PARTICLE_TYPE.getKey(ParticleTypes.ANGRY_VILLAGER) : location).toString();
    }

    private static void loadAllHelpers(Entity target) {
        CompoundTag tag = target.getPersistentData();
        int i = 0;
        while (tag.contains("ParticleHelper" + i, 10)) {
            worker.add(target.getUUID(), of((CompoundTag) tag.get("ParticleHelper" + i), target, i));
        }
    }

    private boolean isRemoved = false;
    private final String helperReason;
    private final CompoundTag properties;
    private final int ticksPerCycle;
    private final float heightChange;
    private int curTagID, currentTick = 0;
    private final Entity target;
    private final Type type;

    public void remove(boolean flag) {
        this.isRemoved = flag;
    }

    public static ParticleHelper createWithTargetHeight(String helperReason, Entity target, Type type, CompoundTag properties) {
        properties.putFloat(MAX_HEIGHT_ID, target.getBbHeight());
        return new ParticleHelper(helperReason, target, type, properties);
    }

    public ParticleHelper(String helperReason, Entity target, Type type, CompoundTag properties) {
        MysticcraftMod.sendInfo("created a new ParticleHelper");
        this.properties = properties;
        this.target = target;
        this.curTagID = getNextFreeHelperSlot();
        this.helperReason = helperReason;
        this.ticksPerCycle = (int) (360 / this.properties.getFloat(ROTATION_PER_TICK_ID));
        this.heightChange = getHeightChangePerTick();
        this.type = type;
        this.save();
        worker.add(target.getUUID(), this);
    }

    public String getHelperReason() {
        return helperReason;
    }

    private float getHeightChangePerTick() {
        return this.properties.getFloat(MAX_HEIGHT_ID) / (this.properties.getFloat(ROTATION_PER_TICK_ID) / 2f);
    }

    private ParticleHelper(@NotNull CompoundTag tag, Entity target, int curTagID) {
        this.curTagID = curTagID;
        this.properties = (CompoundTag) tag.get("properties");
        this.helperReason = tag.getString("helperReason");
        this.currentTick = tag.getInt("currentTick");
        if (this.properties != null) {
            this.ticksPerCycle = (int) (360 / this.properties.getFloat(ROTATION_PER_TICK_ID));
        } else {
            throw new IllegalStateException("properties shouldn't be null");
        }
        this.heightChange = getHeightChangePerTick();
        this.target = target;
        this.type = Type.getFromString(tag.getString("type"));
        worker.add(target.getUUID(), this);
    }

    private int getNextFreeHelperSlot() {
        CompoundTag targetTag = target.getPersistentData();
        int tagNum = 0;
        while (targetTag.contains("ParticleHelper" + tagNum, 10)) {
            tagNum++;
        }
        return tagNum;
    }

    private CompoundTag updateSaveData(boolean shouldSave) {
        CompoundTag tag = new CompoundTag();
        tag.putString("helperReason", helperReason);
        tag.putString("type", type.getRegistryName());
        tag.putInt("currentTick", currentTick);
        tag.put("properties", properties);
        if (shouldSave) {
            target.getPersistentData().put("ParticleHelper" + curTagID, tag);
        }
        return tag;
    }

    private void save() {
        if (this.curTagID > getNextFreeHelperSlot()) {
            updateSave(this.curTagID);
        } else {
            updateSaveData(true);
        }
    }

    private void initSave() {
        CompoundTag tag = updateSaveData(false);
        MysticcraftMod.sendInfo("Saving ParticleHelper to slot: " + this.curTagID + "(" + "ParticleHelper" + getNextFreeHelperSlot() + ")");
        this.target.getPersistentData().put("ParticleHelper" + this.curTagID, tag);
    }

    private void updateSave(int oldLoc) {
        target.getPersistentData().remove("ParticleHelper" + oldLoc);
        this.curTagID = getNextFreeHelperSlot();
        initSave();
    }

    public static ParticleHelper of(@NotNull CompoundTag tag, Entity living, int tagID) {
        return new ParticleHelper(tag, living, tagID);
    }

    public void tick(int ticks) {
        int cooldown = this.properties.getInt(COOLDOWN_ID);
        if (!(MISCTools.increaseIntegerTagValue(this.properties, TICKS_ALIVE_ID, 1) > this.properties.getInt(MAX_TICKS_ALIVE_ID) && this.target.isRemoved()) && (cooldown == 0 || ticks % cooldown == 0) && !this.isRemoved) {
            Level level = this.target.level;
            if (this.currentTick++ >= this.ticksPerCycle) {
                this.currentTick = 0;
            }
            if (this.type == Type.ORBIT) {
                tickOrbit(level);
            } else if (this.type == Type.ARROW_HEAD) {
                tickArrowHead(level);
            }
            this.save();
        }
    }

    private void tickOrbit(Level level) {
        if (currentTick >= (ticksPerCycle / 2)) {
            MISCTools.increaseFloatTagValue(this.properties, Y_OFFSET_ID, -this.heightChange);
        } else {
            MISCTools.increaseFloatTagValue(this.properties, Y_OFFSET_ID, this.heightChange);
        }
        MISCTools.increaseFloatTagValue(this.properties, CURRENT_ROTATION_ID, this.properties.getFloat(ROTATION_PER_TICK_ID));
        Vec3 targetPos = MISCTools.calculateViewVector(0, this.properties.getFloat(CURRENT_ROTATION_ID)).add(target.getX(), target.getY() +  this.properties.getFloat(Y_OFFSET_ID), target.getZ());
        MISCTools.sendParticles(level, getFromTag(this.properties.getString("particleType")), false, targetPos, 2, 0,0, 0, 0);
        }

    public static SimpleParticleType getFromTag(String tag) {
        return (SimpleParticleType) BuiltInRegistries.PARTICLE_TYPE.get(new ResourceLocation(tag));
    }

    private void tickArrowHead(Level level) {
        ParticleGradientHolder[] gradientHolders = new ParticleGradient(this.properties.getInt("maxSteps"),this.properties.getInt("maxParticle"), getFromTag(this.properties.getString("type1")), getFromTag(this.properties.getString("type2"))).generate();
        for (int y = 0; y < 2; y++) {
            for (int i = y; i <= 10; i++) {
                Vec3 targetLoc = MISCTools.calculateViewVector(target.getXRot(), target.getYRot() + (y == 0 ? 120 : -120)).scale(1 + (i * 0.05)).add(MISCTools.getPosition(target));
                MISCTools.sendParticles(level, false, targetLoc, 0, 0, 0, 0, gradientHolders[i]);
            }
        }
    }
    public enum Type {
        ORBIT("orbit"),
        ARROW_HEAD("arrow_head");

        private final String registryName;
        Type(String registryName) {
            this.registryName = registryName;
        }

        public String getRegistryName() {
            return registryName;
        }

        public static @Nullable Type getFromString(String name) {
            for (Type type : values()) {
                if (Objects.equals(type.registryName, name)) {
                    return type;
                }
            }
            return null;
        }
    }
}