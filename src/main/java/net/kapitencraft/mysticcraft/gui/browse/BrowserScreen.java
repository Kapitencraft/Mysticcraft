package net.kapitencraft.mysticcraft.gui.browse;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.gui.screen.IBackgroundScreen;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BrowserScreen extends Screen implements IBackgroundScreen {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("mysticcraft:textures/gui/browsable_background");
    private final IBrowsable browsable;
    private int leftPos;
    private int topPos;

    public BrowserScreen(IBrowsable browsable) {
        super(browsable.getName());
        this.browsable = browsable;
    }

    @Override
    public void renderBackground(@NotNull PoseStack pPoseStack) {
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        GuiComponent.blit(pPoseStack, this.leftPos, this.topPos, 0, 0, 0, getImageWidth(),  getImageHeight(), getImageWidth(), getImageHeight());
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.browsable.render(pPoseStack, this.leftPos, this.topPos, pMouseX, pMouseY, this.minecraft);
    }

    @Override
    protected void init() {
        this.leftPos = this.leftPos(this.width);
        this.topPos = this.topPos(this.height);
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
