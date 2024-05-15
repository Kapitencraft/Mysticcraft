package net.kapitencraft.mysticcraft.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class ExtendedEditBox extends EditBox {
    private final Predicate<String> textValid;

    public ExtendedEditBox(Font pFont, int pX, int pY, int pWidth, int pHeight, Component pMessage, Predicate<String> textValid) {
        super(pFont, pX, pY, pWidth, pHeight, pMessage);
        this.textValid = textValid;
    }

    public ExtendedEditBox(Font pFont, int pX, int pY, int pWidth, int pHeight, @Nullable ExtendedEditBox toCopy, Component pMessage, Predicate<String> textValid) {
        super(pFont, pX, pY, pWidth, pHeight, toCopy, pMessage);
        this.textValid = toCopy == null ? textValid : toCopy.textValid;
    }

    public void renderButton(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.isVisible()) {
            if (this.isBordered()) {
                int i = this.isFocused() ? -1 : -6250336;
                fill(pPoseStack, this.getX() - 1, this.getY() - 1, this.getX() + this.width + 1, this.getY() + this.height + 1, i);
                fill(pPoseStack, this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, -16777216);
            }
            int i2;
            if (!this.value.isEmpty() && !this.textValid.test(this.value)) {
                i2 = 0xFFFF0000;
            } else {
                i2 = this.isEditable ? this.textColor : this.textColorUneditable;
            }
            int j = this.cursorPos - this.displayPos;
            int k = this.highlightPos - this.displayPos;
            String s = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), this.getInnerWidth());
            boolean flag = j >= 0 && j <= s.length();
            boolean flag1 = this.isFocused() && this.frame / 6 % 2 == 0 && flag;
            int l = this.bordered ? this.getX() + 4 : this.getX();
            int i1 = this.bordered ? this.getY() + (this.height - 8) / 2 : this.getY();
            int j1 = l;
            if (k > s.length()) {
                k = s.length();
            }

            if (!s.isEmpty()) {
                String s1 = flag ? s.substring(0, j) : s;
                j1 = this.font.drawShadow(pPoseStack, this.formatter.apply(s1, this.displayPos), (float)l, (float)i1, i2);
            }

            boolean flag2 = this.cursorPos < this.value.length() || this.value.length() >= this.getMaxLength();
            int k1 = j1;
            if (!flag) {
                k1 = j > 0 ? l + this.width : l;
            } else if (flag2) {
                k1 = j1 - 1;
                --j1;
            }

            if (!s.isEmpty() && flag && j < s.length()) {
                this.font.drawShadow(pPoseStack, this.formatter.apply(s.substring(j), this.cursorPos), (float)j1, (float)i1, 0xFFD4D4D4);
            }

            if (this.hint != null && s.isEmpty() && !this.isFocused()) {
                this.font.drawShadow(pPoseStack, this.hint, (float)j1, (float)i1, i2);
            }

            if (!flag2 && this.suggestion != null) {
                this.font.drawShadow(pPoseStack, this.suggestion, (float)(k1 - 1), (float)i1, -8355712);
            }

            if (flag1) {
                if (flag2) {
                    GuiComponent.fill(pPoseStack, k1, i1 - 1, k1 + 1, i1 + 1 + 9, -3092272);
                } else {
                    this.font.drawShadow(pPoseStack, "_", (float)k1, (float)i1, i2);
                }
            }

            if (k != j) {
                int l1 = l + this.font.width(s.substring(0, k));
                this.renderHighlight(k1, i1 - 1, l1 - 1, i1 + 1 + 9);
            }

        }
    }
}
