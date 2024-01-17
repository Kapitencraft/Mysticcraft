package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FireworkRocketEntity.class)
public class FireworkRocketEntityMixin {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V", ordinal = 0))
    public void redirectDeltaMovement(LivingEntity instance, Vec3 vec3) {
        instance.setDeltaMovement(MiscHelper.getFireWorkSpeedBoost(instance));
    }
}
