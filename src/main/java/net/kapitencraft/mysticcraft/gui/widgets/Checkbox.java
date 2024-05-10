package net.kapitencraft.mysticcraft.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.UsefulTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class Checkbox extends Button {
    private final boolean checkBoxLeft;
    boolean checked;

    public Checkbox(int pX, int pY, Component pMessage, boolean checkBoxLeft, Font font, boolean checked) {
        super(pX, pY, 12 + font.width(pMessage), 10, pMessage, pButton -> {}, Supplier::get);
        this.checkBoxLeft = checkBoxLeft;
        this.checked = checked;
    }
    @Override
    protected boolean clicked(double pMouseX, double pMouseY) {
        return Math.abs(pMouseY - this.getY()) <= 10 && checkBoxLeft ? Math.abs(pMouseX - this.getX()) <= 10 : pMouseX - this.getX() >= this.getWidth() - 10;
    }

    @Override
    public void renderButton(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        int checkBoxX = this.checkBoxLeft ? this.getX() : this.getX() + this.getWidth() - 10;
        int checkBoxY = this.getY();
        GuiComponent.fill(pPoseStack, checkBoxX, checkBoxY, checkBoxX + 10, checkBoxY + 10, 0xFF000000);
        GuiComponent.fill(pPoseStack, checkBoxX + 1, checkBoxY + 1, checkBoxX + 9, checkBoxY + 9, -1);
        if (checked) UsefulTextures.renderCheckMark(pPoseStack, checkBoxX + 1, checkBoxY + 1);
        int textX = this.checkBoxLeft ? this.getX() + 11 : this.getX() + this.getWidth() - 11;
        Minecraft.getInstance().font.draw(pPoseStack, this.getMessage(), textX, this.getY() + 1, -1);
    }

    @Override
    public void onClick(double pMouseX, double pMouseY) {
        this.setChecked(!this.isChecked());
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean wasChecked) {
        checked = wasChecked;
    }
}
