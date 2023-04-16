package net.kapitencraft.mysticcraft.mob_effects;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class StunMobEffect extends MobEffect {
    public StunMobEffect() {
        super(MobEffectCategory.HARMFUL, -16777216);
    }

    @Override
    public void applyEffectTick(LivingEntity living, int p_19468_) {
        CompoundTag tag = living.getPersistentData();
        if (!tag.contains("isStunned", 1) || !tag.getBoolean("isStunned")) {
            CompoundTag stunTag = new CompoundTag();
            stunTag.putFloat("YHeadRot", living.getYHeadRot());
            stunTag.putFloat("YRot", living.getYRot());
            stunTag.putFloat("XRot", living.getXRot());
            stunTag.putFloat("YBodyRot", living.getVisualRotationYInDegrees());
            stunTag.putDouble("x", living.getX());
            stunTag.putDouble("y", living.getY());
            stunTag.putDouble("z", living.getZ());
            tag.put("StunInfo", stunTag);
            tag.putBoolean("isStunned", true);
        }
        if (tag.contains("StunInfo", 10)) {
            CompoundTag stunTag = (CompoundTag) tag.get("StunInfo");
            if (stunTag != null) {
                living.setYHeadRot(stunTag.getFloat("YHeadRot"));
                living.setYRot(stunTag.getFloat("YRot"));
                living.setXRot(stunTag.getFloat("XRot"));
                living.setYBodyRot(stunTag.getFloat("YBodyRot"));
                living.setPos(new Vec3(stunTag.getDouble("x"), stunTag.getDouble("y"), stunTag.getDouble("z")));
                living.setJumping(false);
            } else {
                throw new IllegalStateException("could not find Tag");
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity living, @NotNull AttributeMap p_19470_, int p_19471_) {
        super.removeAttributeModifiers(living, p_19470_, p_19471_);
        CompoundTag tag = living.getPersistentData();
        tag.putBoolean("isStunned", false);
    }
}
