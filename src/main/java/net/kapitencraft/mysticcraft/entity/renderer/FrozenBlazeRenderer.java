package net.kapitencraft.mysticcraft.entity.renderer;

import net.kapitencraft.mysticcraft.entity.FrozenBlazeEntity;
import net.minecraft.client.model.BlazeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Blaze;

public class FrozenBlazeRenderer extends MobRenderer<FrozenBlazeEntity, BlazeModel<FrozenBlazeEntity>> {
    public FrozenBlazeRenderer(EntityRendererProvider.Context p_173933_) {
        super(p_173933_, new BlazeModel<>(p_173933_.bakeLayer(ModelLayers.BLAZE)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(FrozenBlazeEntity p_114482_) {
        return new ResourceLocation("mysticcraft:textures/entity/frozen_blaze.png");
    }

    protected int getBlockLightLevel(Blaze p_113910_, BlockPos p_113911_) {
        return 15;
    }
}
