package net.kapitencraft.mysticcraft.misc;

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
import java.util.Objects;

public class ParticleHelper {
    private final int cooldown;
    private final float heightChange;
    private final int ticksAlive;
    private final float rotPerTick;
    private int currentTick = 0;
    private final int ticksPerCylce;
    private final LivingEntity target;
    private final @NotNull ArmorStand helper;
    private final Type type;
    private final ParticleType particleType;

    public ParticleHelper(int cooldown, float heightChange, int ticksAlive, float rotPerTick, LivingEntity target, Type type, ParticleType particleType) {
        this.cooldown = cooldown;
        this.heightChange = heightChange;
        this.ticksAlive = ticksAlive;
        this.rotPerTick = rotPerTick;
        this.ticksPerCylce = (int) (360 / rotPerTick);
        this.target = target;
        this.helper = MISCTools.createMarker(MISCTools.getPosition(target), target.level, true);
        this.type = type;
        this.particleType = particleType;
        this.updateSaveData();
    }

    private ParticleHelper(CompoundTag tag, LivingEntity target) {
        this.cooldown = tag.getInt("cooldown");
        this.ticksAlive = tag.getInt("ticksAlive");
        this.rotPerTick = tag.getFloat("rotTick");
        this.heightChange = tag.getFloat("height");
        this.ticksPerCylce = (int) (360 / rotPerTick);
        this.target = target;
        this.type = Type.getFromString(tag.getString("type"));
        if (target.level instanceof ServerLevel serverLevel && serverLevel.getEntity(tag.getUUID("helperID")) != null) {
            this.helper = (ArmorStand) serverLevel.getEntity(tag.getUUID("helperID"));
        } else {
            this.helper = MISCTools.createMarker(MISCTools.getPosition(target), target.level, true);
        }
        this.particleType = BuiltInRegistries.PARTICLE_TYPE.get(new ResourceLocation(tag.getString("particleType")));
    }

    public void tick(int ticks) {
        if (!(currentTick > ticksAlive && this.target.isRemoved()) && ticks % cooldown == 0) {
            Level level = target.level;
            if (this.currentTick++ >= this.ticksPerCylce) {
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

    private void updateSaveData() {
        CompoundTag tag = new CompoundTag();
        tag.putUUID("helperID", helper.getUUID());
        tag.putString("type", type.getRegistryName());
        tag.putInt("currentTick", currentTick);
        tag.putInt("ticksAlive", ticksAlive);
        ResourceLocation location = BuiltInRegistries.PARTICLE_TYPE.getKey(this.particleType);
        tag.putString("particleType", (location == null ? BuiltInRegistries.PARTICLE_TYPE.getKey(ParticleTypes.ANGRY_VILLAGER) : location).toString());
        tag.putFloat("rotTick", rotPerTick);
        tag.putInt("cooldown", cooldown);
        tag.putFloat("height", heightChange);
        tag.putFloat("XRot", helper.getXRot());
        this.target.getPersistentData().put("ParticleHelper", tag);
    }

    public static @Nullable ParticleHelper of(LivingEntity target) {
        if (target.level instanceof ServerLevel) {
            CompoundTag targetTag = target.getPersistentData();
            CompoundTag tag = (CompoundTag) targetTag.get("ParticleHelper");
            if (tag != null) {
                return new ParticleHelper(tag, target);
            }
        }
        return null;
    }

    private void tickOrbit(Level level) {
        double heightOffset = helper.getPersistentData().getDouble("YOffset");
        Vec3 targetPos = helper.getLookAngle().scale(1).add(MISCTools.getPosition(helper));
        MISCTools.sendParticles(level, (SimpleParticleType) particleType, false, targetPos, 2, 0,0, 0, 0);
        if (currentTick >= (ticksPerCylce / 2)) {
            heightOffset+=this.heightChange;
        } else {
            heightOffset-=this.heightChange;
        }
        helper.turn(this.rotPerTick, 0);
        helper.setPos(target.getX(), target.getY() +  heightOffset, target.getZ());
        helper.getPersistentData().putDouble("YOffset", heightOffset);
    }



    enum Type {
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
