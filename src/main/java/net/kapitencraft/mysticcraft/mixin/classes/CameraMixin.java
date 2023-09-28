package net.kapitencraft.mysticcraft.mixin.classes;


import net.minecraft.client.Camera;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Camera.class)
public interface CameraMixin {

    @Accessor
    void setPosition(Vec3 position);
}
