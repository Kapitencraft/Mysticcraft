package net.kapitencraft.mysticcraft.client.render.box;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.phys.Vec2;

public class InteractiveBox extends RenderBox {
    public InteractiveBox(Vec2 start, Vec2 finish, int cursorType, PoseStack stack, int color) {
        super(start, finish, cursorType, stack, color);
    }

    public InteractiveBox(PoseStack stack, Vec2 center, float size, int fillColor, int cursorType) {
        this(center.add(-size), center.add(size), cursorType, stack, fillColor);
    }


    public boolean isHovering(double x, double y) {
        return check(start.x, finish.x, x) && check(start.y, finish.y, y);
    }

    private static boolean check(float s, float f, double t) {
        return s < f ? s < t && t < f : s > t && t > f;
    }

    public void click(double x, double y) {
    }

    public void release() {

    }
}
