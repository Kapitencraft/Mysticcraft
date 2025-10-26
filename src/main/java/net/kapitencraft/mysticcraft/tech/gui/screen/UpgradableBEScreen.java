package net.kapitencraft.mysticcraft.tech.gui.screen;

import net.kapitencraft.kap_lib.client.gui.screen.BlockEntityScreen;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.network.packets.C2S.ToggleUpgradeVisibilityPacket;
import net.kapitencraft.mysticcraft.tech.block.UpgradableBlockEntity;
import net.kapitencraft.mysticcraft.tech.gui.menu.UpgradableBEMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;

public abstract class UpgradableBEScreen<BE extends UpgradableBlockEntity, M extends UpgradableBEMenu<BE>> extends BlockEntityScreen<BE, M> {
    private static final ResourceLocation UPGRADE_UI_BACKGROUND = MysticcraftMod.res("textures/gui/blue_background.png");
    private static final ResourceLocation SLOT_BACKGROUND = MysticcraftMod.res("textures/gui/slot_background_blue.png");
    private static final ResourceLocation UPGRADE = MysticcraftMod.res("textures/gui/upgrade_ui.png");

    public UpgradableBEScreen(M menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        if (this.menu.doesShowUpgrades()) {
            int pHeight = 18 * Mth.ceil(menu.getCapabilityProvider().upgradeSlots() / 2f) + 28;
            renderUpgradeBackground(guiGraphics, 50, pHeight);
            for (int i = 0; i < menu.getCapabilityProvider().upgradeSlots(); i++) {
                guiGraphics.blit(SLOT_BACKGROUND, this.leftPos + 183 + i % 2 * 18, this.topPos + 30 + i / 2 * 18, 0, 0, 18, 18, 18, 18);
            }
        } else {
            renderUpgradeBackground(guiGraphics, 20, 20);
        }
        guiGraphics.blit(UPGRADE, this.leftPos + this.imageWidth + 4, this.topPos + 14, 0, 0, 8, 8, 8, 8);
    }

    private void renderUpgradeBackground(GuiGraphics pGuiGraphics, int width, int height) {
        pGuiGraphics.blit(UPGRADE_UI_BACKGROUND, this.leftPos + this.imageWidth, this.topPos + 8, 219 - width, 0, width, 4, 219, 180);
        pGuiGraphics.blit(UPGRADE_UI_BACKGROUND, this.leftPos + this.imageWidth, this.topPos + 12, 219 - width, 4, width, height - 8, 219, 180);
        pGuiGraphics.blit(UPGRADE_UI_BACKGROUND, this.leftPos + this.imageWidth, this.topPos + 12 + height - 8, 219 - width, 176, width, 4, 219, 180);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (pMouseX > this.leftPos + this.imageWidth + 2 && pMouseX < this.leftPos + this.imageWidth + 10
                && pMouseY > this.topPos + 12 && pMouseY < this.topPos + 20) {
            this.toggleUpgrades();
            return true;
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    private void toggleUpgrades() {
        boolean showUpgrades = this.menu.doesShowUpgrades();
        this.menu.setShowUpgrades(!showUpgrades);
        PacketDistributor.sendToServer(new ToggleUpgradeVisibilityPacket());
    }
}
