package net.kapitencraft.mysticcraft.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.MysticcraftClient;
import net.kapitencraft.mysticcraft.client.render.RenderController;
import net.kapitencraft.mysticcraft.client.render.box.RenderBox;
import net.kapitencraft.mysticcraft.client.render.box.ResizeBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChangeGUILocationsScreen extends Screen {
    private final RenderController controller = MysticcraftClient.getInstance().renderController;
    private final List<ResizeBox> boxes = new ArrayList<>();

    public ChangeGUILocationsScreen() {
        super(Component.translatable("change_gui_locations.title"));
    }

    @Override
    protected void init() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        boxes.clear();
        controller.fillRenderBoxes(boxes::add, player, font, width / 2f, height / 2f);
        super.init();
    }

    @Override
    public void render(@NotNull PoseStack stack, int mouseX, int mouseY, float delta) {
        super.render(stack, mouseX, mouseY, delta);
        boxes.forEach(RenderBox::render);
    }
}
