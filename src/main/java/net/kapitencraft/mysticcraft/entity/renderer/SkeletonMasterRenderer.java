package net.kapitencraft.mysticcraft.entity.renderer;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.entity.model.SkeletonMasterModel;
import net.kapitencraft.mysticcraft.entity.skeleton_master.SkeletonMaster;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SkeletonMasterRenderer extends MobRenderer<SkeletonMaster, SkeletonMasterModel> {
    public SkeletonMasterRenderer(EntityRendererProvider.Context context) {
        super(context, new SkeletonMasterModel(context.bakeLayer(ModelLayers.SKELETON)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull SkeletonMaster p_115941_) {
        return MysticcraftMod.res("textures/entity/skeleton_master.png");
    }
}
