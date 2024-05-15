package net.kapitencraft.mysticcraft.gui.widgets.menu.drop_down.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.gui.widgets.menu.drop_down.DropDownMenu;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class ButtonElement extends Element {
    private final Runnable runnable;

    public ButtonElement(@Nullable ListElement parent, DropDownMenu menu, Component name, Runnable runnable) {
        super(parent, menu, name);
        this.runnable = runnable;
    }

    @Override
    public void click(float relativeX, float relativeY) {
        this.runnable.run();
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
    }
}
