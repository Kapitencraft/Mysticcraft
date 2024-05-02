package net.kapitencraft.mysticcraft.gui.screen.menu.scroll.elements;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.guild.requests.GuildDataRequestable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GuildPlayerElement extends ScrollElement {
    private final ResourceLocation skin;
    private final String name;

    public GuildPlayerElement(PlayerInfo info) {
        this.name = info.getProfile().getName();
        this.skin = info.getSkinLocation();
    }

    public GuildPlayerElement(GuildDataRequestable.GuildPlayerData data) {
        this.name = data.getName();
        this.skin = data.getSkin();
    }


    @Override
    public int getWidth() {
        return 12 + Minecraft.getInstance().font.width(this.name);
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShaderTexture(0, this.skin);
        PlayerFaceRenderer.draw(pPoseStack, this.x + 1, this.y + 1, 8);
        Minecraft.getInstance().font.draw(pPoseStack, this.name, this.x + 10, this.y + 1, -1);
    }
}
