package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.kap_lib.core.helpers.MiscHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ILivingEntityExtension {

    public LivingEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    /**
     * @reason negative kb
     * @author Kapitencraft
     */
    @Overwrite
    public void knockback(double strenght, double xSpeed, double ySpeed) {
        LivingKnockBackEvent event = CommonHooks.onLivingKnockBack(self(), (float) strenght, xSpeed, ySpeed);
        if(event.isCanceled()) return;
        strenght = event.getStrength();
        xSpeed = event.getRatioX();
        ySpeed = event.getRatioZ();
        double kbResistance = self().getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
        double reducePercentage = kbResistance / (100 + kbResistance);
        strenght *= 1.0D - Math.min(1, reducePercentage);
        this.hasImpulse = true;
        Vec3 vec3 = this.getDeltaMovement();
        Vec3 vec31 = (new Vec3(xSpeed, 0.0D, ySpeed)).normalize().scale(strenght);
        this.setDeltaMovement(vec3.x / 2.0D - vec31.x, this.onGround() ? Math.min(0.4D, vec3.y / 2.0D + strenght) : vec3.y, vec3.z / 2.0D - vec31.z);
    }

    @ModifyConstant(method = "tryAddFrost", constant = @Constant(floatValue = -0.05f))
    private float increaseFreezeSlowness(float in) {
        return MiscHelper.forDifficulty(self().level().getDifficulty(), in * 1.5f, in * 2.5f, in * 4f, in);
    }
}