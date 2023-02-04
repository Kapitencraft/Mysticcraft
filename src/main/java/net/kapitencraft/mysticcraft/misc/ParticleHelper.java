package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ParticleHelper {
    public static void tickHelper(LivingEntity living) {
        MysticcraftMod.sendInfo(String.valueOf(shouldHaveHelper(living)));
        if (shouldHaveHelper(living)) {
            for (ParticleHelper particleHelper : loadAllHelpers(living)) {
                particleHelper.tick(living.tickCount);
            }
        }
    }


    private static boolean shouldHaveHelper(LivingEntity target) {
        return target.getPersistentData().contains("ParticleHelper0", 10);
    }

    public static void clearAllHelpers(@Nullable String helperReason, LivingEntity target) {
        CompoundTag tag = target.getPersistentData();
        int i = 0;
        while (tag.contains("ParticleHelper" + i, 10)) {
            if (helperReason == null || ((CompoundTag) tag.get("ParticleHelper" + i)).getString("helperReason").equals(helperReason)) {
                tag.remove("ParticleHelper" + i);
            }
        }

    }

    private static Collection<ParticleHelper> loadAllHelpers(LivingEntity target) {
        CompoundTag tag = target.getPersistentData();
        Collection<ParticleHelper> helpers = new ArrayList<>();
        int i = 0;
        while (tag.contains("ParticleHelper" + i, 10)) {
            helpers.add(of((CompoundTag) tag.get("ParticleHelper" + i), target));
        }
        return helpers;
    }

    private boolean isRemoved = false;
    private final String helperReason;
    private final int cooldown, maxTicksAlive, ticksPerCycle;
    private float YOffset, curRotation;
    private final float maxHeight, heightChange, rotPerTick;
    private int ticksAlive, currentTick = 0;
    private final LivingEntity target;
    private final @NotNull ArmorStand helper;
    private final Type type;
    private final ParticleType<SimpleParticleType> particleType;

    public void remove(boolean flag) {
        this.isRemoved = flag;
    }

    public static ParticleHelper createWithTargetHeight(String helperReason, float initRot, int cooldown, int ticksAlive, float rotPerTick, LivingEntity target, Type type, ParticleType<SimpleParticleType> particleType) {
        return new ParticleHelper(helperReason, initRot, cooldown, target.getBbHeight(), ticksAlive, rotPerTick, target, type, particleType);
    }

    public ParticleHelper(String helperReason, float initRot, int cooldown, float maxHeight, int ticksAlive, float rotPerTick, LivingEntity target, Type type, ParticleType<SimpleParticleType> particleType) {
        MysticcraftMod.sendInfo("created a new ParticleHelper");
        this.helperReason = helperReason;
        this.ticksAlive = 0;
        this.YOffset = 0;
        this.curRotation = initRot;
        this.cooldown = cooldown;
        this.maxHeight = maxHeight;
        this.maxTicksAlive = ticksAlive;
        this.rotPerTick = rotPerTick;
        this.ticksPerCycle = (int) (360 / rotPerTick);
        this.heightChange = getHeightChangePerTick();
        this.target = target;
        this.helper = MISCTools.createMarker(MISCTools.getPosition(target), target.level, true);
        this.helper.setYRot(curRotation);
        this.type = type;
        this.particleType = particleType;
        this.updateSaveData();
    }

    private float getHeightChangePerTick() {
        return maxHeight / (ticksPerCycle / 2f);
    }

    private ParticleHelper(CompoundTag tag, LivingEntity target) {
        this.helperReason = tag.getString("helperReason");
        this.ticksAlive = tag.getInt("ticksAlive");
        this.cooldown = tag.getInt("cooldown");
        this.maxTicksAlive = tag.getInt("maxTicksAlive");
        this.rotPerTick = tag.getFloat("rotTick");
        this.maxHeight = tag.getFloat("height");
        this.currentTick = tag.getInt("currentTick");
        this.ticksPerCycle = (int) (360 / rotPerTick);
        this.heightChange = getHeightChangePerTick();
        this.YOffset = tag.getFloat("YOffset");
        this.curRotation = tag.getFloat("curRot");
        this.target = target;
        this.type = Type.getFromString(tag.getString("type"));
        if (target.level instanceof ServerLevel serverLevel && serverLevel.getEntity(tag.getUUID("helperID")) != null) {
            this.helper = (ArmorStand) serverLevel.getEntity(tag.getUUID("helperID"));
        } else {
            this.helper = MISCTools.createMarker(MISCTools.getPosition(target), target.level, true);
        }
        if (this.helper == null) {
            throw new IllegalStateException("Couldn't find nor create Helper");
        }
        this.helper.setYRot(this.curRotation);
        this.particleType = (SimpleParticleType) BuiltInRegistries.PARTICLE_TYPE.get(new ResourceLocation(tag.getString("particleType")));
    }

    private int getNextFreeHelperSlot() {
        CompoundTag targetTag = target.getPersistentData();
        int tagNum = 0;
        while (targetTag.contains("ParticleHelper" + tagNum, 10)) {
            tagNum++;
        }
        return tagNum;
    }

    private void updateSaveData() {
        CompoundTag tag = new CompoundTag();
        tag.putString("helperReason", helperReason);
        tag.putInt("ticksAlive", ticksAlive);
        tag.putUUID("helperID", helper.getUUID());
        tag.putString("type", type.getRegistryName());
        tag.putInt("currentTick", currentTick);
        tag.putInt("maxTicksAlive", maxTicksAlive);
        ResourceLocation location = BuiltInRegistries.PARTICLE_TYPE.getKey(this.particleType);
        tag.putString("particleType", (location == null ? BuiltInRegistries.PARTICLE_TYPE.getKey(ParticleTypes.ANGRY_VILLAGER) : location).toString());
        tag.putFloat("rotTick", rotPerTick);
        tag.putFloat("YOffset", YOffset);
        tag.putInt("cooldown", cooldown);
        tag.putFloat("height", maxHeight);
        tag.putFloat("curRot", curRotation);
        tag.putFloat("XRot", helper.getXRot());
        MysticcraftMod.sendInfo("Saving ParticleHelper to slot: " + getNextFreeHelperSlot());
        this.target.getPersistentData().put("ParticleHelper" + getNextFreeHelperSlot(), tag);
    }

    public static @Nullable ParticleHelper of(CompoundTag tag, LivingEntity living) {
        if (tag != null) {
            return new ParticleHelper(tag, living);
        }
        return null;
    }

    public void tick(int ticks) {
        if (!(this.ticksAlive++ > this.maxTicksAlive && this.target.isRemoved()) && (this.cooldown == 0 || ticks % this.cooldown == 0) && !this.isRemoved) {
            Level level = this.target.level;
            if (this.currentTick++ >= this.ticksPerCycle) {
                this.currentTick = 0;
            }
            if (this.type == Type.ORBIT) {
                tickOrbit(level);
            }
            this.updateSaveData();
        }
        if (this.target.isRemoved()) {
            this.helper.kill();
        }
    }

    private void tickOrbit(Level level) {
        if (currentTick >= (ticksPerCycle / 2)) {
            YOffset-=this.heightChange;
        } else {
            YOffset+=this.heightChange;
        }
        this.curRotation+=rotPerTick;
        helper.setYRot(curRotation);
        helper.setPos(target.getX(), target.getY() +  YOffset, target.getZ());
        Vec3 targetPos = helper.getLookAngle().scale(1).add(MISCTools.getPosition(helper));
        MISCTools.sendParticles(level, (SimpleParticleType) particleType, false, targetPos, 2, 0,0, 0, 0);
    }

    public enum Type {
        ORBIT("orbit");

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