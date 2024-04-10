package net.kapitencraft.mysticcraft.client.render.overlay.box;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.render.overlay.PositionHolder;
import net.kapitencraft.mysticcraft.client.render.overlay.holder.RenderHolder;
import net.kapitencraft.mysticcraft.gui.IMenuBuilder;
import net.kapitencraft.mysticcraft.gui.menu.Menu;
import net.kapitencraft.mysticcraft.gui.menu.drop_down.DropDownMenu;
import net.kapitencraft.mysticcraft.gui.menu.drop_down.elements.EnumElement;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResizeBox extends ResizeAccessBox implements IMenuBuilder {
    private final List<ResizeAccessBox> boxes = new ArrayList<>();
    private static final int boxColor = 0xFFFFFFFF;
    private static final int fillColor = 0x30FFFFFF;
    private ResizeAccessBox active;
    private boolean dirty = false;


    public ResizeBox(Vec2 start, Vec2 finish, RenderHolder dedicatedHolder) {
        super(start, finish, GLFW.GLFW_RESIZE_ALL_CURSOR, new PoseStack(), fillColor, dedicatedHolder, Type.C, null);
        fillBoxes();
    }

    private void fillBoxes() {
        this.boxes.clear();
        for (Type type : Type.values()) {
            if (type == Type.C) continue;
            boxes.add(new ResizeAccessBox(stack, boxColor, dedicatedHolder, type, this));
        }
        reapplyPosition();
    }

    @Override
    public void render(double mouseX, double mouseY) {
        if (dirty) {
            this.reapplyPosition();
            dirty = false;
        }
        super.render(mouseX, mouseY);
        boxes.forEach(resizeAccessBox -> resizeAccessBox.render(mouseX, mouseY));
    }

    @Override
    public int getCursorType(double mouseX, double mouseY) {
        return boxes.stream().filter(resizeAccessBox -> resizeAccessBox.isMouseOver(mouseX, mouseY))
                .map(box -> box.getCursorType(mouseX, mouseY))
                .findFirst().orElse(getSelfCursor(mouseX, mouseY));
    }

    private int getSelfCursor(double mouseX, double mouseY) {
        int cursorId = GLFW.GLFW_ARROW_CURSOR;
        if (this.isMouseOver(mouseX, mouseY)) cursorId = GLFW.GLFW_RESIZE_ALL_CURSOR;
        return cursorId;
    }

    public void move(Vec2 delta) {
        super.move(delta);
        this.dedicatedHolder.move(delta);
        this.boxes.forEach(resizeAccessBox -> resizeAccessBox.move(delta));
    }

    @Override
    protected void reapplyPosition() {
        this.boxes.forEach(ResizeAccessBox::reapplyPosition);
    }

    @Override
    public boolean mouseClicked(double x, double y, int clickId) {
        if (clickId == 1) {
            Menu menu = this.createMenu((int) x, (int) y);
            menu.show();
        }
        return super.mouseClicked(x, y, clickId);
    }


    @Override
    public boolean isMouseOver(double x, double y) {
        return super.isMouseOver(x, y) || boxes.stream().anyMatch(resizeAccessBox -> resizeAccessBox.isMouseOver(x, y));
    }

    @Override
    public boolean mouseDrag(double x, double y, int mouseType, double xChange, double yChange, double oldX, double oldY) {
        if (this.active == null) {
            boolean[] flag = new boolean[]{false};
            boxes.stream().filter(resizeAccessBox -> resizeAccessBox.isMouseOver(oldX, oldY)).findFirst()
                    .ifPresentOrElse(this::setActive, ()-> {
                        if (this.isMouseOver(oldX, oldY)) {
                            this.setActive(this);
                            flag[0] = true;
                        }
                    });
            return flag[0];
        } else {
            if (active == this) {
                this.move(new Vec2((float) xChange, (float) yChange));
            } else {
                float width = width();
                float height = height();
                float scaleX = (float) ((width + xChange) / width);
                float scaleY = (float) ((height + yChange) / height);
                this.scale(scaleX, scaleY);
            }
            this.dirty = true;
            return true;
        }
    }

    @Override
    public void scale(float x, float y) {
        float height = height();
        float width = width();
        float xChange = width * x - width;
        float yChange = height * x - height;
        Map<Axis, Boolean> map = getTypeAxes();
        if (map.containsKey(Axis.X) && map.get(Axis.X)) {
            xChange *= -1;
            this.dedicatedHolder.move(new Vec2(xChange, 0));
        }
        if (map.containsKey(Axis.Y) && map.get(Axis.Y)) {
            yChange *= -1;
            this.dedicatedHolder.move(new Vec2(0, yChange));
        }
        reapplyPosition(new Vec2(xChange, yChange));
        super.scale(x, y);
    }

    private void reapplyPosition(Vec2 change) {
        Map<Axis, Boolean> map = getTypeAxes();
        if (map.containsKey(Axis.X)) {
            if (map.get(Axis.X)) {
                this.start = this.start.add(new Vec2(change.x, 0));
            } else {
                this.finish = this.finish.add(new Vec2(change.x, 0));
            }
        }
        if (map.containsKey(Axis.Y)) {
            if (map.get(Axis.Y)) {
                this.start = this.start.add(new Vec2(0, change.y));
            } else {
                this.finish = this.finish.add(new Vec2(0, change.y));
            }
        }
        reapplyPosition();
    }

    private Map<Axis, Boolean> getTypeAxes() {
        return getType().axes;
    }

    protected Type getType() {
        return this.active.getType();
    }

    @Override
    public void mouseRelease(double x, double y) {
        this.active = null;
    }

    private void setActive(ResizeAccessBox box) {
        this.active = box;
    }

    @Override
    public Menu createMenu(int x, int y) {
        DropDownMenu menu = new DropDownMenu(x, y, this);
        menu.addElement(menu1 -> new EnumElement<>(menu1, Component.translatable("gui.alignment"), PositionHolder.Alignment.values(), PositionHolder.Alignment::getName));
        return menu;
    }

    public enum Type {
        N(Map.of(Axis.Y, true)),
        NE(Map.of(Axis.Y, true, Axis.X, false)),
        E(Map.of(Axis.X, false)),
        SE(Map.of(Axis.Y, false, Axis.X, false)),
        S(Map.of(Axis.Y, false)),
        SW(Map.of(Axis.Y, false, Axis.X, true)),
        W(Map.of(Axis.X, true)),
        NW(Map.of(Axis.Y, true, Axis.X, false)),
        C(Map.of());

        private final Map<Axis, Boolean> axes;

        Type(Map<Axis, Boolean> map) {
            this.axes = map;
        }
    }

    private enum Axis {
        X,
        Y
    }
}
