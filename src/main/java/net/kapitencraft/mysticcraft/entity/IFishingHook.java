package net.kapitencraft.mysticcraft.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public interface IFishingHook {

    default TagKey<Fluid> getFluidType() {
        return FluidTags.WATER;
    }

    void setHookSpeedModifier(int modifier);
    int getHookSpeedModifier();

    default ResourceLocation lootTableId() {
        return BuiltInLootTables.FISHING;
    }

    default Material getMaterial() {
        return Material.WATER;
    }

    default ParticleOptions getBubbleParticle() {
        return ParticleTypes.BUBBLE;
    }
    default ParticleOptions getFishingParticle() {
        return ParticleTypes.FISHING;
    }
    default ParticleOptions getSplashParticle() {
        return ParticleTypes.SPLASH;
    }
}
