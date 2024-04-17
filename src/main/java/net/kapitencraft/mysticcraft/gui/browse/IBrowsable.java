package net.kapitencraft.mysticcraft.gui.browse;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public interface IBrowsable {
    void render(PoseStack stack, int leftPos, int topPos, int mouseX, int mouseY, Minecraft minecraft);

    Component getName();
}
