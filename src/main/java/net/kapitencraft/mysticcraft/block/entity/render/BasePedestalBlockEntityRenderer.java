package net.kapitencraft.mysticcraft.block.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.kapitencraft.mysticcraft.block.entity.pedestal.AbstractPedestalBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class BasePedestalBlockEntityRenderer<T extends AbstractPedestalBlockEntity> implements BlockEntityRenderer<T> {
    private final ItemRenderer itemRenderer;

    public BasePedestalBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        ItemStack stack = pBlockEntity.getItem();

        pPoseStack.pushPose();
        pPoseStack.translate(.5f, 1.15f, .5f);
        pPoseStack.scale(.5f, .5f, .5f);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(pBlockEntity.getRenderingRotation()));
        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, pPackedLight, pPackedOverlay, pPoseStack, pBuffer, null, 42);
        pPoseStack.popPose();
    }
}
