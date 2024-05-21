package net.kapitencraft.mysticcraft.gui.gemstone_grinder;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.block.entity.GemstoneGrinderBlockEntity;
import net.kapitencraft.mysticcraft.gui.screen.ModScreen;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneSlot;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GemstoneGrinderScreen extends ModScreen<GemstoneGrinderBlockEntity, GemstoneGrinderMenu> {

    public GemstoneGrinderScreen(GemstoneGrinderMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageHeight = 166;
        this.imageWidth = 176;
    }

    @Override
    protected String getTextureName() {
        return "gemstone_grinder_gui";
    }

    @Override
    protected void renderTooltip(@NotNull PoseStack pPoseStack, int pX, int pY) {
        super.renderTooltip(pPoseStack, pX, pY);
        if (this.hoveredSlot != null) {
            if (!this.hoveredSlot.hasItem()) {
                if (MathHelper.isBetween(36, 40, this.hoveredSlot.index)) {
                    GemstoneGrinderBlockEntity blockEntity = this.menu.getCapabilityProvider();
                    ItemStack target = blockEntity.itemHandler.getApplicable();
                    int slotId = blockEntity.getSlotForItem(this.hoveredSlot.index - 35);
                    if (slotId < 0 || slotId > 5) return;
                    CapabilityHelper.exeCapability(target, CapabilityHelper.GEMSTONE, handler -> {
                        GemstoneSlot slot = handler.getSlots()[slotId];
                        this.renderTooltip(pPoseStack, slot.createPossibleList(), pX, pY);
                    });
                }
            }
        }
    }
}
