package net.kapitencraft.mysticcraft.tech.gui.screen;

import net.kapitencraft.kap_lib.client.gui.screen.BlockEntityScreen;
import net.kapitencraft.mysticcraft.tech.block.UpgradableBlockEntity;
import net.kapitencraft.mysticcraft.tech.gui.menu.UpgradableBEMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public abstract class UpgradableBEScreen<BE extends UpgradableBlockEntity, M extends UpgradableBEMenu<BE>> extends BlockEntityScreen<BE, M> {
    public UpgradableBEScreen(M menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }
}
