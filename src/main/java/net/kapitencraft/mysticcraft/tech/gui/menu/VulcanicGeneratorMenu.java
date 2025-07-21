package net.kapitencraft.mysticcraft.tech.gui.menu;

import net.kapitencraft.mysticcraft.registry.ModMenuTypes;
import net.kapitencraft.mysticcraft.tech.block.entity.GenericFueledGeneratorBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class VulcanicGeneratorMenu extends GenericFueledGeneratorMenu {

    public VulcanicGeneratorMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenuTypes.VULCANIC_GENERATOR.get(), containerId, inventory, buf);
    }

    public VulcanicGeneratorMenu(int containerId, Inventory inventory, GenericFueledGeneratorBlockEntity provider) {
        super(ModMenuTypes.VULCANIC_GENERATOR.get(), containerId, inventory, provider);
    }
}
