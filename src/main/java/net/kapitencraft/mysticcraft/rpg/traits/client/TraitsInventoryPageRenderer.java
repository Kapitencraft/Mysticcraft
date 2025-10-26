package net.kapitencraft.mysticcraft.rpg.traits.client;

import net.kapitencraft.kap_lib.inventory.page_renderer.InventoryPageRenderer;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.rpg.traits.TraitsInventoryPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TraitsInventoryPageRenderer implements InventoryPageRenderer {
    private static final ResourceLocation BACKGROUND = MysticcraftMod.res("textures/gui/blank_inventory_page_background.png");
    public TraitsInventoryPageRenderer(TraitsInventoryPage page) {}

    @Override
    public void render(GuiGraphics graphics, Minecraft minecraft, int mouseX, int mouseY, float mouseXOld, float mouseYOld, int leftPos, int topPos) {

    }

    @Override
    public void init(int leftPos, int topPos) {

    }

    @Override
    public @NotNull ResourceLocation pageBackgroundLocation() {
        return BACKGROUND;
    }
}
