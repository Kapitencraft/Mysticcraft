package net.kapitencraft.mysticcraft.gui.screen.menu.drop_down.elements;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.UsefulTextures;
import net.kapitencraft.mysticcraft.gui.screen.menu.IValueModifierElement;
import net.kapitencraft.mysticcraft.gui.screen.menu.drop_down.DropDownMenu;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BooleanElement extends Element implements IValueModifierElement<BooleanElement, Boolean> {

    private final Consumer<Boolean> onChange;
    private boolean selected = false;
    protected BooleanElement(ListElement parent, DropDownMenu menu, Component name, Consumer<Boolean> onChange) {
        super(parent, menu, name);
        this.onChange = onChange;
    }

    public boolean selected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        this.onChange.accept(this.selected);
    }

    @Override
    protected int width() {
        return super.width() + 9;
    }

    @Override
    public void click(float relativeX, float relativeY) {
        this.setSelected(!this.selected());
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (selected()) {
            int checkBoxX = x + this.effectiveWidth() - 9;
            int checkBoxY = y + 1;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, UsefulTextures.CHECK_MARK);
            GuiComponent.blit(pPoseStack, checkBoxX, checkBoxY, 0, 0, 0, 8, 8, 8, 8);
        }
    }

    @Override
    public void setValue(Boolean value) {
        this.selected = value;
    }
}