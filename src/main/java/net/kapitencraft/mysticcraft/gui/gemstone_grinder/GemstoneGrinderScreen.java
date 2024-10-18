package net.kapitencraft.mysticcraft.gui.gemstone_grinder;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneSlot;
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
    protected void renderTooltip(@NotNull PoseStack pPoseStack, int pX, int pY) {
        super.renderTooltip(pPoseStack, pX, pY);
        if (this.hoveredSlot != null) {
            if (!this.hoveredSlot.hasItem()) {
                if (MathHelper.isBetween(36, 40, this.hoveredSlot.index)) {
                    ItemStack target = this.menu.getApplicable();
                    int slotId = menu.getSlotForItem(this.hoveredSlot.index - 36);
                    if (slotId < 0 || slotId > GemstoneGrinderMenu.MAX_GEMSTONE_SLOTS) return;
                    CapabilityHelper.exeCapability(target, CapabilityHelper.GEMSTONE, handler -> {
                        GemstoneSlot slot = handler.getSlots()[slotId];
                        this.renderTooltip(pPoseStack, slot.createPossibleList(), pX, pY);
                    });
                }
            }
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(@NotNull PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, MysticcraftMod.res("textures/gui/gemstone_grinder_gui.png"));
        blit(pPoseStack, this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
        RenderSystem.disableBlend();
    }

}
