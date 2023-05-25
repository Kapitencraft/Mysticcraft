package net.kapitencraft.mysticcraft.mixin.classes;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PlayerItemInHandLayer.class)
public abstract class HandItemLayerRendererMixin<T extends Player, M extends EntityModel<T> & ArmedModel & HeadedModel> extends ItemInHandLayer<T, M> {
    public HandItemLayerRendererMixin(RenderLayerParent p_234846_, ItemInHandRenderer p_234847_) {
        super(p_234846_, p_234847_);
    }

    @Invoker
    abstract void callRenderArmWithSpyglass(@NotNull LivingEntity living, @NotNull ItemStack stack, @NotNull HumanoidArm arm, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int p_117191_);

    @Override
    protected void renderArmWithItem(@NotNull LivingEntity living, @NotNull ItemStack stack, ItemTransforms.@NotNull TransformType transformType, @NotNull HumanoidArm arm, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int i) {
        if (living.isInvisible() && (transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND)) return;
        if (stack.is(Items.SPYGLASS) && living.getUseItem() == stack && living.swingTime == 0) {
            this.callRenderArmWithSpyglass(living, stack, arm, poseStack, bufferSource, i);
        } else {
            super.renderArmWithItem(living, stack, transformType, arm, poseStack, bufferSource, i);
        }

    }
}