package net.kapitencraft.mysticcraft.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.MysticcraftClient;
import net.kapitencraft.mysticcraft.client.render.OverlayController;
import net.kapitencraft.mysticcraft.client.render.overlay.box.InteractiveBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class OverlaysScreen extends Screen {
    private final OverlayController controller = MysticcraftClient.getInstance().overlayController;
    private final List<InteractiveBox> boxes = new ArrayList<>();

    public OverlaysScreen() {
        super(Component.translatable("change_gui_locations.title"));
    }

    @Override
    protected void init() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        boxes.clear();
        controller.fillRenderBoxes(boxes::add, player, font, width, height);
        super.init();
    }

    @Override
    public boolean mouseClicked(double x, double y, int z) {
        List<InteractiveBox> list = getHovering(x, y);
        list.forEach(interactiveBox -> interactiveBox.mouseClicked(x, y, z));
        return list.size() > 0;
    }

    @Override
    public boolean mouseReleased(double x, double y, int z) {
        List<InteractiveBox> list = getHovering(x, y);
        list.forEach(interactiveBox -> interactiveBox.mouseRelease(x, y));
        return list.size() > 0;
    }

    private List<InteractiveBox> getHovering(double x, double y) {
        return boxes.stream().filter(interactiveBox -> interactiveBox.isMouseOver(x, y)).toList();
    }

    @Override
    public void mouseMoved(double x, double y) {
        getHovering(x, y).forEach(interactiveBox -> interactiveBox.mouseMove(x, y));
        super.mouseMoved(x, y);
    }

    @Override
    public void render(@NotNull PoseStack stack, int mouseX, int mouseY, float delta) {
        super.render(stack, mouseX, mouseY, delta);
        boxes.forEach(interactiveBox -> interactiveBox.render(mouseX, mouseY));
        if (minecraft == null) return;
        int arrowId = boxes.stream().map(box -> box.getCursorType(mouseX, mouseY))
                .filter(i -> i != GLFW.GLFW_ARROW_CURSOR) //ensure to only scan for non-default cursors
                .findFirst().orElse(GLFW.GLFW_ARROW_CURSOR);
        long windowId = minecraft.getWindow().getWindow();
        minecraft.execute(()-> GLFW.glfwSetCursor(windowId, GLFW.glfwCreateStandardCursor(arrowId)));
    }

    @Override
    public void onClose() {
        OverlayController.save();
        super.onClose();
    }
}