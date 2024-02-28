package net.kapitencraft.mysticcraft.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.MysticcraftClient;
import net.kapitencraft.mysticcraft.client.render.OverlayRenderer;
import net.kapitencraft.mysticcraft.client.render.overlay.box.InteractiveBox;
import net.kapitencraft.mysticcraft.client.render.overlay.box.RenderBox;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ChangeGUILocationsScreen extends Screen {
    private final OverlayRenderer controller = MysticcraftClient.getInstance().renderController;
    private final List<InteractiveBox> boxes = new ArrayList<>();

    public ChangeGUILocationsScreen() {
        super(Component.translatable("change_gui_locations.title"));
    }

    @Override
    protected void init() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        boxes.clear();
        controller.fillRenderBoxes(boxes::add, player, font, width, height);
        //boxes.add(new ScreenDebugBox());
        super.init();
    }

    @Override
    public boolean mouseClicked(double x, double y, int z) {
        List<InteractiveBox> list = getHovering(x, y);
        list.forEach(CollectionHelper.triUsage(x, y, InteractiveBox::mouseClick));
        return list.size() > 0;
    }

    @Override
    public boolean mouseReleased(double x, double y, int z) {
        List<InteractiveBox> list = getHovering(x, y);
        list.forEach(CollectionHelper.triUsage(x, y, InteractiveBox::mouseRelease));
        return list.size() > 0;
    }

    private List<InteractiveBox> getHovering(double x, double y) {
        return boxes.stream().filter(CollectionHelper.triFilter(x, y, InteractiveBox::isHovering)).toList();
    }

    @Override
    public boolean mouseDragged(double newX, double newY, int clickType, double changeX, double changeY) {
        double oldX = -changeX + newX;
        double oldY = -changeY + newY;
        getHovering(oldX, oldY).forEach(box -> box.mouseDrag(newX, newY, clickType, changeX, changeY, oldX, oldY));
        return super.mouseDragged(newX, newY, clickType, changeX, changeY);
    }

    @Override
    public void mouseMoved(double x, double y) {
        getHovering(x, y).forEach(CollectionHelper.triUsage(x, y, InteractiveBox::mouseMove));
        super.mouseMoved(x, y);
    }

    @Override
    public void render(@NotNull PoseStack stack, int mouseX, int mouseY, float delta) {
        super.render(stack, mouseX, mouseY, delta);
        boxes.forEach(CollectionHelper.triUsage((double) mouseX, (double) mouseY, RenderBox::render));
        if (minecraft == null) return;
        int arrowId = boxes.stream().map(box -> box.getCursorType(mouseX, mouseY))
                .filter(i -> i != GLFW.GLFW_ARROW_CURSOR) //ensure to only scan for non-default cursors
                .findFirst().orElse(GLFW.GLFW_ARROW_CURSOR);
        long windowId = minecraft.getWindow().getWindow();
        minecraft.execute(()-> GLFW.glfwSetCursor(windowId, GLFW.glfwCreateStandardCursor(arrowId)));
    }
}
