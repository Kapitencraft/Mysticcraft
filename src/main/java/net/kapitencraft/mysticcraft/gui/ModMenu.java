package net.kapitencraft.mysticcraft.gui;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ModMenu<T extends ICapabilityProvider> extends AbstractContainerMenu {
    private final int slotAmount;
    protected final Level level;
    public final Player player;
    protected final T blockEntity;

    protected ModMenu(@Nullable MenuType<?> menuType, int containerId, int slotAMount, Inventory inventory, T provider) {
        super(menuType, containerId);
        this.slotAmount = slotAMount;
        this.player = inventory.player;
        this.level = player.level;
        this.blockEntity = provider;
    }

    public void addPlayerInventories(Inventory inventory, int xOffset, int yOffset) {
        addPlayerInventory(inventory, xOffset, yOffset);
        addPlayerHotbar(inventory, xOffset, yOffset);
    }

    private void addPlayerInventory(Inventory playerInventory, int xOffset, int yOffSet) {
        MiscHelper.repeat(3, i -> MiscHelper.repeat(9, l -> this.addSlot(new Slot(playerInventory, l + i * 9 + 9, xOffset + 8 + l * 18, yOffSet + 84 + i * 18))));
    }
    private void addPlayerHotbar(Inventory playerInventory, int xOffset, int yOffSet) {
        MiscHelper.repeat(9, i -> this.addSlot(new Slot(playerInventory, i, xOffset + 8 + i * 18, yOffSet + 142)));
    }


    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    public static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    public static final int VANILLA_FIRST_SLOT_INDEX = 0;
    public static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + slotAmount, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + slotAmount) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            MysticcraftMod.LOGGER.warn("Invalid slotIndex: {}", index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    public T getCapabilityProvider() {
        return blockEntity;
    }
}
