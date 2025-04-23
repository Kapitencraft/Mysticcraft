package net.kapitencraft.mysticcraft.gui.gemstone_grinder;

import net.kapitencraft.mysticcraft.gui.GUISlotBlockItem;
import net.kapitencraft.mysticcraft.gui.NoBEMenu;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.gemstone.*;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.registry.ModMenuTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class GemstoneGrinderMenu extends NoBEMenu<GemstoneGrinderMenu.ItemContainer> {

    public static final int MAX_GEMSTONE_SLOTS = 5;
    private static boolean[][] unlockedCache;

    public GemstoneGrinderMenu(int id, Inventory inventory) {
        this(id, inventory.player, ContainerLevelAccess.NULL);
    }

    public GemstoneGrinderMenu(int id, Player player, ContainerLevelAccess access) {
        super(ModMenuTypes.GEM_GRINDER.get(), id, new ItemContainer(), player.level(), player, access);
        Inventory inv = player.getInventory();
        checkContainerSize(inv, 3);

        this.addPlayerInventories(inv, 0, 0);


        this.addSlot(new GemstoneItemSlot(0, 79, 17));

        for (int i = 0; i < 5; i++) {
            this.addSlot(new GemstoneItemSlot(i + 1, 25 + 27*i, 53));
        }

        this.container.emptyItemHandler(false);
    }


    @Override
    protected Block getBlock() {
        return ModBlocks.GEMSTONE_GRINDER.getBlock();
    }

    public ItemStack getApplicable() {
        return this.container.getApplicable();
    }

    public int getSlotForItem(int i) {
        return this.container.getSlotForItem(i);
    }


    private class GemstoneItemSlot extends Slot {
        public GemstoneItemSlot(int index, int xPosition, int yPosition) {
            super(GemstoneGrinderMenu.this.container, index, xPosition, yPosition);
        }


        @Override
        public boolean mayPickup(@NotNull Player pPlayer) {
            if (this.getSlotIndex() == 0) {
                return true;
            }
            return !(this.getItem().getItem() instanceof GUISlotBlockItem);
        }

        @Override
        public void onTake(@NotNull Player pPlayer, @NotNull ItemStack pStack) {
            if (this.getSlotIndex() == 0) {
                GemstoneGrinderMenu.this.container.emptyItemHandler(false);
            }
            else GemstoneHelper.getCapability(getApplicable(), handler ->
                    handler.putGemstone(
                            GemstoneType.EMPTY,
                            GemstoneType.Rarity.EMPTY,
                            GemstoneGrinderMenu.this.container.getSlotForItem(getSlotIndex())
                    )
            );
            super.onTake(pPlayer, pStack);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            if (this.getSlotIndex() == 0) {
                return GemstoneHelper.hasCapability(stack);
            } else {
                ItemStack applicableStack = getApplicable();
                return GemstoneHelper.exCapability(applicableStack, handler -> {
                    int slotId = ItemContainer.getSlotForItem(this.getSlotIndex(), applicableStack);
                    return handler.putGemstoneFromStack(stack, slotId);
                });
            }
        }

        @Override
        public void set(@NotNull ItemStack stack) {
            if (this.getSlotIndex() == 0) GemstoneHelper.getCapability(stack, iGemstoneHandler -> {
                ItemStack[] stacks = getGemstoneForSlot(iGemstoneHandler);
                for (int i = 0; i < MAX_GEMSTONE_SLOTS; i++) {
                    this.container.setItem(i + 1, stacks[i]);
                }
            });
            super.set(stack);
        }

        private static ItemStack[] getGemstoneForSlot(IGemstoneHandler applicable) {
            ItemStack[] stacks = new ItemStack[MAX_GEMSTONE_SLOTS];
            GemstoneSlot[] slots = applicable.getSlots();
            int slotAmount = applicable.getSlotAmount();
            boolean[] slotsUnlocked = getOrCacheUnlockedSlots(slotAmount);
            int j = 0;
            for (int i = 0; i < MAX_GEMSTONE_SLOTS; i++) {
                if (slotsUnlocked[i]) {
                    stacks[i] = GemstoneItem.of(slots[j]);
                    j++;
                } else {
                    stacks[i] = new ItemStack(ModItems.MISSING_GEMSTONE_SLOT.get());
                }
            }
            return stacks;
        }

        private ItemStack getApplicable() {
            return GemstoneGrinderMenu.this.container.getApplicable();
        }
    }

    public static class ItemContainer extends SimpleContainer {
        public ItemContainer() {
            super(1 + MAX_GEMSTONE_SLOTS);
        }

        public ItemStack getApplicable() {
            return this.getItem(0);
        }

        public void emptyItemHandler(boolean checkHasApplicable) {
            if (checkHasApplicable && GemstoneHelper.hasCapability(getApplicable())) return;
            for (int i = 1; i <= 5; i++) {
                this.setItem(i, new ItemStack(ModItems.EMPTY_APPLICABLE_SLOT.get()));
            }
        }

        public int getSlotForItem(int slotId) {
            ItemStack applicable = this.getApplicable();
            return getSlotForItem(slotId, applicable);
        }

        public static int getSlotForItem(@Range(from = 0, to = MAX_GEMSTONE_SLOTS - 1) int slotId, ItemStack applicable) {
            return applicable.getCapability(CapabilityHelper.GEMSTONE).map(handler -> {
                boolean[] unlockedSlots = getOrCacheUnlockedSlots(handler.getSlotAmount());
                int j = 0;
                for (int i = 0; i < slotId - 1; i++) {
                    if (unlockedSlots[i]) {
                        j++;
                    }
                }
                return j;
            }).orElse(-1);
        }
    }

    private static void fillUnlockedCache() {
        unlockedCache = new boolean[MAX_GEMSTONE_SLOTS][MAX_GEMSTONE_SLOTS];
        for (int i = 1; i <= MAX_GEMSTONE_SLOTS; i++) {
            unlockedCache[i - 1] = getSlotsUnlocked(i);
        }
    }

    private static boolean[] getSlotsUnlocked(int slotAmount) {
        boolean[] booleans = new boolean[MAX_GEMSTONE_SLOTS];
        int middle = MAX_GEMSTONE_SLOTS / 2;
        for (int i = 0; i < booleans.length; i++) {
            if (i == middle) booleans[i] = slotAmount % 2 == 1;
            else {
                int index = Mth.abs(i - middle);
                booleans[i] = index * 2 <= slotAmount;
            }
        }
        return booleans;
    }

    private static short getSlotsUnlocked(@Range(from = 1, to = 16) byte slotAmount) {
        return 0;
    }

    @Override
    public void removed(@NotNull Player pPlayer) {
        SimpleContainer gemstoneItemToDrop = new SimpleContainer(1);
        gemstoneItemToDrop.setItem(0, this.getApplicable());
        this.access.execute((level, pos) -> clearContainer(pPlayer, gemstoneItemToDrop));
    }

    private static boolean[] getOrCacheUnlockedSlots(int slotAmount) {
        if (unlockedCache == null) {
            fillUnlockedCache();
        }
        return unlockedCache[slotAmount - 1];
    }
}