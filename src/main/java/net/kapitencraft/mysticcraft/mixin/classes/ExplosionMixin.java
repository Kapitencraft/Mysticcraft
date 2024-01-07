package net.kapitencraft.mysticcraft.mixin.classes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {

    @Shadow @Final private double x;

    @Shadow @Final private double y;

    @Shadow @Final private double z;

    @Shadow @Final private float radius;

    @Redirect(method = "explode", at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z"))
    public boolean addProxy(Set instance, Object e) {
        BlockPos pos = (BlockPos) e;
        if (new Vec3(pos.getX(), pos.getY(), pos.getZ()).distanceTo(new Vec3(this.x, this.y, this.z)) < radius) {
            return instance.add(pos);
        }
        return false;
    }
}