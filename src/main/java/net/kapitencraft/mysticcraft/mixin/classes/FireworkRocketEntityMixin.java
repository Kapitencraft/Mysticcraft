package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.elytra.ElytraData;
import net.kapitencraft.mysticcraft.item.capability.elytra.IElytraData;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
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
        LazyOptional<IElytraData> dataOptional = chest.getCapability(CapabilityHelper.ELYTRA);
        if (dataOptional.isPresent()) {
            IElytraData data = dataOptional.resolve().get();
            int scale = data.getLevelForData(ElytraData.SPEED_BOOST);
            if (scale > 0) {
                d1 *= scale;
                d3 *= scale / 5.;
            }
        }
        return sourceSpeed.add(sourceLookAngle.x * d1 + (sourceLookAngle.x * d2 - sourceSpeed.x) * d3, sourceLookAngle.y * d1 + (sourceLookAngle.y * d2 - sourceSpeed.y) * d3, sourceLookAngle.z * d1 + (sourceLookAngle.z * d2 - sourceSpeed.z) * d3);
    }
}
