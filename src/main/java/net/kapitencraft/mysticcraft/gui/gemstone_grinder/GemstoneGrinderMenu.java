package net.kapitencraft.mysticcraft.gui.gemstone_grinder;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.GemstoneGrinderBlockEntity;
import net.kapitencraft.mysticcraft.gui.GUISlotBlockItem;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.init.ModMenuTypes;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class GemstoneGrinderMenu extends AbstractContainerMenu {
    public final GemstoneGrinderBlockEntity blockEntity;
    private final Level level;
    private final Player player;

    public GemstoneGrinderMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public GemstoneGrinderMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.GEM_GRINDER_MENU.get(), id);
        checkContainerSize(inv, 3);
        blockEntity = (GemstoneGrinderBlockEntity) entity;
        this.level = inv.player.level;
        this.player = inv.player;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new CCSlotItemHandler(handler, 2, 52, 53));
            this.addSlot(new CCSlotItemHandler(handler, 3, 79, 53));
            this.addSlot(new CCSlotItemHandler(handler, 4, 106, 53));
            this.addSlot(new CCSlotItemHandler(handler, 5, 133, 53));
            this.addSlot(new CCSlotItemHandler(handler, 1, 25, 53));
            this.addSlot(new CCSlotItemHandler(handler, 0, 79, 17));
        });

        addDataSlots(data);
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

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    public static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 6;  // must be the number of slots you have!

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
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
            slotChanged(this.getSlotIndex());
        }
    }

    public void slotChanged(int slotId) {
        GemstoneGrinderBlockEntity.GemstoneGrinderItemStackHandler handler = this.blockEntity.itemHandler;
        this.verifySlots(this.player, handler);
        if (this.level != null && this.level.isClientSide()) {
            int x = this.blockEntity.getBlockPos().getX();
            int y = this.blockEntity.getBlockPos().getY();
            int z = this.blockEntity.getBlockPos().getZ();
            Level world = this.player.level;
            // security measure to prevent arbitrary chunk generation
            if (!(world.hasChunkAt(new BlockPos(x, y, z)))) {
                return;
            }
            ItemStack gemstoneApplicableSlot = handler.getStackInSlot(0);
            Inventory playerInventory = this.player.getInventory();
            boolean flag = gemstoneApplicableSlot != ItemStack.EMPTY;
            IGemstoneApplicable gemstoneApplicable = null;
            if (flag && gemstoneApplicableSlot.getItem() instanceof IGemstoneApplicable iGemstoneApplicable) {
                    gemstoneApplicable = iGemstoneApplicable;
            } //Making the gemstoneApplicable
            if (slotId == 0) {
                updateSlots(gemstoneApplicable, handler);
            } else if (slotId <= 5 && slotId >= 1) {
                ItemStack slot = handler.getStackInSlot(slotId);
                if (slot.getItem() instanceof GemstoneItem gemstoneItem && gemstoneApplicable != null) {
                    if (!gemstoneApplicable.putGemstone(gemstoneItem.getGemstone(), gemstoneItem.getRarity(), slotId - 1, slot)) {
                        playerInventory.add(slot);
                        player.sendSystemMessage(Component.literal("Gemstone Slot " + (slotId + 1) + " can not Support Gemstone " + gemstoneItem.createDisplay()));
                        handler.setStackInSlot(slotId, ItemStack.EMPTY);
                    }
                } else if (slot != ItemStack.EMPTY && !handler.getStackInSlot(0).isEmpty()) {
                    if (!(slot.getItem() instanceof GUISlotBlockItem))
                        playerInventory.add(slot);
                    MysticcraftMod.sendInfo("ea2: " + slot);
                    handler.setStackInSlot(slotId, ItemStack.EMPTY);
                }
            } else {
                throw new IllegalArgumentException("The Slot Index shouldn't be bigger than 6 or smaller than 0 it is currently: " + slotId);
            }
        }
    }

    public void updateSlots(IGemstoneApplicable applicable, ItemStackHandler handler) {
        ItemStack applicableSlot = handler.getStackInSlot(0);
        CompoundTag tag = this.blockEntity.getPersistentData();
        if (applicable != null) {
            applicable.getHelper().loadData(handler.getStackInSlot(0));
            tag.putBoolean("hasApplicable", true);
            int gemstoneSlotAmount = applicable.getGemstoneSlotAmount();
            boolean[] unlockedSlots = getSlotUnlocked(gemstoneSlotAmount);
            GemstoneItem[] gemstoneItems = new GemstoneItem[gemstoneSlotAmount];
            for (int j = 0; j < gemstoneSlotAmount; j++) {
                gemstoneItems[j] = applicable.getGemstoneSlots(applicableSlot)[j].toItem();
            }
            for (int i = 0; i < 5; i++) {
                ItemStack slot = handler.getStackInSlot(i + 1);
                if (unlockedSlots[i]) {
                    handler.setStackInSlot(i + 1, new ItemStack(gemstoneItems[i]));
                } else {
                    if (slot != ItemStack.EMPTY) {
                        player.getInventory().add(slot);
                    }
                    MysticcraftMod.sendInfo("ea4");
                    handler.setStackInSlot(i + 1, new ItemStack(ModItems.MISSING_GEMSTONE_SLOT.get()));
                }
            }
        } else {
            tag.putBoolean("hasApplicable", false);
            GemstoneGrinderBlockEntity.emptyItemHandler(handler);
        }
    }
    private void verifySlots(Player player, ItemStackHandler handler) {
        CompoundTag tag = this.blockEntity.getPersistentData();
        for (int i = 0; i < handler.getSlots(); i++) {
            if (!isValidItemInSlot(handler, i)) {
                if (!(handler.getStackInSlot(i).getItem() instanceof GUISlotBlockItem || handler.getStackInSlot(i).isEmpty())) {
                    if (!tag.getBoolean("hasApplicable") || !(handler.getStackInSlot(i).getItem() instanceof GemstoneItem)) {
                        player.getInventory().add(handler.getStackInSlot(i));
                    }
                }
                if (!handler.getStackInSlot(i).isEmpty()) {
                    MysticcraftMod.sendInfo("ea5");
                    MysticcraftMod.sendInfo(handler.getStackInSlot(i).toString());
                    handler.setStackInSlot(i, ItemStack.EMPTY);
                }
            }
        }
    }

    private boolean isValidItemInSlot(ItemStackHandler handler, int index) {
        ItemStack stack = handler.getStackInSlot(index);
        if (stack == ItemStack.EMPTY) {
            return true;
        } else if (index <= 5 && index >= 1 && (stack.getItem() instanceof GemstoneItem || stack.getItem() instanceof GUISlotBlockItem)) {
            return true;
        } else return index == 0 && stack.getItem() instanceof IGemstoneApplicable;
    }

    private static boolean[] getSlotUnlocked(int slotAmount) {
        return switch (slotAmount) {
            case 1 -> new boolean[]{false, false, true, false, false};
            case 2 -> new boolean[]{false, true, false, true, false};
            case 3 -> new boolean[]{false, true, true, true, false};
            case 4 -> new boolean[]{true, true, false, true, true};
            case 5 -> new boolean[]{true, true, true, true, true};
            default -> new boolean[]{false, false, false, false, false};
        };
    }
}