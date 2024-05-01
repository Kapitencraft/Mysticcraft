package net.kapitencraft.mysticcraft.gui.browse;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.gui.screen.DefaultBackgroundScreen;
import net.kapitencraft.mysticcraft.gui.screen.menu.IMenuElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class BrowserScreen<T extends IBrowsable> extends DefaultBackgroundScreen {
    private final List<IMenuElement> browseables = new ArrayList<>();
    protected final T browsable;

    public BrowserScreen(T browsable) {
        super(browsable.getName());
        this.browsable = browsable;
    }


    private byte lastDotCount = 0;

    protected void renderFetchingString(PoseStack poseStack) {
        Font font = Minecraft.getInstance().font;
        MutableComponent toShow = Component.translatable("gui.fetching");
        GuiComponent.drawCenteredString(poseStack, font, toShow.append(" " + ".".repeat(Math.max(0, lastDotCount++))), (int) (this.leftPos + this.getImageWidth() / 2.), (int) (this.topPos + (this.getImageHeight() / 2.)), -1);
        if (lastDotCount == 3) lastDotCount = 0;
    }

    @Override
    public @NotNull List<IMenuElement> children() {
        return this.browseables;
    }

}
