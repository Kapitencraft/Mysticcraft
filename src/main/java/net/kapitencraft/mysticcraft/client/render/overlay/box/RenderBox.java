package net.kapitencraft.mysticcraft.client.render.overlay.box;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.render.overlay.holder.RenderHolder;
import net.kapitencraft.mysticcraft.helpers.ClientHelper;
import net.minecraft.world.phys.Vec2;

public class RenderBox {
    public Vec2 start;
    protected Vec2 finish;
    private final int cursorType;
    protected final PoseStack stack;
    private final int color;
    public final RenderHolder dedicatedHolder;

    public RenderBox(Vec2 start, Vec2 finish, int cursorType, PoseStack stack, int color, RenderHolder dedicatedHolder) {
        this.start = start;
        this.finish = finish;
        this.cursorType = cursorType;
        this.stack = stack;
        this.color = color;
        this.dedicatedHolder = dedicatedHolder;
    }

    protected float width() {
        return Math.abs(this.finish.x - this.start.x);
    }

    protected float height() {
        return Math.abs(this.finish.y - this.start.y);
    }

    public void scale(float x, float y) {
        this.dedicatedHolder.scale(x, y);
    }

    public int getCursorType(double mouseX, double mouseY) {
        return cursorType;
    }

    public void move(Vec2 toAdd) {
        this.start = this.start.add(toAdd);
        this.finish = this.finish.add(toAdd);
    }

    public void render(double mouseX, double mouseY) {
        ClientHelper.fill(stack, start.x, start.y, finish.x, finish.y, color);
    }
}
