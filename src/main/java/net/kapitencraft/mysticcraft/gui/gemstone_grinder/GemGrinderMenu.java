package net.kapitencraft.mysticcraft.gui.gemstone_grinder;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.GemstoneGrinderBlockEntity;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


public class GemGrinderMenu extends AbstractContainerMenu {
    public final static HashMap<String, Object> GUI_STATE = new HashMap<>();

    public final GemstoneGrinderBlockEntity blockEntity;
    private final Level level;
    public final Player player;
    private int x, y, z;
    private final ContainerData data;
    private SimpleContainer container;
    public final Map<Integer, Slot> customSlots = new HashMap<>();

    public GemGrinderMenu(int id, Inventory inventory, FriendlyByteBuf extraData) {
        this(id, inventory, inventory.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(0), inventory.player.level, inventory.player);
        if (extraData != null) {
            BlockPos pos = extraData.readBlockPos();
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
        }
    }

    public GemGrinderMenu(int id, Inventory inventory, BlockEntity entity, ContainerData data, Level level, Player player) {
        super(ModMenuTypes.GEM_GRINDER_MENU.get(), id);
        checkContainerSize(inventory, GemstoneGrinderBlockEntity.INVENTORY_SIZE);
        blockEntity = (GemstoneGrinderBlockEntity) entity;
        this.container = new SimpleContainer(blockEntity.getItemHandler().getSlots());
        this.level = level;
        this.data = data;
        this.player = player;
        addPlayerHotbar(inventory);
        addPlayerInventory(inventory);
        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.customSlots.put(1, this.addSlot(new SlotItemHandler(handler, 0, 52, 53) {
                @Override
                public void setChanged() {
                    super.setChanged();
                    slotChanged(0, 0, 0);
                }
            }));
            this.customSlots.put(2, this.addSlot(new SlotItemHandler(handler, 1, 79, 53) {
                @Override
                public void setChanged() {
                    super.setChanged();
                    slotChanged(0, 0, 0);
                }
            }));
            this.customSlots.put(3, this.addSlot(new SlotItemHandler(handler, 2, 106, 53) {
                @Override
                public void setChanged() {
                    super.setChanged();
                    slotChanged(0, 0, 0);
                }
            }));
            this.customSlots.put(4, this.addSlot(new SlotItemHandler(handler, 3, 133, 53) {
                @Override
                public void setChanged() {
                    super.setChanged();
                    slotChanged(0, 0, 0);
                }
            }));
            this.customSlots.put(5, this.addSlot(new SlotItemHandler(handler, 4, 25, 53) {
                @Override
                public void setChanged() {
                    super.setChanged();
                    slotChanged(0, 0, 0);
                }
            }));
            this.customSlots.put(0, this.addSlot(new SlotItemHandler(handler, 5, 79, 17) {
                @Override
                public void setChanged() {
                    super.setChanged();
                    slotChanged(0, 0, 0);
                }
            }));
        });
        addDataSlots(data);
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(player.level, blockEntity.getBlockPos()), player, ModBlocks.GEMSTONE_GRINDER.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 26; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    public static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + GemstoneGrinderBlockEntity.INVENTORY_SIZE, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + GemstoneGrinderBlockEntity.INVENTORY_SIZE) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            MysticcraftMod.LOGGER.warn("Invalid slotIndex:" + index);
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

    private void slotChanged(int slot_id, int c_type, int meta) {
        if (this.level != null && this.level.isClientSide()) {
            MysticcraftMod.PACKET_HANDLER.sendToServer(new GemGrinderSlotMessage(slot_id, x, y, z, c_type, meta));
            GemGrinderSlotMessage.handleSlotAction(this.player, slot_id, c_type, meta, x, y, z, this.container);
        }
    }


}
