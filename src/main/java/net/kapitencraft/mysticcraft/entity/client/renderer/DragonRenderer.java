package net.kapitencraft.mysticcraft.entity.client.renderer;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.entity.client.model.DragonModel;
import net.kapitencraft.mysticcraft.entity.client.model.ModModelLayers;
import net.kapitencraft.mysticcraft.entity.dragon.Dragon;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DragonRenderer extends LivingEntityRenderer<Dragon, DragonModel<Dragon>> {
    private static final ResourceLocation TEXTURE = MysticcraftMod.res("textures/entity/dragon/dragon.png");

    public DragonRenderer(EntityRendererProvider.Context context) {
        super(context, new DragonModel<>(context.bakeLayer(ModModelLayers.DRAGON)), 2);

    }

    public DragonRenderer(EntityRendererProvider.Context pContext, DragonModel<Dragon> pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Dragon pEntity) {
        return TEXTURE;
    }
}
