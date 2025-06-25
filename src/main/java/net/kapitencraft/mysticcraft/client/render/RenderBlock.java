package net.kapitencraft.mysticcraft.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.kapitencraft.mysticcraft.client.shader.ModRenderTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class RenderBlock {
    private static final List<BlockPos> toRender = List.of(new BlockPos(0, -61, 0));

    @SubscribeEvent
    public static void render(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) {
            renderBlocks(event.getPoseStack());
        }
    }

    private static void renderBlocks(PoseStack stack) {
        RenderSystem.assertOnRenderThread();
        Tesselator tesselator = RenderSystem.renderThreadTesselator();
        MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(tesselator.getBuilder());
        VertexConsumer builder = bufferSource.getBuffer(ModRenderTypes.OVERLAY_LINES);
        Level level = Minecraft.getInstance().level;
        Player player = Minecraft.getInstance().player;
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        Vec3 camPos = camera.getPosition();
        if (level == null || player == null) return;
        for (BlockPos pos : toRender) {
            BlockState state = level.getBlockState(pos);
            LevelRenderer.renderShape(stack, builder, state.getVisualShape(level, pos, CollisionContext.of(player)), pos.getX() - camPos.x, pos.getY() - camPos.y, pos.getZ() - camPos.z, 0, 0, 1, 0.8f);
        }
        bufferSource.endBatch(ModRenderTypes.OVERLAY_LINES);
    }
}
