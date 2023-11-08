package net.kapitencraft.mysticcraft.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class RenderBlock {
    private static final List<BlockPos> toRender = List.of(new BlockPos(0, 100, 0));

    //@SubscribeEvent
    public static void render(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) renderBlocks(event.getProjectionMatrix());
    }

    private static void renderBlocks(Matrix4f matrix) {
        MultiBufferSource.BufferSource source = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer consumer = source.getBuffer(ModRenderTypes.OVERLAY_LINES);
        RenderSystem.setShader(GameRenderer::getRendertypeOutlineShader);
        for (BlockPos pos : toRender) {
            renderLine(consumer, matrix, pos, 0, 0, 0, 1, 0, 0);
            renderLine(consumer, matrix, pos, 0, 1, 0, 1, 1, 0);
            renderLine(consumer, matrix, pos, 0, 0, 1, 1, 0 ,1);
            renderLine(consumer, matrix, pos, 0, 1, 1, 1, 1, 1);

            renderLine(consumer, matrix, pos, 0, 0, 0, 0, 0, 0);
            renderLine(consumer, matrix, pos, 1, 0, 0, 1, 0, 1);
            renderLine(consumer, matrix, pos, 0, 1, 0, 0, 1, 1);
            renderLine(consumer, matrix, pos, 1, 1, 0, 1, 1, 1);

            renderLine(consumer, matrix, pos, 0, 0, 0, 0, 1, 0);
            renderLine(consumer, matrix, pos, 1, 0, 0, 1, 1, 0);
            renderLine(consumer, matrix, pos, 0, 0, 1, 0, 1, 1);
            renderLine(consumer, matrix, pos, 1, 0, 1, 1, 1, 1);
        }
    }

    private static void renderLine(VertexConsumer consumer, Matrix4f posMat, BlockPos pos, float dx1, float dy1, float dz1, float dx2, float dy2, float dz2) {
        consumer.vertex(posMat, pos.getX() + dx1, pos.getY() + dy1, pos.getZ() + dz1)
                .color(0, 1, 0, 1)
                .endVertex();
        consumer.vertex(posMat, pos.getX() + dy1, pos.getY() + dy2, pos.getZ() + dy2)
                .color(0, 1, 0, 1)
                .endVertex();
    }
}
