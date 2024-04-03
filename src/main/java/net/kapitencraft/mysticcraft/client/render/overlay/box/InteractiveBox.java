package net.kapitencraft.mysticcraft.client.render.overlay.box;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.render.overlay.holder.RenderHolder;
import net.minecraft.world.phys.Vec2;

public class InteractiveBox extends RenderBox {
    protected InteractiveBox(Vec2 start, Vec2 finish, int cursorType, PoseStack stack, int color, RenderHolder dedicatedHolder) {
        super(start, finish, cursorType, stack, color, dedicatedHolder);
    }

    public boolean isHovering(double x, double y) {
        return check(start.x, finish.x, x) && check(start.y, finish.y, y);
    }

    private static boolean check(float s, float f, double t) {
        return s < f ? s < t && t < f : s > t && t > f;
    }


    public void mouseDrag(double x, double y, int mouseType, double xChange, double yChange, double oldX, double oldY) {
    }
    public void mouseClick(double x, double y) {
    }

    public void mouseMove(double x, double y) {
    }

    public void mouseRelease(double x, double y) {
    }
}
