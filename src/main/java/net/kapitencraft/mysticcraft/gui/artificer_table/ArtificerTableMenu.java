package net.kapitencraft.mysticcraft.gui.artificer_table;

import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneHandler;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.gui.NoBEMenu;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
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

import java.util.Arrays;

public class ArtificerTableMenu extends NoBEMenu<ArtificerTableMenu.ItemContainer> {

    public static int MAX_GEMSTONE_SLOTS = 5;
    private static boolean[][] unlockedCache;

    private boolean[] unlocked;

    public ArtificerTableMenu(int id, Inventory inventory) {
        this(id, inventory.player, ContainerLevelAccess.NULL);
    }

    public ArtificerTableMenu(int id, Player player, ContainerLevelAccess access) {
        super(ModMenuTypes.GEM_GRINDER.get(), id, new ItemContainer(), player.level(), player, access);
        Inventory inv = player.getInventory();

        this.addPlayerInventories(inv, 0, 0);

        this.addSlot(new GemstoneItemSlot(0, 79, 17));

        int xStart = 25 - (MAX_GEMSTONE_SLOTS - 5) * 13;
        for (int i = 0; i < MAX_GEMSTONE_SLOTS; i++) {
            this.addSlot(new GemstoneItemSlot(i + 1, xStart + 27*i, 53));
        }

        this.unlocked = new boolean[MAX_GEMSTONE_SLOTS];
        Arrays.fill(unlocked, false);

        this.container.emptyItemHandler(false);
    }

    @Override
    protected Block getBlock() {
        return ModBlocks.ARTIFICER_TABLE.get();
    }

    public ItemStack getApplicable() {
        return this.container.getApplicable();
    }

    public int getSlotForItem(int i) {
        return this.container.getSlotForItem(i);
    }

    public boolean[] getUnlocked() {
        return unlocked;
    }

    private class GemstoneItemSlot extends Slot {
        public GemstoneItemSlot(int index, int xPosition, int yPosition) {
            super(ArtificerTableMenu.this.container, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPickup(@NotNull Player pPlayer) {
            return true;
        }

        @Override
        public void onTake(@NotNull Player pPlayer, @NotNull ItemStack pStack) {
            if (this.getSlotIndex() == 0) {
                ArtificerTableMenu.this.container.emptyItemHandler(false);
                ArtificerTableMenu.this.unlocked = new boolean[MAX_GEMSTONE_SLOTS];
                ArtificerTableMenu.this.container.handlerCache = null;
                Arrays.fill(ArtificerTableMenu.this.unlocked, false);
            }
            else {
                ArtificerTableMenu.this.container.handlerCache.putGemstone(
                        GemstoneType.EMPTY,
                        GemstoneType.Rarity.EMPTY,
                        ArtificerTableMenu.this.container.getSlotForItem(getSlotIndex())
                );
                getApplicable().set(ModDataComponentTypes.EMBEDDED_GEMSTONES, ArtificerTableMenu.this.container.handlerCache);
            }
            super.onTake(pPlayer, pStack);
        }

        @Override
        public boolean isActive() {
            return this.getSlotIndex() == 0 || ArtificerTableMenu.this.unlocked[this.getSlotIndex() - 1];
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            if (this.getSlotIndex() == 0) {
                return stack.has(ModDataComponentTypes.EMBEDDED_GEMSTONES);
            } else {
                ItemStack applicableStack = getApplicable();
                if (!ArtificerTableMenu.this.unlocked[this.getSlotIndex() - 1]) {
                    return false;
                }
                int slotId = ItemContainer.getSlotForItem(this.getSlotIndex(), applicableStack);
                boolean check = ArtificerTableMenu.this.container.handlerCache.putGemstoneFromStack(stack, slotId);
                if (check) applicableStack.set(ModDataComponentTypes.EMBEDDED_GEMSTONES, ArtificerTableMenu.this.container.handlerCache);
                return check;
            }
        }

        @Override
        public void set(@NotNull ItemStack stack) {
            if (this.getSlotIndex() == 0) {
                GemstoneHandler handler = stack.get(ModDataComponentTypes.EMBEDDED_GEMSTONES);
                ArtificerTableMenu.this.container.handlerCache = handler;
                if (handler != null) {
                    ItemStack[] stacks = getGemstoneForSlot(handler);
                    ArtificerTableMenu.this.unlocked = getOrCacheUnlockedSlots(handler.getSlotAmount());
                    for (int i = 0; i < MAX_GEMSTONE_SLOTS; i++) {
                        this.container.setItem(i + 1, stacks[i]);
                    }
                }
            }
            super.set(stack);
        }

        private static ItemStack[] getGemstoneForSlot(GemstoneHandler applicable) {
            ItemStack[] stacks = new ItemStack[MAX_GEMSTONE_SLOTS];
            GemstoneSlot[] slots = applicable.slots();
            int slotAmount = applicable.getSlotAmount();
            boolean[] slotsUnlocked = getOrCacheUnlockedSlots(slotAmount);
            int j = 0;
            for (int i = 0; i < MAX_GEMSTONE_SLOTS; i++) {
                if (slotsUnlocked[i]) {
                    stacks[i] = GemstoneItem.of(slots[j]);
                    j++;
                } else {
                    stacks[i] = ItemStack.EMPTY;
                }
            }
            return stacks;
        }

        private ItemStack getApplicable() {
            return ArtificerTableMenu.this.container.getApplicable();
        }
    }

    public static class ItemContainer extends SimpleContainer {
        private GemstoneHandler handlerCache;

        public ItemContainer() {
            super(1 + MAX_GEMSTONE_SLOTS);
        }

        public ItemStack getApplicable() {
            return this.getItem(0);
        }

        public void emptyItemHandler(boolean checkHasApplicable) {
            if (checkHasApplicable && handlerCache != null) return;
            for (int i = 1; i <= 5; i++) {
                this.setItem(i, ItemStack.EMPTY);
            }
        }

        public int getSlotForItem(int slotId) {
            ItemStack applicable = this.getApplicable();
            return getSlotForItem(slotId, applicable);
        }

        public static int getSlotForItem(@Range(from = 0, to = 9) int slotId, ItemStack applicable) {
            GemstoneHandler handler = applicable.get(ModDataComponentTypes.EMBEDDED_GEMSTONES);
            if (handler == null) return -1;
            boolean[] unlockedSlots = getOrCacheUnlockedSlots(handler.getSlotAmount());
            int j = 0;
            for (int i = 0; i < slotId - 1; i++) {
                if (unlockedSlots[i]) {
                    j++;
                }
            }
            return j;
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