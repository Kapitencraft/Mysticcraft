package net.kapitencraft.mysticcraft.entity.client.renderer;

import net.kapitencraft.mysticcraft.entity.skeleton_master.ControlledArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ControlledArrowRenderer extends ArrowRenderer<ControlledArrow> {
    public ControlledArrowRenderer(EntityRendererProvider.Context p_173917_) {
        super(p_173917_);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ControlledArrow p_114482_) {
        return TippableArrowRenderer.NORMAL_ARROW_LOCATION;
    }
}
