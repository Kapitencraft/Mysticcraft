package net.kapitencraft.mysticcraft.client.render.overlay.box;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec2;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ResizeBox extends InteractiveBox {
    private final List<InteractiveBox> boxes = new ArrayList<>();

    private static final int boxColor = 0xFFFFFFFF;
    private static final int fillColor = 0x30FFFFFF;
    private double dragStartX;
    private double dragStartY;
    private InteractiveBox active;



    public ResizeBox(Vec2 start, Vec2 finish, PoseStack stack, float dotSize, float lineSize) {
        super(start, finish, GLFW.GLFW_RESIZE_ALL_CURSOR, stack, fillColor);
        Vec2 extra1 = new Vec2(start.x, finish.y);
        Vec2 extra2 = new Vec2(finish.x, start.y);
        boxes.addAll(
                List.of(
                        new InteractiveBox(stack, start, dotSize, boxColor, GLFW.GLFW_RESIZE_NWSE_CURSOR),
                        new InteractiveBox(stack, extra1, dotSize, boxColor, GLFW.GLFW_RESIZE_NESW_CURSOR),
                        new InteractiveBox(stack, extra2, dotSize, boxColor, GLFW.GLFW_RESIZE_NESW_CURSOR),
                        new InteractiveBox(stack, finish, dotSize, boxColor, GLFW.GLFW_RESIZE_NWSE_CURSOR),
                        new InteractiveBox(start.add(new Vec2(0, -lineSize)), extra1.add(new Vec2(0, lineSize)), GLFW.GLFW_RESIZE_NS_CURSOR, stack, boxColor),
                        new InteractiveBox(start.add(new Vec2(-lineSize, 0)), extra2.add(new Vec2(lineSize, 0)), GLFW.GLFW_RESIZE_EW_CURSOR, stack, boxColor),
                        new InteractiveBox(extra1.add(new Vec2(-lineSize, 0)), finish.add(new Vec2(lineSize, 0)), GLFW.GLFW_RESIZE_NS_CURSOR, stack, boxColor),
                        new InteractiveBox(extra2.add(new Vec2(0, -lineSize)), finish.add(new Vec2(0, lineSize)), GLFW.GLFW_RESIZE_EW_CURSOR, stack, boxColor)
                )
        );
    }

    @Override
    public void render(double mouseX, double mouseY) {
        super.render(mouseX, mouseY);
        boxes.forEach(box -> box.render(mouseX, mouseY));
        long windowId = Minecraft.getInstance().getWindow().getWindow();
        boxes.stream().filter(box -> box.isHovering(mouseX, mouseY))
                .map(RenderBox::getCursorType)
                .map(GLFW::glfwCreateStandardCursor)
                .findFirst() //expecting 1 but who knows...
                .ifPresentOrElse(CollectionHelper.biUsage(windowId, GLFW::glfwSetCursor),
                        ()-> GLFW.glfwSetCursor(windowId, GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR))
                );
    }

    @Override
    public boolean isHovering(double x, double y) {
        return super.isHovering(x, y) || boxes.stream().anyMatch(box -> box.isHovering(x, y));
    }

    @Override
    public void click(double x, double y) {
        boxes.stream().filter(box -> box.isHovering(x, y)).findFirst()
                .ifPresentOrElse(box -> this.setActive(box, x, y), ()-> {});
        super.click(x, y);
    }

    private void setActive(InteractiveBox box, double dragStartX, double dragStartY) {
        this.active = box;
        this.dragStartX = dragStartX;
        this.dragStartY = dragStartY;
    }
}
