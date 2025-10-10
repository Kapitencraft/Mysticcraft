package net.kapitencraft.mysticcraft.tech.gui.menu;

import net.kapitencraft.kap_lib.client.gui.BlockEntityMenu;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.kapitencraft.mysticcraft.tech.block.UpgradableBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UpgradableBEMenu<BE extends UpgradableBlockEntity> extends BlockEntityMenu<BE> {
    private boolean showUpgrades;

    protected UpgradableBEMenu(@Nullable MenuType<?> menuType, int containerId, int slotAmount, Inventory inventory, BE provider) {
        super(menuType, containerId, slotAmount + 8, inventory, provider);

        addPlayerInventories(inventory, 0, 0);

        for (int i = 0; i < 8; i++) {
            int slot = i;
            this.addSlot(new SlotItemHandler(provider.getUpgrades(), i, 184 + i % 2 * 18, 14 + i / 2 * 18) {
                @Override
                public boolean isActive() {
                    return provider.upgradeSlots() > slot && UpgradableBEMenu.this.showUpgrades;
                }

                @Override
                public boolean mayPlace(ItemStack pStack) {
                    return provider.canUpgrade(pStack);
                }

                @Override
                public void setByPlayer(ItemStack pStack) {
                    super.setByPlayer(pStack);
                    provider.upgrade(pStack);
                }

                @Override
                public int getMaxStackSize() {
                    return 1;
                }
            });
        }
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return true;
    }

    public boolean doesShowUpgrades() {
        return showUpgrades;
    }

    public void setShowUpgrades(boolean showUpgrades) {
        this.showUpgrades = showUpgrades;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        Slot slot = this.slots.get(index);
        ItemStack stack = slot.getItem();
        if (stack.is(ModTags.Items.UPGRADE)) {
            if ((index < 36 || index > 36 + this.blockEntity.upgradeSlots()) && !moveItemStackTo(stack, 36, 36 + this.blockEntity.upgradeSlots(), false)) return ItemStack.EMPTY;
        }
        return super.quickMoveStack(playerIn, index);
    }
}
