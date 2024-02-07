package net.kapitencraft.mysticcraft.misc.particle_help;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.TagHelper;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

public class ParticleAnimator {
    private static final String COOLDOWN_ID = "cooldown";
    private static final String TICKS_ALIVE_ID = "ticksAlive";
    private static final String MAX_TICKS_ALIVE_ID = "maxTicksAlive";
    private static final String Y_OFFSET_ID = "YOffset";
    private static final String DISTANCE_ID = "distance";
    private static final String CURRENT_ROTATION_ID = "curRotation";
    private static final String MAX_HEIGHT_ID = "maxHeight";
    private static final String ROTATION_PER_TICK_ID = "rotPerTick";
    private static final String PARTICLE_OPTIONS = "particleType";
    private static final ParticleHelperQueueWorker worker = new ParticleHelperQueueWorker();
    public static void tickHelper(Entity living) {
        if (!worker.hasTarget(living.getUUID())) {
            loadAllHelpers(living);
        }
        worker.tick(living.tickCount, living.getUUID());
    }

    public static void clearAllHelpers(@Nullable String helperReason, LivingEntity target) {
        worker.remove(target.getUUID(), helperReason);
        target.getPersistentData()
                .getList("ParticleHelpers", Tag.TAG_COMPOUND)
                .removeIf(tag1 -> tag1 instanceof CompoundTag helperTag && (helperReason == null || helperTag.getString("helperReason").equals(helperReason)));
    }

    public static CompoundTag createArrowHeadProperties(int maxSteps, int maxParticle, SimpleParticleType particleType, SimpleParticleType particleType1) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("maxSteps", maxSteps);
        tag.putInt("maxParticle", maxParticle);
        tag.putString("type1", toTag(particleType));
        tag.putString("type2", toTag(particleType1));
        return tag;
    }

    public static CompoundTag createOrbitProperties(int cooldown, int maxTicksAlive, float initRotation, float maxHeight, float rotPerTick, ParticleOptions particleType, float distance) {
        CompoundTag tag = new CompoundTag();
        tag.putInt(COOLDOWN_ID, cooldown);
        tag.putInt(TICKS_ALIVE_ID, 0);
        tag.putInt(MAX_TICKS_ALIVE_ID, maxTicksAlive);
        tag.putFloat(Y_OFFSET_ID, 0);
        tag.putFloat(CURRENT_ROTATION_ID, initRotation);
        tag.putFloat(MAX_HEIGHT_ID, maxHeight);
        tag.putFloat(ROTATION_PER_TICK_ID, rotPerTick);
        tag.putString(PARTICLE_OPTIONS, toTag(particleType));
        tag.putFloat(DISTANCE_ID, distance);
        return tag;
    }

    public static String toTag(ParticleOptions type) {
        ResourceLocation location = BuiltInRegistries.PARTICLE_TYPE.getKey(type.getType());
        if (location == null) location = BuiltInRegistries.PARTICLE_TYPE.getKey(ParticleTypes.ANGRY_VILLAGER);
        return location == null ? "null" : location.toString();
    }

    private static void loadAllHelpers(Entity target) {
        CompoundTag tag = target.getPersistentData();
        ListTag helpers = tag.getList("ParticleHelpers", Tag.TAG_COMPOUND);
        for (int i = 0; i < helpers.size(); i++) {
            worker.add(target.getUUID(), of(helpers.getCompound(i), target, i));
        }
    }

    private boolean isRemoved = false;
    private final boolean liveUntilTargetDeath;
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

    public static ParticleAnimator createWithTargetHeight(String helperReason, Entity target, Type type, CompoundTag properties) {
        properties.putFloat(MAX_HEIGHT_ID, target.getBbHeight());
        return new ParticleAnimator(helperReason, target, type, properties);
    }

    public ParticleAnimator(String helperReason, Entity target, Type type, CompoundTag properties) {
        this.liveUntilTargetDeath = type == Type.ARROW_HEAD;
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

    private ParticleAnimator(@NotNull CompoundTag tag, Entity target, int curTagID) {
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
        this.liveUntilTargetDeath = this.type == Type.ARROW_HEAD;
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
        MysticcraftMod.LOGGER.info(Markers.PARTICLE_ENGINE, "Saving ParticleHelper to slot: {} (ParticleHelper {})", getNextFreeHelperSlot(), this.curTagID);
        this.target.getPersistentData().put("ParticleHelper" + this.curTagID, tag);
    }

    private void updateSave(int oldLoc) {
        target.getPersistentData().remove("ParticleHelper" + oldLoc);
        this.curTagID = getNextFreeHelperSlot();
        initSave();
    }

    public static ParticleAnimator of(@NotNull CompoundTag tag, Entity living, int tagID) {
        return new ParticleAnimator(tag, living, tagID);
    }

    public void tick(int ticks) {
        int cooldown = this.properties.getInt(COOLDOWN_ID);
        if (!(TagHelper.increaseIntegerTagValue(this.properties, TICKS_ALIVE_ID, 1) > this.properties.getInt(MAX_TICKS_ALIVE_ID) && this.target.isRemoved()) || this.liveUntilTargetDeath && (cooldown == 0 || ticks % cooldown == 0) && !this.isRemoved) {
            Level level = this.target.level;
            if (this.currentTick++ >= this.ticksPerCycle) {
                this.currentTick = 0;
            }
            switch (this.type) {
                case ORBIT -> tickOrbit(level);
                case ARROW_HEAD -> tickArrowHead(level);
            }
            this.save();
        }
    }

    public static SimpleParticleType getFromTag(String tag) {
        return (SimpleParticleType) BuiltInRegistries.PARTICLE_TYPE.get(new ResourceLocation(tag));
    }

    private void tickOrbit(Level level) {
        float currentRot = TagHelper.increaseFloatTagValue(this.properties, CURRENT_ROTATION_ID, this.properties.getFloat(ROTATION_PER_TICK_ID));
        Vec3 targetPos;
        if (this.target instanceof Projectile) {
            targetPos = MathHelper.setLength(MathHelper.calculateViewVector(currentRot, 0), this.properties.getFloat(DISTANCE_ID)).add(target.position());
        } else {
            TagHelper.increaseFloatTagValue(this.properties, Y_OFFSET_ID, (currentTick >= (ticksPerCycle / 2)) ? -this.heightChange : this.heightChange);
            targetPos = MathHelper.setLength(MathHelper.calculateViewVector(0, currentRot), this.properties.getFloat(DISTANCE_ID)).add(target.getX(), target.getY() +  this.properties.getFloat(Y_OFFSET_ID), target.getZ());
        }
        net.kapitencraft.mysticcraft.helpers.ParticleHelper.sendParticles(level, getFromTag(this.properties.getString("particleType")), false, targetPos, 2, 0,0, 0, 0);
    }

    private void tickArrowHead(Level level) {
        if (level instanceof ServerLevel) {
            ParticleGradientHolder[] gradientHolders = new ParticleGradient(this.properties.getInt("maxSteps"), this.properties.getInt("maxParticle"), getFromTag(this.properties.getString("type1")), getFromTag(this.properties.getString("type2"))).generate();
            for (int y = 0; y < 2; y++) {
                for (int i = y; i < 10; i++) {
                    Vec3 targetLoc = MathHelper.calculateViewVector(target.getXRot(), target.getYRot() + (y == 0 ? 160 : -160)).scale(i * 0.05).add(target.getX(), target.getY() + 0.1f, target.getZ());
                    net.kapitencraft.mysticcraft.helpers.ParticleHelper.sendParticles(level, false, targetLoc, 0, 0, 0, 0, gradientHolders[i]);
                }
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