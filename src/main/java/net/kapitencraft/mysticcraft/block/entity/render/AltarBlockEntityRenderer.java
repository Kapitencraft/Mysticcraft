package net.kapitencraft.mysticcraft.block.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.kapitencraft.mysticcraft.block.entity.pedestal.AltarBlockEntity;
import net.kapitencraft.mysticcraft.client.shader.ModRenderTypes;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AltarBlockEntityRenderer extends BasePedestalBlockEntityRenderer<AltarBlockEntity> {
    private final BlockRenderDispatcher dispatcher;

    public AltarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        this.dispatcher = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(AltarBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        super.render(pBlockEntity, pPartialTick, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);

        VertexConsumer consumer = pBuffer.getBuffer(ModRenderTypes.GHOST);
        BlockRenderDispatcher dispatcher = this.dispatcher;
        ModelBlockRenderer modelRenderer = dispatcher.getModelRenderer();
        BlockState state = ModBlocks.PEDESTAL.get().defaultBlockState();
        BakedModel model = dispatcher.getBlockModel(state);

        Level level = pBlockEntity.getLevel();
        BlockPos origin = pBlockEntity.getBlockPos();
        for (BlockPos pos : pBlockEntity.getPedestalPositions()) {
            pPoseStack.pushPose();
            pPoseStack.translate(pos.getX() - origin.getX(), 0, pos.getZ() - origin.getZ());
            boolean air = level.getBlockState(pos).isAir();
            modelRenderer.renderModel(pPoseStack.last(), consumer, state, model, 1, air ? 0 : 1, air ? 0 : 1, pPackedLight, pPackedOverlay);
            pPoseStack.popPose();
        }
    }
}
