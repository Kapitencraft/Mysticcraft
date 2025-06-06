package net.kapitencraft.mysticcraft.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.gui.screen.tooltip.HoverTooltip;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class SimpleScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> implements IModScreen {
    private final List<HoverTooltip> hoverTooltips = new ArrayList<>();


    public SimpleScreen(T pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public void addHoverTooltip(HoverTooltip tooltip) {
        this.hoverTooltips.add(tooltip);
    }

    @Override
    protected void init() {
        super.init();
        this.hoverTooltips.clear(); //ensure emptying them to reload them from the init calls from above
    }

    protected abstract String getTextureName();

    @Override
    protected void containerTick() {
        tickRequirements();
    }

    private void tickRequirements() {
        Stream<? extends HoverScreenUpdatable<?>> stream = this.hoverTooltips.stream().filter(tooltip -> tooltip instanceof HoverScreenUpdatable<?>).map(tooltip -> (HoverScreenUpdatable<?>) tooltip).filter(HoverScreenUpdatable::changed);
        List<HoverScreenUpdatable<?>> list = (List<HoverScreenUpdatable<?>>) stream.toList();
        list.forEach(HoverScreenUpdatable::tick); //updating all displays that changed
        if (!list.isEmpty()) rebuildWidgets(); //rebuild all displays if any changed
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        graphics.blit(MysticcraftMod.res("textures/gui/" + getTextureName() + ".png"), this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
        RenderSystem.disableBlend();
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);
        renderTooltip(graphics, mouseX, mouseY);
        if (this.menu.getCarried().isEmpty() && this.hoveredSlot == null) {
            hoverTooltips.forEach(tooltip -> {
                if (tooltip.matches(this.leftPos, this.topPos, mouseX, mouseY)) {
                    graphics.renderComponentTooltip(this.font, tooltip.getText(), mouseX, mouseY);
                }
            });
        }
    }

    protected void addHoverTooltipAndImgButton(HoverTooltip tooltip, ResourceLocation location, Button.OnPress onPress) {
        this.hoverTooltips.add(tooltip);
        this.addRenderableWidget(tooltip.createButton(location, leftPos, topPos, onPress));
    }
}
