package net.kapitencraft.mysticcraft.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.client.MysticcraftClient;
import net.kapitencraft.mysticcraft.client.render.RenderController;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ChangeGUILocationsScreen extends Screen {
    private final RenderController controller = MysticcraftClient.getInstance().renderController;
    protected ChangeGUILocationsScreen() {
        super(Component.translatable("change_gui_locations.title"));
    }

    @Override
    public void render(PoseStack p_96562_, int p_96563_, int p_96564_, float p_96565_) {
        super.render(p_96562_, p_96563_, p_96564_, p_96565_);
    }
}
