package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.capability.elytra.ElytraAttachment;
import net.kapitencraft.mysticcraft.capability.elytra.ElytraData;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FireworkRocketEntity.class)
public abstract class FireworkRocketEntityMixin {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V", ordinal = 0))
    public void redirectDeltaMovement(LivingEntity instance, Vec3 vec3) {
        instance.setDeltaMovement(getFireworkSpeedBoost(instance));
    }

    @Unique
    private static Vec3 getFireworkSpeedBoost(LivingEntity living) {
        Vec3 sourceLookAngle = living.getLookAngle();
        Vec3 sourceSpeed = living.getDeltaMovement();
        double d1 = 0.1;
        double d2 = 1.5;
        double d3 = 0.5;
        ItemStack chest = living.getItemBySlot(EquipmentSlot.CHEST);
        ElytraAttachment attachment = chest.get(ModDataComponentTypes.ELYTRA);
        if (attachment != null && attachment.data() == ElytraData.SPEED_BOOST) {
            d1 *= attachment.level();
            d3 *= attachment.level() / 5.;
        }
        return sourceSpeed.add(sourceLookAngle.x * d1 + (sourceLookAngle.x * d2 - sourceSpeed.x) * d3, sourceLookAngle.y * d1 + (sourceLookAngle.y * d2 - sourceSpeed.y) * d3, sourceLookAngle.z * d1 + (sourceLookAngle.z * d2 - sourceSpeed.z) * d3);
    }
}
