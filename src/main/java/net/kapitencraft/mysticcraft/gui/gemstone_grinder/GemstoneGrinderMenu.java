package net.kapitencraft.mysticcraft.gui.gemstone_grinder;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.GemstoneGrinderBlockEntity;
import net.kapitencraft.mysticcraft.gui.GUISlotBlockItem;
import net.kapitencraft.mysticcraft.gui.ModMenu;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class GemstoneGrinderMenu extends ModMenu<GemstoneGrinderBlockEntity> {

    public GemstoneGrinderMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, (GemstoneGrinderBlockEntity) inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public GemstoneGrinderMenu(int id, Inventory inv, GemstoneGrinderBlockEntity entity) {
        super(ModMenuTypes.GEM_GRINDER.get(), id, 6, inv, entity);
        checkContainerSize(inv, 3);

        this.addPlayerInventories(inv, 0, 0);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new CCSlotItemHandler(handler, 2, 52, 53));
            this.addSlot(new CCSlotItemHandler(handler, 3, 79, 53));
            this.addSlot(new CCSlotItemHandler(handler, 4, 106, 53));
            this.addSlot(new CCSlotItemHandler(handler, 5, 133, 53));
            this.addSlot(new CCSlotItemHandler(handler, 1, 25, 53));
            this.addSlot(new CCSlotItemHandler(handler, 0, 79, 17));
        });
    }


    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.GEMSTONE_GRINDER.getBlock());
    }


    private class CCSlotItemHandler extends SlotItemHandler {
        public CCSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }


        @Override
        public boolean mayPickup(Player playerIn) {
            return !(this.getItem().getItem() instanceof GUISlotBlockItem);
        }

        @Override
        public void setChanged() {
            super.setChanged();
            slotChanged();
        }
    }

    public void slotChanged() {
        if (!this.blockEntity.queue.isEmpty()) {
            MysticcraftMod.sendInfo(this.blockEntity.queue.toString());
            this.blockEntity.queue.forEach((stack -> {
                if (this.blockEntity.queue.remove(stack)) this.player.getInventory().add(stack);
            }));
        }
    }
}