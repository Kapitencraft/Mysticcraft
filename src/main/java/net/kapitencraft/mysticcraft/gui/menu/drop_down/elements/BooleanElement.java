package net.kapitencraft.mysticcraft.gui.menu.drop_down.elements;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.gui.menu.drop_down.DropDownMenu;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BooleanElement extends Element {
    private static final ResourceLocation CHECK_MARK = new ResourceLocation("minecraft:assets/textures/gui/checkmark.png");
    private boolean selected = false;
    protected BooleanElement(DropDownMenu menu, Component name) {
        super(menu, name);
    }

    public boolean selected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void render() {
        if (selected()) {
            int checkBoxX = x + super.width() + 1;
            int checkBoxY = y + 1;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, CHECK_MARK);
            GuiComponent.blit(new PoseStack(), checkBoxX, checkBoxY, 0, 0, 0, 8, 8, 8, 8);
        }
    }

    @Override
    protected int width() {
        return super.width() + 9;
    }

    @Override
    public void click(float relativeX, float relativeY) {
        this.setSelected(!this.selected());
    }
}