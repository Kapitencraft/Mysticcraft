package net.kapitencraft.mysticcraft.gui.gemstone_grinder;

import com.mojang.blaze3d.systems.RenderSystem;
import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneSlot;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GemstoneGrinderScreen extends AbstractContainerScreen<GemstoneGrinderMenu> {

    public GemstoneGrinderScreen(GemstoneGrinderMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageHeight = 166;
        this.imageWidth = 176;
    }

    @Override
    protected void renderTooltip(@NotNull GuiGraphics graphics, int pX, int pY) {
        super.renderTooltip(graphics, pX, pY);
        if (this.hoveredSlot != null) {
            if (!this.hoveredSlot.hasItem()) {
                if (MathHelper.isBetween(36, 40, this.hoveredSlot.index)) {
                    ItemStack target = this.menu.getApplicable();
                    int slotId = menu.getSlotForItem(this.hoveredSlot.index - 36);
                    if (slotId < 0 || slotId > GemstoneGrinderMenu.MAX_GEMSTONE_SLOTS) return;
                    CapabilityHelper.exeCapability(target, CapabilityHelper.GEMSTONE, handler -> {
                        GemstoneSlot slot = handler.getSlots()[slotId];
                        graphics.renderTooltip(this.font, slot.createPossibleList(), pX, pY);
                    });
                }
            }
        }
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(graphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(graphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        graphics.blit(MysticcraftMod.res("textures/gui/gemstone_grinder_gui.png"), this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
        RenderSystem.disableBlend();
    }

}
