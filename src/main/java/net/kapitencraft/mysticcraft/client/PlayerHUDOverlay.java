package net.kapitencraft.mysticcraft.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class PlayerHUDOverlay {

    public static final IGuiOverlay Player_HUD = ((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth / 2;
        int y = screenHeight;
    });
}
