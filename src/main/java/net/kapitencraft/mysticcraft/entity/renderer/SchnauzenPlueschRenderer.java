package net.kapitencraft.mysticcraft.entity.renderer;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.entity.SchnauzenPluesch;
import net.kapitencraft.mysticcraft.entity.model.SchnauzenPlueschModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SchnauzenPlueschRenderer extends GeoEntityRenderer<SchnauzenPluesch> {

    public SchnauzenPlueschRenderer(EntityRendererProvider.Context context) {
        super(context, new SchnauzenPlueschModel<>());
        this.shadowRadius = 0.3f;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(SchnauzenPluesch pluesch) {
            return MysticcraftMod.res("textures/entity/schnauzen_pluesch/schnauzen_pluesch_" + pluesch.getColor() + ".png");
    }
}
