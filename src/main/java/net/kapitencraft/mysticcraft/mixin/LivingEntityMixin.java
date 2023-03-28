package net.kapitencraft.mysticcraft.mixin;


import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(LivingEntity.class)
public interface LivingEntityMixin {

    @Accessor
    boolean getJumping();

    @Inject(method = "hurt", at = @At(value = "HEAD"))
    default boolean hurtMixin() {
        MysticcraftMod.sendInfo("hurt!");
        return true;
    }
}
