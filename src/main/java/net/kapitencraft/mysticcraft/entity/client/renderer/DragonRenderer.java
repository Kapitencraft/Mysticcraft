package net.kapitencraft.mysticcraft.entity.client.renderer;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.entity.client.model.ModModelLayers;
import net.kapitencraft.mysticcraft.entity.client.model.TestEntityModel;
import net.kapitencraft.mysticcraft.entity.dragon.Dragon;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DragonRenderer extends LivingEntityRenderer<Dragon, TestEntityModel<Dragon>> {
    private static final ResourceLocation FLYING_LOCATION = MysticcraftMod.res("textures/entity/test/red_texture.png");
    private static final ResourceLocation WALKING_LOCATION = MysticcraftMod.res("textures/entity/test/blue_texture.png");

    public DragonRenderer(EntityRendererProvider.Context context) {
        super(context, new TestEntityModel<>(context.bakeLayer(ModModelLayers.DRAGON)), 2);

    }

    public DragonRenderer(EntityRendererProvider.Context pContext, TestEntityModel<Dragon> pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Dragon pEntity) {
        return pEntity.isFlying() ? FLYING_LOCATION : WALKING_LOCATION;
    }
}
