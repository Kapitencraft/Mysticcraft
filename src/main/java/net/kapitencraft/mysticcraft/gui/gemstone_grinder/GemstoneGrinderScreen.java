package net.kapitencraft.mysticcraft.gui.gemstone_grinder;

import net.kapitencraft.mysticcraft.block.entity.GemstoneGrinderBlockEntity;
import net.kapitencraft.mysticcraft.gui.ModScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GemstoneGrinderScreen extends ModScreen<GemstoneGrinderBlockEntity, GemstoneGrinderMenu> {

    public GemstoneGrinderScreen(GemstoneGrinderMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageHeight = 166;
        this.imageWidth = 176;
    }

    @Override
    protected String getTextureName() {
        return "gemstone_grinder_gui";
    }
}
