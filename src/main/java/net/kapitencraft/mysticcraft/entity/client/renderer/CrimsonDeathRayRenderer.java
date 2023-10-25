package net.kapitencraft.mysticcraft.entity.client.renderer;

import net.kapitencraft.mysticcraft.entity.CrimsonDeathRayProjectile;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CrimsonDeathRayRenderer extends EntityRenderer<CrimsonDeathRayProjectile> {
    public CrimsonDeathRayRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull CrimsonDeathRayProjectile p_114482_) {
        return new ResourceLocation("minecraft", "textures/entity/projectiles/arrow.png");
    }
}
