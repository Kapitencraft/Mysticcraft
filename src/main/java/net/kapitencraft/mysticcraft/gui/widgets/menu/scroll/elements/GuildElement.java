package net.kapitencraft.mysticcraft.gui.widgets.menu.scroll.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.BannerPatternRenderer;
import net.kapitencraft.mysticcraft.guild.Guild;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class GuildElement extends ScrollElement {
    private final Guild guild;

    public GuildElement(Guild guild) {
        this.guild = guild;
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        BannerPatternRenderer.renderBannerFromStack(pPoseStack, this.x + 1, this.y + 1, guild.getBanner(), 18);
        Component name = guild.getName();
        GuiComponent.drawCenteredString(pPoseStack, Minecraft.getInstance().font, name, this.x + 11, this.y + 1, -1);
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 20;
    }
}
