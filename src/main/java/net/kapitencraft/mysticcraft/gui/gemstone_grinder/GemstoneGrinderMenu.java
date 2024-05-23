package net.kapitencraft.mysticcraft.gui.gemstone_grinder;

import net.kapitencraft.mysticcraft.block.entity.GemstoneGrinderBlockEntity;
import net.kapitencraft.mysticcraft.gui.GUISlotBlockItem;
import net.kapitencraft.mysticcraft.gui.ModMenu;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.init.ModMenuTypes;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.gemstone.*;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.kapitencraft.mysticcraft.networking.packets.S2C.SyncGemstoneDataToBlockPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GemstoneGrinderMenu extends ModMenu<GemstoneGrinderBlockEntity> {
    public static final int MAX_GEMSTONE_SLOTS = 5;

    public GemstoneGrinderMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, (GemstoneGrinderBlockEntity) inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }
    public GemstoneGrinderMenu(int id, Inventory inv, GemstoneGrinderBlockEntity entity) {
        super(ModMenuTypes.GEM_GRINDER.get(), id, 6, inv, entity);
        checkContainerSize(inv, 3);

        this.addPlayerInventories(inv, 0, 0);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new CCSlotItemHandler(handler, 1, 25, 53));
            this.addSlot(new CCSlotItemHandler(handler, 2, 52, 53));
            this.addSlot(new CCSlotItemHandler(handler, 3, 79, 53));
            this.addSlot(new CCSlotItemHandler(handler, 4, 106, 53));
            this.addSlot(new CCSlotItemHandler(handler, 5, 133, 53));
            this.addSlot(new CCSlotItemHandler(handler, 0, 79, 17));
        });
        this.blockEntity.emptyItemHandler(true);
        ItemStack applicable = this.blockEntity.itemHandler.getApplicable();
        if (!applicable.isEmpty() && this.player instanceof ServerPlayer serverPlayer) {
            applicable.getCapability(CapabilityHelper.GEMSTONE).ifPresent(handler -> sendGemstoneCapability(handler, serverPlayer)
            );
        }
    }

    private void sendGemstoneCapability(IGemstoneHandler capability, ServerPlayer player) {
        ModMessages.sendToClientPlayer(new SyncGemstoneDataToBlockPacket(this.blockEntity.getBlockPos(), Map.of(0, (GemstoneCapability) capability)), player);
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
        public void onTake(Player pPlayer, ItemStack pStack) {
            if (this.getSlotIndex() == 0) {
                blockEntity.emptyItemHandler(false);
            }
            else GemstoneHelper.getCapability(getApplicable(), handler -> {
                handler.putGemstone(GemstoneType.EMPTY, GemstoneType.Rarity.EMPTY, blockEntity.getSlotForItem(getSlotIndex()));

                if (player instanceof ServerPlayer serverPlayer) sendGemstoneCapability(handler, serverPlayer);
            });
            super.onTake(pPlayer, pStack);
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            if (this.getSlotIndex() == 0) {
                return GemstoneHelper.hasCapability(stack);
            } else {
                ItemStack applicableStack = getApplicable();
                return GemstoneHelper.exCapability(applicableStack, handler -> {
                    int slotId = GemstoneGrinderBlockEntity.getSlotForItem(this.getSlotIndex(), applicableStack);
                    boolean flag = handler.putGemstoneFromStack(stack, slotId);
                    if (player instanceof ServerPlayer serverPlayer && flag) sendGemstoneCapability(handler, serverPlayer);
                    return flag;
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
            Boolean[] slotsUnlocked = getSlotUnlocked(slotAmount);
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

        private static Boolean[] getSlotUnlocked(int slotAmount) {
            List<Boolean> list = new ArrayList<>();
            for (int i = 0; i < slotAmount; i++) {
                int offset = slotAmount / 2 - i;
                list.add(calcSlot(offset, slotAmount));
            }
            return list.toArray(Boolean[]::new);
        }

        private static boolean calcSlot(int offset, int slotAmount) {
            if (offset == 0) {
                return slotAmount % 2 == 0;
            }
            return slotAmount >= offset * 2;
        }

        private GemstoneGrinderBlockEntity.GemstoneGrinderItemStackHandler getHandler() {
            return (GemstoneGrinderBlockEntity.GemstoneGrinderItemStackHandler) this.getItemHandler();
        }


        private ItemStack getApplicable() {
            return this.getHandler().getStackInSlot(0);
        }
    }

}