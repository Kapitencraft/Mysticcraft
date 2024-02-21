package net.kapitencraft.mysticcraft.client.render.box;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.phys.Vec2;

public class RenderBox {
    protected final Vec2 start;
    protected final Vec2 finish;
    private final int cursorType;
    private final PoseStack stack;
    private final int color;

    public RenderBox(Vec2 start, Vec2 finish, int cursorType, PoseStack stack, int color) {
        this.start = start;
        this.finish = finish;
        this.cursorType = cursorType;
        this.stack = stack;
        this.color = color;
    }

    public int getCursorType() {
        return cursorType;
    }

    public void render(double mouseX, double mouseY) {
        Gui.fill(stack, (int) start.x, (int) start.y, (int) finish.x, (int) finish.y, color);
    }
}
