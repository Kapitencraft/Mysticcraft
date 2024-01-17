package net.kapitencraft.mysticcraft.gui.containable;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.gui.ModMenu;
import net.kapitencraft.mysticcraft.init.ModMenuTypes;
import net.kapitencraft.mysticcraft.item.material.containable.ContainableItemHandler;
import net.kapitencraft.mysticcraft.item.material.containable.GUIContainableItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class ContainableMenu<T extends Item> extends ModMenu<ItemStack> {
    private final ContainableItemHandler<T> handler;
    private final Inventory inventory;

    public ContainableMenu(int id, Inventory inventory, FriendlyByteBuf byteBuf) {
        this(id, inventory, (GUIContainableItem<T>) byteBuf.readRegistryIdUnsafe(ForgeRegistries.ITEMS), inventory.getItem(byteBuf.readInt()));
    }
    public ContainableMenu(int id, Inventory inventory, GUIContainableItem<T> item, ItemStack stack) {
        super(ModMenuTypes.CONTAINABLE.get(), id, item.getStackSize(), inventory, stack);
        this.inventory = inventory;
        this.handler = new ContainableItemHandler<>(item);

        addPlayerInventories(inventory, 0, 0);

        int stackAmount = handler.getItem().getStackAmount();
        int rowAmount = stackAmount % 9;
        int lastRowAmount = stackAmount - rowAmount * 9;
        for (int i = 0; i < handler.getItem().getStackAmount(); i++) {
            this.addSlot(new SlotItemHandler(handler, i, i * 18 + 0, 0));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = slots.get(index);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + this.handler.getSlots(), false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + this.handler.getSlots()) {
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
        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return false;
    }
}
