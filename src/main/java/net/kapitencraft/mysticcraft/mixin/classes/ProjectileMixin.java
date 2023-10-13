package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Projectile.class)
public abstract class ProjectileMixin extends Entity {

    public ProjectileMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    /**
     * @reason arrow speed attribute
     * @author Kapitencraft
     */
    @Overwrite
    public void shootFromRotation(Entity entity, float p_37253_, float p_37254_, float p_37255_, float speed, float p_37257_) {
        if (entity instanceof LivingEntity living) {
            speed *= 1 + AttributeHelper.getSaveAttributeValue(ModAttributes.ARROW_SPEED.get(), living) / 100;
        }
        float f = -Mth.sin(p_37254_ * ((float)Math.PI / 180F)) * Mth.cos(p_37253_ * ((float)Math.PI / 180F));
        float f1 = -Mth.sin((p_37253_ + p_37255_) * ((float)Math.PI / 180F));
        float f2 = Mth.cos(p_37254_ * ((float)Math.PI / 180F)) * Mth.cos(p_37253_ * ((float)Math.PI / 180F));
        this.callShoot(f, f1, f2, speed, p_37257_);
        Vec3 vec3 = entity.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, entity.isOnGround() ? 0.0D : vec3.y, vec3.z));
    }

    @Invoker
    abstract void callShoot(double p_37266_, double p_37267_, double p_37268_, float p_37269_, float p_37270_);
}