package net.kapitencraft.mysticcraft.client.render.overlay.box;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.render.overlay.holder.RenderHolder;
import net.minecraft.world.phys.Vec2;

public class InteractiveBox extends RenderBox {
    protected InteractiveBox(Vec2 start, Vec2 finish, int cursorType, PoseStack stack, int color, RenderHolder dedicatedHolder) {
        super(start, finish, cursorType, stack, color, dedicatedHolder);
    }

    public static InteractiveBox line(Vec2 start, Vec2 finish, float lineW, int color, int cursorType, PoseStack stack, RenderHolder dedicated) {
        boolean horizontal = start.x == finish.x;
        Vec2 finalStart = new Vec2(horizontal ? start.x - lineW : start.x, horizontal ? start.y : start.y - lineW);
        Vec2 finalFinish = new Vec2(horizontal ? finish.x + lineW : finish.x, horizontal ? finish.y : finish.y  + lineW);
        return new InteractiveBox(finalStart, finalFinish, cursorType, stack, color, dedicated);
    }

    public static InteractiveBox centeredQuad(Vec2 center, float xSize, float ySize, int cursorType, int fillColor, PoseStack stack, RenderHolder dedicated) {
        return new InteractiveBox(center.add(new Vec2(-xSize, -ySize)), center.add(new Vec2(xSize, ySize)), cursorType, stack, fillColor, dedicated);
    }

    public static InteractiveBox square(Vec2 center, float size, int fillColor, int cursorType, PoseStack stack, RenderHolder dedicated) {
        return centeredQuad(center, size, size, cursorType, fillColor, stack, dedicated);
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
