package net.kapitencraft.mysticcraft.client.render.box;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.phys.Vec2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ResizeBox extends InteractiveBox {
    private final List<InteractiveBox> boxes = new ArrayList<>();

    private static final int boxColor = 0xFFFFFFFF;
    private static final int fillColor = 0x30FFFFFF;


    public ResizeBox(Vec2 start, Vec2 finish, PoseStack stack, float dotSize, float lineSize) {
        super(start, finish, Cursor.MOVE_CURSOR, stack, fillColor);
        Vec2 extra1 = new Vec2(start.x, finish.y);
        Vec2 extra2 = new Vec2(finish.x, start.y);
        boxes.addAll(
                List.of(
                        new InteractiveBox(stack, start, dotSize, boxColor, Cursor.NW_RESIZE_CURSOR),
                        new InteractiveBox(stack, extra1, dotSize, boxColor, Cursor.SW_RESIZE_CURSOR),
                        new InteractiveBox(stack, extra2, dotSize, boxColor, Cursor.NE_RESIZE_CURSOR),
                        new InteractiveBox(stack, finish, dotSize, boxColor, Cursor.SE_RESIZE_CURSOR),
                        new InteractiveBox(start.add(new Vec2(0, -lineSize)), extra1.add(new Vec2(0, lineSize)), Cursor.W_RESIZE_CURSOR, stack, boxColor),
                        new InteractiveBox(start.add(new Vec2(-lineSize, 0)), extra2.add(new Vec2(lineSize, 0)), Cursor.N_RESIZE_CURSOR, stack, boxColor),
                        new InteractiveBox(extra1.add(new Vec2(-lineSize, 0)), finish.add(new Vec2(lineSize, 0)), Cursor.S_RESIZE_CURSOR, stack, boxColor),
                        new InteractiveBox(extra2.add(new Vec2(0, -lineSize)), finish.add(new Vec2(0, lineSize)), Cursor.E_RESIZE_CURSOR, stack, boxColor)
                )
        );
    }

    @Override
    public boolean isClicked(float x, float y) {
        return super.isClicked(x, y) || boxes.stream().anyMatch(box -> box.isClicked(x, y));
    }
}
