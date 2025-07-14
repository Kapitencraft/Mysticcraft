package net.kapitencraft.mysticcraft.tech.gui.menu;

import net.kapitencraft.kap_lib.client.gui.BlockEntityMenu;
import net.kapitencraft.mysticcraft.registry.ModMenuTypes;
import net.kapitencraft.mysticcraft.tech.block.entity.PrismaticGeneratorBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.SlotItemHandler;

public class PrismaticGeneratorMenu extends BlockEntityMenu<PrismaticGeneratorBlockEntity> {

    public PrismaticGeneratorMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, (PrismaticGeneratorBlockEntity) inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public PrismaticGeneratorMenu(int containerId, Inventory inventory, PrismaticGeneratorBlockEntity provider) {
        super(ModMenuTypes.PRISMATIC_GENERATOR.get(), containerId, 1, inventory, provider);

        addPlayerInventories(inventory, 0, 0);
        this.addSlot(new SlotItemHandler(provider.getItems(), 0, 80, 40));
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public boolean isLit() {
        return this.blockEntity.isLit();
    }

    public int getLitProgress() {
        return this.blockEntity.getLitProgress();
    }

    public int getManaTankScaledFillPercentage() {
        return 69 * this.blockEntity.getManaStored() / this.blockEntity.getMaxManaStored();
    }

    public Component getManaTooltip() {
        return this.blockEntity.getStorageTooltip();
    }
}
