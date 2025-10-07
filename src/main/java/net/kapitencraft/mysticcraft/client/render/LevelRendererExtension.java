package net.kapitencraft.mysticcraft.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.kapitencraft.mysticcraft.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.tech.DistributionNetworkManager;
import net.kapitencraft.mysticcraft.tech.ManaDistributionNetwork;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class LevelRendererExtension {


    @SuppressWarnings("DataFlowIssue")
    @SubscribeEvent
    public static void render(RenderLevelStageEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        MultiBufferSource.BufferSource source = minecraft.renderBuffers().bufferSource();
        PoseStack stack = event.getPoseStack();
        stack.pushPose();
        Camera camera = event.getCamera();
        Vec3 camPos = camera.getPosition();
        stack.translate(-camPos.x, -camPos.y, -camPos.z); //ALWAYS ensure to translate -camPos because otherwise it will render anywhere, but not where you need it
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {
            Frustum frustum = event.getFrustum();
            DistributionNetworkManager manager = DistributionNetworkManager.getClient();
            VertexConsumer buffer = source.getBuffer(RenderType.debugLineStrip(20));

            for (ManaDistributionNetwork network : manager.getNetworks()) {
                List<ManaDistributionNetwork.Node> visited = new ArrayList<>();
                for (ManaDistributionNetwork.Node node : network.getNodes()) {
                    for (ManaDistributionNetwork.Node node1 : node.getConnected()) {
                        if (!visited.contains(node1) && frustum.isVisible(new AABB(node.getPosition(), node1.getPosition()))) {
                            Vec3 start = node.getPosition().getCenter();
                            Vec3 end = node1.getPosition().getCenter();
                            Matrix4f posMat = stack.last().pose();
                            buffer.vertex(posMat, (float) start.x, (float) start.y, (float) start.z).color(0, 150, 240, 255).endVertex();
                            buffer.vertex(posMat, (float) end.x, (float) end.y, (float) end.z).color(0, 150, 240, 255).endVertex();
                        }
                    }
                    visited.add(node);
                }
            }
        }
        else if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            BlockPos pos = SpellHelper.getBlockTarget(minecraft.player);
            if (pos != null) {
                stack.pushPose();
                stack.translate(pos.getX(), pos.getY(), pos.getZ());
                BlockState state = minecraft.level.getBlockState(pos);
                OutlineBufferSource outlineBufferSource = minecraft.renderBuffers().outlineBufferSource();
                outlineBufferSource.setColor(0, 127, 204, 255);
                minecraft.getBlockRenderer().renderSingleBlock(state, stack, outlineBufferSource, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
                stack.popPose();
            }
        }
        stack.popPose();
    }
}
