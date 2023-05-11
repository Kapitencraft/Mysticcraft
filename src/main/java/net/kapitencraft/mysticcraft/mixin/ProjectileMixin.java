package net.kapitencraft.mysticcraft.mixin;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.utils.AttributeUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(Projectile.class)
public class ProjectileMixin {
    @Inject(method = "shootFromRotation", at = @At("HEAD"))
    public void shootFromRotation(Entity p_37252_, float p_37253_, float p_37254_, float p_37255_, float speed, float p_37257_) {
        if (p_37252_ instanceof LivingEntity living) {
            speed *= 1 + AttributeUtils.getSaveAttributeValue(ModAttributes.ARROW_SPEED.get(), living) / 100;
        }
    }
}