package net.kapitencraft.mysticcraft.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.entity.client.model.VampireBatModel;
import net.kapitencraft.mysticcraft.entity.vampire.VampireBat;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class VampireBatRenderer extends MobRenderer<VampireBat, VampireBatModel> {
    public VampireBatRenderer(EntityRendererProvider.Context context) {
        super(context, new VampireBatModel(VampireBatModel.createBodyLayer().bakeRoot()), 0.25f);
    }

    @Override
    protected void scale(@NotNull VampireBat bat, PoseStack stack, float p_115316_) {
        stack.scale(0.7f, 0.7f, 0.7f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull VampireBat bat) {
        return MysticcraftMod.res("textures/entity/vampire_bat.png");
    }
}
