package net.kapitencraft.mysticcraft.entity;

import net.kapitencraft.mysticcraft.tags.ModFluidTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class ModFishingHook extends FishingHook implements IFishingHook {
    private TagKey<Fluid> fluidType;
    private int hookSpeedModifier;

    private ModFishingHook(EntityType<? extends ModFishingHook> p_150141_, Level p_150142_, int luck, int lureSpeed, TagKey<Fluid> fluidType) {
        super(p_150141_, p_150142_, luck, lureSpeed);
        this.fluidType = fluidType;
    }

    protected ModFishingHook(EntityType<? extends ModFishingHook> type, Level level, TagKey<Fluid> fluidType) {
        this(type, level, 0, 0, fluidType);
    }


    protected ModFishingHook(EntityType<? extends ModFishingHook> type, Player p_37106_, Level level, int luck, int lureSpeed, TagKey<Fluid> fluidType) {
        this(type, level, luck, lureSpeed, fluidType);
        this.setOwner(p_37106_);
        float f = p_37106_.getXRot();
        float f1 = p_37106_.getYRot();
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        double d0 = p_37106_.getX() - (double)f3 * 0.3D;
        double d1 = p_37106_.getEyeY();
        double d2 = p_37106_.getZ() - (double)f2 * 0.3D;
        this.moveTo(d0, d1, d2, f1, f);
        Vec3 vec3 = new Vec3(-f3, Mth.clamp(-(f5 / f4), -5.0F, 5.0F), -f2);
        double d3 = vec3.length();
        vec3 = vec3.multiply(0.6D / d3 + this.random.triangle(0.5D, 0.0103365D), 0.6D / d3 + this.random.triangle(0.5D, 0.0103365D), 0.6D / d3 + this.random.triangle(0.5D, 0.0103365D));
        this.setDeltaMovement(vec3);
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
        this.setXRot((float)(Mth.atan2(vec3.y, vec3.horizontalDistance()) * (double)(180F / (float)Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        tag.putString("fluidType", this.fluidType.toString());
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        this.fluidType = ModFluidTags.getByName(tag.getString("fluidType"));
    }

    @Override
    public TagKey<Fluid> getFluidType() {
        return this.fluidType;
    }

    @Override
    public int getHookSpeedModifier() {
        return hookSpeedModifier;
    }

    @Override
    public void setHookSpeedModifier(int hookSpeedModifier) {
        this.hookSpeedModifier = hookSpeedModifier;
    }
}