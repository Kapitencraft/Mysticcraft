package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.init.ModMobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MoveControl.class)
public abstract class MoveControlMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo info) {
        if (getMob().hasEffect(ModMobEffects.STUN.get())) {
            info.cancel();
        }
    }

    @Accessor
    abstract public Mob getMob();
}