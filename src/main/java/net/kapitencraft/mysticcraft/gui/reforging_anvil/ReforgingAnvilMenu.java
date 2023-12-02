package net.kapitencraft.mysticcraft.gui.reforging_anvil;

import net.kapitencraft.mysticcraft.block.entity.ReforgingAnvilBlockEntity;
import net.kapitencraft.mysticcraft.gui.ModMenu;
import net.kapitencraft.mysticcraft.helpers.InventoryHelper;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModMenuTypes;
import net.kapitencraft.mysticcraft.item.data.dungeon.IPrestigeAbleItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ReforgingAnvilMenu extends ModMenu<ReforgingAnvilBlockEntity> {

    public ReforgingAnvilMenu(int containerId, Inventory inventory, ReforgingAnvilBlockEntity re) {
        super(ModMenuTypes.REFORGING_ANVIL.get(), containerId, 1, inventory, re);
        this.addPlayerInventories(inventory, 0, 0);
        LazyOptional<IItemHandler> itemHandler =
        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER);
        itemHandler.ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 43, 35));
            this.addSlot(new SlotItemHandler(handler, 1, 115, 35));
        });
    }

    public ReforgingAnvilMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, (ReforgingAnvilBlockEntity) inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public String handleButtonPress() {
        return this.blockEntity.updateButtonPress();
    }

    public void upgrade() {
        ItemStack upgrade = this.blockEntity.getStack(true);
        Item item = upgrade.getItem();
        if (item instanceof IPrestigeAbleItem prestigeAbleItem) {
            if (prestigeAbleItem.mayPrestige(upgrade, false) && InventoryHelper.hasInInventory(prestigeAbleItem.getMatCost(upgrade), this.player)) {
            }
        }
    }



    @Override
    public boolean stillValid(@NotNull Player p_38874_) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.REFORGING_ANVIL.getBlock());
    }
}
