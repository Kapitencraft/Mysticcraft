package net.kapitencraft.mysticcraft.gui.reforging_anvil;

import net.kapitencraft.mysticcraft.block.entity.ReforgingAnvilBlockEntity;
import net.kapitencraft.mysticcraft.gui.ModMenu;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ReforgingAnvilMenu extends ModMenu<ReforgingAnvilBlockEntity> {

    public ReforgingAnvilMenu(int containerId, Inventory inventory, ReforgingAnvilBlockEntity re) {
        super(ModMenuTypes.REFORGING_ANVIL.get(), containerId, 1, inventory, re);
        this.addPlayerInventories(inventory, 0, 0);
        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler ->
                this.addSlot(new SlotItemHandler(handler, 0, 78, 35))
        );
    }

    public ReforgingAnvilMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, (ReforgingAnvilBlockEntity) inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public String handleButtonPress() {
        return this.blockEntity.updateButtonPress();
    }


    @Override
    public boolean stillValid(@NotNull Player p_38874_) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.REFORGING_ANVIL.getBlock());
    }
}