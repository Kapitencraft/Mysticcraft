package net.kapitencraft.mysticcraft.gui.gemstone_grinder;

import net.kapitencraft.mysticcraft.block.entity.GemstoneGrinderBlockEntity;
import net.kapitencraft.mysticcraft.gui.GUISlotBlockItem;
import net.kapitencraft.mysticcraft.gui.ModMenu;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.init.ModMenuTypes;
import net.kapitencraft.mysticcraft.item.capability.gemstone.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
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
        this.blockEntity.emptyItemHandler(true);
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
            if (this.getSlotIndex() == 0) {
                return true;
            }
            return !(this.getItem().getItem() instanceof GUISlotBlockItem);
        }

        @Override
        public void onTake(@NotNull Player p_150645_, @NotNull ItemStack p_150646_) {
            if (this.getSlotIndex() == 0) {
                blockEntity.emptyItemHandler(false);
            }
            else GemstoneHelper.getCapability(getApplicable(), iGemstoneHandler -> iGemstoneHandler.putGemstone(GemstoneType.EMPTY, GemstoneType.Rarity.EMPTY, blockEntity.getSlotForItem(getSlotIndex())));
            super.onTake(p_150645_, p_150646_);
        }

        //TODO fix gemstones being removed

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            if (this.getSlotIndex() == 0) {
                return GemstoneHelper.hasCapability(stack);
            } else {
                ItemStack applicableStack = getApplicable();
                return GemstoneHelper.exCapability(applicableStack, iGemstoneHandler -> {
                    int slotId = GemstoneGrinderBlockEntity.getSlotForItem(this.getSlotIndex(), applicableStack);
                    return iGemstoneHandler.putGemstoneFromStack(stack, slotId);
                });
            }
        }

        @Override
        public void set(@NotNull ItemStack stack) {
            if (this.getSlotIndex() == 0) GemstoneHelper.getCapability(stack, iGemstoneHandler -> {
                ItemStack[] stacks = getGemstoneForSlot(iGemstoneHandler);
                for (int i = 1; i < 6; i++) {
                    this.getHandler().setStackInSlot(i, stacks[i-1]);
                }
            });
            super.set(stack);
        }

        private static ItemStack[] getGemstoneForSlot(IGemstoneHandler applicable) {
            ItemStack[] stacks = new ItemStack[5];
            GemstoneSlot[] slots = applicable.getSlots();
            int slotAmount = applicable.getSlotAmount();
            boolean[] slotsUnlocked = getSlotUnlocked(slotAmount);
            int j = 0;
            for (int i = 0; i < 5; i++) {
                if (slotsUnlocked[i]) {
                    stacks[i] = GemstoneItem.of(slots[j]);
                    j++;
                } else {
                    stacks[i] = new ItemStack(ModItems.MISSING_GEMSTONE_SLOT.get());
                }
            }
            return stacks;
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

        private GemstoneGrinderBlockEntity.GemstoneGrinderItemStackHandler getHandler() {
            return (GemstoneGrinderBlockEntity.GemstoneGrinderItemStackHandler) this.getItemHandler();
        }


        private ItemStack getApplicable() {
            return this.getHandler().getStackInSlot(0);
        }
    }

}