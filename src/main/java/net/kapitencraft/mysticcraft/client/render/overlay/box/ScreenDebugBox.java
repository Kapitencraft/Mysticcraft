package net.kapitencraft.mysticcraft.client.render.overlay.box;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec2;
import org.lwjgl.glfw.GLFW;

public class ScreenDebugBox extends InteractiveBox {
    public ScreenDebugBox() {
        super(Vec2.ZERO, Vec2.ZERO, GLFW.GLFW_ARROW_CURSOR, new PoseStack(), 0xffffffff, null);
    }

    @Override
    public void render(double mouseX, double mouseY) {
        PoseStack stack = new PoseStack();
        float screenX = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        float screenY = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        Minecraft.getInstance().font.draw(stack, "Mouse location: " + mouseX + ", " + mouseY, (float) mouseX, (float) mouseY, -1);
        Minecraft.getInstance().font.draw(stack, "Screen Height: " + screenX + ", " + screenY, (float) mouseX, (float) (mouseY + 10), -1);
    }

    @Override
    public void move(Vec2 toAdd) {
    }

    @Override
    public boolean isHovering(double x, double y) {
        return false;
    }
}
