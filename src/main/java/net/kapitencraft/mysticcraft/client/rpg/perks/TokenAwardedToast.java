package net.kapitencraft.mysticcraft.client.rpg.perks;

import net.kapitencraft.mysticcraft.rpg.perks.PerkTree;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;

public class TokenAwardedToast implements Toast {
    private static final Component TITLE = Component.translatable("perks.toast.title");

    private final PerkTree tree;
    private final Component description;

    public TokenAwardedToast(PerkTree tree, int amount) {
        this.tree = tree;
        this.description = Component.translatable("perks.toast.description", amount, tree.getTitle());
    }

    @Override
    public Visibility render(GuiGraphics pGuiGraphics, ToastComponent pToastComponent, long pTimeSinceLastVisible) {
        pGuiGraphics.blit(TEXTURE, 0, 0, 0, 32, this.width(), this.height());

        pGuiGraphics.drawString(pToastComponent.getMinecraft().font, TITLE, 30, 7, -11534256, false);
        pGuiGraphics.drawString(pToastComponent.getMinecraft().font, description, 30, 18, -16777216, false);

        pGuiGraphics.renderFakeItem(this.tree.icon(), 8, 8);
        return (double)(pTimeSinceLastVisible) >= 5000.0D * pToastComponent.getNotificationDisplayTimeMultiplier() ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }
}
