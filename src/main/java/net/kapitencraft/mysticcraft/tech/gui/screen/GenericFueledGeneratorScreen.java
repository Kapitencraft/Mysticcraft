package net.kapitencraft.mysticcraft.tech.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.kapitencraft.kap_lib.client.gui.screen.BlockEntityScreen;
import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.tech.block.entity.GenericFueledGeneratorBlockEntity;
import net.kapitencraft.mysticcraft.tech.gui.menu.GenericFueledGeneratorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class GenericFueledGeneratorScreen extends BlockEntityScreen<GenericFueledGeneratorBlockEntity, GenericFueledGeneratorMenu> {

    public GenericFueledGeneratorScreen(GenericFueledGeneratorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        graphics.blit(getTexture(), this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight, 256, 256);
        RenderSystem.disableBlend();
        if (this.menu.isLit()) {
            int k = this.menu.getLitProgress();
            graphics.blit(getTexture(), this.leftPos + 80, this.topPos + 23 + 12 - k, 176, 12 - k, 14, k + 1);
        }
        graphics.fill(this.leftPos + 158, this.topPos + 75 - this.menu.getManaTankScaledFillPercentage(), this.leftPos + 168, this.topPos + 75, 0xFF005FFF);
    }


    @Override
    protected void renderTooltip(GuiGraphics pGuiGraphics, int pX, int pY) {
        super.renderTooltip(pGuiGraphics, pX, pY);
        if (this.menu.getCarried().isEmpty() && MathHelper.is2dBetween(pX, pY, this.leftPos + 158, this.topPos + 6, this.leftPos + 168, this.topPos + 75)) {
            pGuiGraphics.renderTooltip(this.font, this.menu.getManaTooltip(), pX, pY);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return MysticcraftMod.res("textures/gui/generic_fueled_generator.png");
    }
}
