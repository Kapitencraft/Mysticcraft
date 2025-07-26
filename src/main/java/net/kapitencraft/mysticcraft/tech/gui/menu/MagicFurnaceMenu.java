package net.kapitencraft.mysticcraft.tech.gui.menu;

import net.kapitencraft.mysticcraft.registry.ModMenuTypes;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.kapitencraft.mysticcraft.tech.block.entity.MagicFurnaceBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class MagicFurnaceMenu extends UpgradableBEMenu<MagicFurnaceBlockEntity> {

    public MagicFurnaceMenu(int containerId, Inventory inventory, MagicFurnaceBlockEntity provider) {
        super(ModMenuTypes.MAGIC_FURNACE.get(), containerId, 2, inventory, provider);

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

    public int getManaTankScaledFillPercentage() {
        return 69 * this.blockEntity.getManaStored() / this.blockEntity.getMaxManaStored();
    }

    public int getBurnProgress() {
        return this.blockEntity.getBurnProgress();
    }

    public Component getManaTooltip() {
        return this.blockEntity.getStorageTooltip();
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        if (index < 36) {
            Slot slot = this.slots.get(index);
            ItemStack stack = slot.getItem();
            if (!stack.is(ModTags.Items.UPGRADE)) {
                this.slots.get(44).safeInsert(stack);
                return ItemStack.EMPTY;
            }
        }

        return super.quickMoveStack(playerIn, index);
    }
}
