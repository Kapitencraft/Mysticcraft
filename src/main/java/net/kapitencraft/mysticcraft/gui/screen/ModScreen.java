package net.kapitencraft.mysticcraft.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.gui.ModMenu;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class ModScreen<K extends ICapabilityProvider, T extends ModMenu<K>> extends AbstractContainerScreen<T> {
    public ModScreen(T p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }
    private final List<HoverTooltip> hoverTooltips = new ArrayList<>();

    protected abstract String getTextureName();

    protected void addHoverTooltip(HoverTooltip tooltip) {
        this.hoverTooltips.add(tooltip);
    }

    protected void addHoverTooltipAndImgButton(HoverTooltip tooltip, ResourceLocation location, Button.OnPress onPress) {
        this.hoverTooltips.add(tooltip);
        this.addRenderableWidget(tooltip.createButton(location, leftPos, topPos, onPress));
    }


    @Override
    protected void init() {
        super.init();
        this.hoverTooltips.clear(); //ensure emptying them to reload them from the init calls from above
    }

    @Override
    protected void containerTick() {
        tickRequirements();
    }

    public void tickRequirements() {
        Stream<? extends HoverScreenUpdatable<?>> stream = this.hoverTooltips.stream().filter(tooltip -> tooltip instanceof HoverScreenUpdatable<?>).map(tooltip -> (HoverScreenUpdatable<?>) tooltip).filter(HoverScreenUpdatable::changed);
        List<HoverScreenUpdatable<?>> list = (List<HoverScreenUpdatable<?>>) stream.toList();
        list.forEach(HoverScreenUpdatable::tick); //updating all displays that changed
        if (!list.isEmpty()) rebuildWidgets(); //rebuild all displays if any changed
    }

    @Override
    protected void renderBg(@NotNull PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, MysticcraftMod.res("textures/gui/" + getTextureName() + ".png"));
        blit(pPoseStack, this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
        RenderSystem.disableBlend();
    }

    @Override
    public void render(@NotNull PoseStack stack, int mouseX, int mouseY, float delta) {
        renderBackground(stack);
        super.render(stack, mouseX, mouseY, delta);
        renderTooltip(stack, mouseX, mouseY);
        if (this.menu.getCarried().isEmpty() && this.hoveredSlot == null) {
            hoverTooltips.forEach(tooltip -> {
                if (tooltip.matches(this.leftPos, this.topPos, mouseX, mouseY)) {
                    this.renderComponentTooltip(stack, tooltip.getText(), mouseX, mouseY);
                }
            });
        }
    }
}