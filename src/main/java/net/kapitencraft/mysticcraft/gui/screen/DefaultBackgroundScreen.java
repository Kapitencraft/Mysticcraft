package net.kapitencraft.mysticcraft.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class DefaultBackgroundScreen extends Screen implements IBackgroundScreen {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("mysticcraft:textures/gui/browsable_background.png");

    protected int leftPos;
    protected int topPos;


    protected DefaultBackgroundScreen(Component pTitle) {
        super(pTitle);
    }

    @Override
    protected void init() {
        this.leftPos = this.leftPos(this.width);
        this.topPos = this.topPos(this.height);
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderBackground(pPoseStack);
    }

    @Override
    public void renderBackground(@NotNull PoseStack pPoseStack) {
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        GuiComponent.blit(pPoseStack, this.leftPos, this.topPos, 0, 0, 0, getImageWidth(),  getImageHeight(), getImageWidth(), getImageHeight());
    }

    @Override
    public int getImageWidth() {
        return 219;
    }

    @Override
    public int getImageHeight() {
        return 180;
    }
}
