package net.kapitencraft.mysticcraft.client.render.box;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec2;
import org.lwjgl.glfw.GLFW;

public class ScreenDebugBox extends InteractiveBox {
    public ScreenDebugBox() {
        super(Vec2.ZERO, Vec2.ZERO, GLFW.GLFW_ARROW_CURSOR, new PoseStack(), 0xffffffff);
    }

    @Override
    public void render(double mouseX, double mouseY) {
        Minecraft.getInstance().font.draw(new PoseStack(), "Mouse location: " + mouseX + ", " + mouseY, (float) mouseX, (float) mouseY, -1);
    }

    @Override
    public boolean isHovering(double x, double y) {
        return false;
    }

    @Override
    public void click(double x, double y) {
    }
}
