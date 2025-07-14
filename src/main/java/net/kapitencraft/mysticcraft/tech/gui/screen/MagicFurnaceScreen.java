package net.kapitencraft.mysticcraft.tech.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.kapitencraft.kap_lib.client.gui.screen.BlockEntityScreen;
import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.tech.block.entity.MagicFurnaceBlockEntity;
import net.kapitencraft.mysticcraft.tech.gui.menu.MagicFurnaceMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class MagicFurnaceScreen extends BlockEntityScreen<MagicFurnaceBlockEntity, MagicFurnaceMenu> {
    public MagicFurnaceScreen(MagicFurnaceMenu p_97741_, Inventory p_97742_, Component title) {
        super(p_97741_, p_97742_, title);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        int i = leftPos;
        int j = topPos;

        graphics.blit(getTexture(), i, j, 0, 0, imageWidth, imageHeight, 256, 256);
        RenderSystem.disableBlend();
        graphics.fill(i + 158, j + 75 - this.menu.getManaTankScaledFillPercentage(), i + 168, j + 75, 0xFF005FFF);

        int l = this.menu.getBurnProgress();
        graphics.blit(getTexture(), i + 79, j + 34, 176, 14, l + 1, 16);
    }

    @Override
    public ResourceLocation getTexture() {
        return MysticcraftMod.res("textures/gui/magic_furnace.png");
    }

    @Override
    protected void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY) {
        super.renderTooltip(pGuiGraphics, pX, pY);
        if (this.menu.getCarried().isEmpty() && MathHelper.is2dBetween(pX, pY, this.leftPos + 158, this.topPos + 6, this.leftPos + 168, this.topPos + 75)) {
            pGuiGraphics.renderTooltip(this.font, this.menu.getManaTooltip(), pX, pY);
        }
    }
}
