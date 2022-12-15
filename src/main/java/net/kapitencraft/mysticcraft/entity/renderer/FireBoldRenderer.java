package net.kapitencraft.mysticcraft.entity.renderer;

import net.kapitencraft.mysticcraft.spell.spells.FireBoldProjectile;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FireBoldRenderer extends EntityRenderer<FireBoldProjectile> {
    public FireBoldRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(FireBoldProjectile p_114482_) {
        return new ResourceLocation("minecraft", "textures/entity/projectiles/arrow.png");
    }
}
