package net.kapitencraft.mysticcraft.mixin.classes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Set;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {

    @Shadow @Final private double x;

    @Shadow @Final private double y;

    @Shadow @Final private double z;

    @Shadow @Final private float radius;

    @WrapOperation(method = "explode", at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z"))
    public boolean addProxy(Set<BlockPos> instance, Object e, Operation<Boolean> original) {
        BlockPos pos = (BlockPos) e;
        if (new Vec3(pos.getX(), pos.getY(), pos.getZ()).distanceTo(new Vec3(this.x, this.y, this.z)) < radius) {
            return original.call(instance, e);
        }
        return false;
    }
}