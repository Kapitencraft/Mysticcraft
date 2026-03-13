package net.kapitencraft.mysticcraft.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.kapitencraft.kap_lib.core.client.widget.PositionedWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix4f;

import java.util.function.Supplier;

public class FluidTankWidget extends PositionedWidget {
    private final Supplier<FluidStack> fluid;
    private final Supplier<Integer> capacityGetter;

    public FluidTankWidget(int x, int y, int width, int height, Supplier<FluidStack> fluid, Supplier<Integer> capacityGetter) {
        super(x, y, width, height);
        this.fluid = fluid;
        this.capacityGetter = capacityGetter;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        FluidStack fluidStack = fluid.get();

        if (!fluidStack.isEmpty()) {
            int tankX = x;
            int tankY = y;

            float fillRatio = Math.min((float) fluidStack.getAmount() / capacityGetter.get(), 1.0f);
            int fluidRenderHeight = (int) (fillRatio * height);

            if (fluidRenderHeight < 1 && fluidStack.getAmount() > 0) {
                fluidRenderHeight = 1;
            }

            IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
            TextureAtlasSprite fluidSprite = Minecraft.getInstance()
                    .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                    .apply(extensions.getStillTexture(fluidStack));

            int color = extensions.getTintColor(fluidStack);
            float r = ((color >> 16) & 0xFF) / 255f;
            float g = ((color >> 8) & 0xFF) / 255f;
            float b = (color & 0xFF) / 255f;

            guiGraphics.setColor(r, g, b, 1f);

            int fluidY = tankY + height - fluidRenderHeight;

            RenderSystem.setShaderTexture(0, fluidSprite.atlasLocation());
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            BufferBuilder builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            Matrix4f pose = guiGraphics.pose().last().pose();
            for (int i = 0; i < fluidRenderHeight; i += 16) {
                for (int j = 0; i < width; i += 16) {
                    int drawWidth = Math.min(width - j, 16);
                    int drawHeight = Math.min(fluidRenderHeight - i, 16);

                    renderFluid(builder, pose, tankX, fluidY + i, fluidSprite, drawWidth, drawHeight);
                }
            }
            BufferUploader.drawWithShader(builder.buildOrThrow());
            guiGraphics.setColor(1f, 1f, 1f, 1f);
        }
    }

    private static void renderFluid(BufferBuilder builder, Matrix4f matrix4f, int x, int y, TextureAtlasSprite sprite, int width, int height) {
        builder.addVertex(matrix4f, (float)x, (float)y, 0f).setUv(sprite.getU0(), sprite.getV0());
        builder.addVertex(matrix4f, (float)x, (float)y + height, 0f).setUv(sprite.getU0(), sprite.getV(height / 16f));
        builder.addVertex(matrix4f, (float)x + width, (float)y + height, 0f).setUv(sprite.getU(width / 16f), sprite.getV(height / 16f));
        builder.addVertex(matrix4f, (float)x + width, (float)y, 0f).setUv(sprite.getU(width / 16f), sprite.getV0());
    }
}
