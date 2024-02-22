package net.kapitencraft.mysticcraft.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.MysticcraftClient;
import net.kapitencraft.mysticcraft.client.render.OverlayRenderer;
import net.kapitencraft.mysticcraft.client.render.overlay.box.InteractiveBox;
import net.kapitencraft.mysticcraft.client.render.overlay.box.ScreenDebugBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

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
        controller.fillRenderBoxes(boxes::add, player, font, width / 2f, height / 2f);
        boxes.add(new ScreenDebugBox());
        super.init();
    }

    @Override
    public boolean mouseClicked(double x, double y, int z) {
        List<InteractiveBox> list = boxes.stream().filter(interactiveBox -> interactiveBox.isHovering(x, y)).toList();
        list.forEach(box -> box.click(x, y));
        return list.size() > 0;
    }

    @Override
    public boolean mouseReleased(double x, double y, int z) {
        List<InteractiveBox> list = boxes.stream().filter(interactiveBox -> interactiveBox.isHovering(x, y)).toList();
        list.forEach(box -> box.release(x, y));
        return list.size() > 0;
    }

    @Override
    public void render(@NotNull PoseStack stack, int mouseX, int mouseY, float delta) {
        super.render(stack, mouseX, mouseY, delta);
        boxes.forEach(box -> box.render(mouseX, mouseY));
    }
}
