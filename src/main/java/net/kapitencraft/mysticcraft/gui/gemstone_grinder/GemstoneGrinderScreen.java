package net.kapitencraft.mysticcraft.gui.gemstone_grinder;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GemstoneGrinderScreen extends AbstractContainerScreen<GemGrinderMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MysticcraftMod.MOD_ID, "textures/gui/gemstone_grinder_gui.png");

    public GemstoneGrinderScreen(GemGrinderMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageHeight = 166;
        this.imageWidth = 176;
    }

    @Override
    protected void init() {
        super.init();
    }

        @Override
    protected void renderBg(PoseStack pose, float partialTick, int mouseX, int mouseY) {
        menu.player.sendSystemMessage(Component.literal("Rendering Screen"));
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pose, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float delta) {
        renderBackground(stack);
        super.render(stack, mouseX, mouseY, delta);
        renderTooltip(stack, mouseX, mouseY);
    }
}
