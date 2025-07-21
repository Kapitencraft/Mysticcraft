package net.kapitencraft.mysticcraft.tech.gui.screen;

import net.kapitencraft.mysticcraft.tech.gui.menu.GenericFueledGeneratorMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class VulcanicGeneratorScreen extends GenericFueledGeneratorScreen {
    public VulcanicGeneratorScreen(GenericFueledGeneratorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }
}
