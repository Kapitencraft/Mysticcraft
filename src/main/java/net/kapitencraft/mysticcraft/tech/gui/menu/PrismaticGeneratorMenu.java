package net.kapitencraft.mysticcraft.tech.gui.menu;

import net.kapitencraft.mysticcraft.registry.ModMenuTypes;
import net.kapitencraft.mysticcraft.tech.block.entity.PrismaticGeneratorBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class PrismaticGeneratorMenu extends GenericFueledGeneratorMenu {

    public PrismaticGeneratorMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        super(ModMenuTypes.PRISMATIC_GENERATOR.get(), containerId, inventory, buf);
    }

    public PrismaticGeneratorMenu(int containerId, Inventory inventory, PrismaticGeneratorBlockEntity provider) {
        super(ModMenuTypes.PRISMATIC_GENERATOR.get(), containerId, inventory, provider);

        addPlayerInventories(inventory, 0, 0);
        this.addSlot(new SlotItemHandler(provider.getItems(), 0, 80, 40));
    }
}
