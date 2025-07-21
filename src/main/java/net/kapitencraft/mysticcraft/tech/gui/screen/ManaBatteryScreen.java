package net.kapitencraft.mysticcraft.tech.gui.screen;

import net.kapitencraft.kap_lib.client.gui.screen.BlockEntityScreen;
import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.tech.block.entity.ManaBatteryBlockEntity;
import net.kapitencraft.mysticcraft.tech.gui.menu.ManaBatteryMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ManaBatteryScreen extends BlockEntityScreen<ManaBatteryBlockEntity, ManaBatteryMenu> {
    private static final ResourceLocation TEXTURE = MysticcraftMod.res("textures/gui/mana_battery.png");

    public ManaBatteryScreen(ManaBatteryMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
        super.renderBg(graphics, pPartialTick, pMouseX, pMouseY);
        graphics.fill(this.leftPos + 136, this.topPos + 79 - this.menu.getManaTankScaledFillPercentage(), this.leftPos + 168, this.topPos + 79, 0xFF005FFF);
    }

    @Override
    protected void renderTooltip(@NotNull GuiGraphics pGuiGraphics, int pX, int pY) {
        super.renderTooltip(pGuiGraphics, pX, pY);
        if (this.menu.getCarried().isEmpty() && MathHelper.is2dBetween(pX, pY, this.leftPos + 136, this.topPos + 5, this.leftPos + 168, this.topPos + 79)) {
            pGuiGraphics.renderTooltip(this.font, this.menu.getManaTooltip(), pX, pY);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}
