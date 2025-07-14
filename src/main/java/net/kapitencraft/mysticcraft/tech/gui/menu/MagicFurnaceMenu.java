package net.kapitencraft.mysticcraft.tech.gui.menu;

import net.kapitencraft.kap_lib.client.gui.BlockEntityMenu;
import net.kapitencraft.mysticcraft.registry.ModMenuTypes;
import net.kapitencraft.mysticcraft.tech.block.entity.MagicFurnaceBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class MagicFurnaceMenu extends BlockEntityMenu<MagicFurnaceBlockEntity> {
    public MagicFurnaceMenu(int containerId, Inventory inventory, MagicFurnaceBlockEntity provider) {
        super(ModMenuTypes.MAGIC_FURNACE.get(), containerId, 2, inventory, provider);

        this.addPlayerInventories(inventory, 0, 0);
        this.addSlot(new SlotItemHandler(this.blockEntity.getItems(), 0, 56, 35));
        this.addSlot(new SlotItemHandler(this.blockEntity.getItems(), 1, 116, 35) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }
        });
    }

    public MagicFurnaceMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, (MagicFurnaceBlockEntity) inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public int getManaTankScaledFillPercentage() {
        return 69 * this.blockEntity.getManaStored() / this.blockEntity.getMaxManaStored();
    }

    public int getBurnProgress() {
        return this.blockEntity.getBurnProgress();
    }

    public Component getManaTooltip() {
        return this.blockEntity.getStorageTooltip();
    }
}
