package net.kapitencraft.mysticcraft.tech.gui.screen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.tech.block.entity.AbstractTurretBlockEntity;
import net.kapitencraft.mysticcraft.tech.gui.menu.AbstractTurretMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractTurretScreen<BE extends AbstractTurretBlockEntity, M extends AbstractTurretMenu<BE>> extends UpgradableBEScreen<BE, M> {
    private static final ResourceLocation TARGET_SELECT_BACKGROUND = MysticcraftMod.res("textures/gui/background.png");
    private static final ResourceLocation TARGET_SELECT_UI = MysticcraftMod.res("textures/gui/target.png");
    private boolean targetSelectorVisible = false;
    private final AbstractTurretBlockEntity.TargetSelector targetSelector;

    public AbstractTurretScreen(M menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.targetSelector = menu.getSelector();
    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics, int mouseX, int mouseY, float delta) {
        super.renderBackground(pGuiGraphics, mouseX, mouseY, delta);

        if (targetSelectorVisible) {
            AbstractTurretBlockEntity.TargetPriority[] priorities = this.targetSelector.getOrders();
            renderUpgradeBackground(pGuiGraphics, 62 + priorities.length * 12, 70);
            for (int i = 0; i < priorities.length; i++) {
                AbstractTurretBlockEntity.TargetPriority priority = priorities[i];
                int y = this.topPos + 28 + i * 14;
                pGuiGraphics.drawString(this.font, "<", this.leftPos - 94, y, 0xFFFFFF);
                pGuiGraphics.drawCenteredString(this.font, Component.translatable(priority.getTranslationKey()), this.leftPos - 47, y, 0xFFFFFF);
                pGuiGraphics.drawString(this.font, ">", this.leftPos - 6, y, 0xFFFFFF);
            }
        } else {
            renderUpgradeBackground(pGuiGraphics, 20, 20);
        }
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(this.leftPos - 12, this.topPos + 14, 0);
        pGuiGraphics.pose().scale(.5f, .5f, 1);
        pGuiGraphics.blit(TARGET_SELECT_UI, 0, 0, 0, 0, 16, 16, 16, 16);
        pGuiGraphics.pose().popPose();
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (pMouseX > this.leftPos - 12 && pMouseX < this.leftPos - 4
                && pMouseY > this.topPos + 14 && pMouseY < this.topPos + 22) {
            this.targetSelectorVisible = !this.targetSelectorVisible;
        }
        AbstractTurretBlockEntity.TargetPriority[] orders = this.targetSelector.getOrders();
        for (int i = 0; i < orders.length; i++) {
            int y = this.topPos + 28 + i * 14;
            if (pMouseX > this.leftPos - 94 && pMouseX < this.leftPos - 88
                    && pMouseY > y && pMouseY < y + 12) {
                this.targetSelector.cycleOrder(i, false);
                return true;
            } else if (pMouseX > this.leftPos - 6 && pMouseX < this.leftPos
                    && pMouseY > y && pMouseY < y + 12) {
                this.targetSelector.cycleOrder(i, true);
                return true;
            }
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    private void renderUpgradeBackground(GuiGraphics pGuiGraphics, int width, int height) {
        pGuiGraphics.blit(TARGET_SELECT_BACKGROUND, this.leftPos - width, this.topPos + 8, 0, 0, width, 4, 219, 180);
        pGuiGraphics.blit(TARGET_SELECT_BACKGROUND, this.leftPos - width, this.topPos + 12, 0, 4, width, height - 8, 219, 180);
        pGuiGraphics.blit(TARGET_SELECT_BACKGROUND, this.leftPos - width, this.topPos + 12 + height - 8, 0, 176, width, 4, 219, 180);
    }
}
