package net.kapitencraft.mysticcraft.block.entity;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.render.ItemStackQueue;
import net.kapitencraft.mysticcraft.gui.gemstone_grinder.GemstoneGrinderMenu;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModBlockEntities;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class  GemstoneGrinderBlockEntity extends BlockEntity implements MenuProvider {
    public final GemstoneGrinderItemStackHandler itemHandler = new GemstoneGrinderItemStackHandler(6);
    public ItemStackQueue queue = new ItemStackQueue();

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;

    public GemstoneGrinderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GEMSTONE_GRINDER.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return 0;
            }

            @Override
            public void set(int index, int value) {
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public void emptyItemHandler() {
        GemstoneGrinderItemStackHandler handler = this.itemHandler;
        if (handler.getStackInSlot(0).getItem() instanceof IGemstoneApplicable) return;
        for (int i = 1; i <= 5; i++) {
            handler.setStackInSlot(i, new ItemStack(ModItems.EMPTY_APPLICABLE_SLOT.get()));
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Gem Infusing Station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new GemstoneGrinderMenu(id, inventory, this);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        CompoundTag toReturnTag = new CompoundTag();
        int i = 0;
        this.queue.forEach((stack -> {
            CompoundTag tag = new CompoundTag();
            stack.save(tag);
            toReturnTag.put("id: " + i, toReturnTag);
        }));
        nbt.put("queue", toReturnTag);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        int i = 0;
        while (nbt.contains("id: " + i, 10)) {
            CompoundTag tag = nbt.getCompound("id: " + i);
            this.queue.add(ItemStack.of(tag));
            i++;
        }
    }

    public void drops() {
        SimpleContainer inventory = MiscHelper.containerOf(this.itemHandler);
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static <E extends GemstoneGrinderBlockEntity> void tick(Level ignoredLevel, BlockPos ignoredPos, BlockState ignoredState, E blockEntity) {
        GemstoneGrinderBlockEntity.GemstoneGrinderItemStackHandler handler = blockEntity.itemHandler;
        CompoundTag tag = blockEntity.getPersistentData();
        Level level = blockEntity.level;
        if (level != null && !level.isClientSide()) {
            // security measure to prevent arbitrary chunk generation
            if (!(level.hasChunkAt(blockEntity.getBlockPos()))) return;
            ItemStack gemstoneApplicable = handler.getGemstoneApplicable();
            IGemstoneApplicable applicable = gemstoneApplicable.getItem() instanceof IGemstoneApplicable gemstoneApplicable1 ? gemstoneApplicable1 : null;
            boolean applicableNull = gemstoneApplicable == ItemStack.EMPTY || applicable == null;
            if (applicableNull) {
                if (!tag.contains("hasApplicable", 99) || tag.getBoolean("hasApplicable")) {
                    tag.putBoolean("hasApplicable", false);
                    blockEntity.emptyItemHandler();
                }
            } else {
                if (!tag.getBoolean("hasApplicable")) {
                    tag.putBoolean("hasApplicable", true);
                    ItemStack[] stacks = getGemstoneForSlot(gemstoneApplicable, applicable);
                    for (int i = 1; i < 6; i++) {
                        handler.setStackInSlot(i, stacks[i-1]);
                    }
                }
                for (int i = 1; i < 6; i++) {
                    ItemStack stack = handler.getStackInSlot(i);
                    boolean slotWithGemstone = stack.getItem() instanceof GemstoneItem;
                    if (slotWithGemstone && !tag.getBoolean("hadGemstoneIn" + i)) {
                        tag.putBoolean("hadGemstoneIn" + i, true);
                        GemstoneItem gemstoneItem = (GemstoneItem) stack.getItem();
                        if (!applicable.putGemstone(gemstoneItem.getGemstone(), gemstoneItem.getRarity(), blockEntity.getSlotForItem(i), gemstoneApplicable)) {
                            blockEntity.queue.add(stack);
                            handler.setStackInSlot(i, ItemStack.EMPTY);
                        }
                    } else if (!slotWithGemstone) {
                        if (tag.getBoolean("hadGemstoneIn" + i)) {
                            MysticcraftMod.sendInfo("removing");
                            applicable.putGemstone(GemstoneType.EMPTY, GemstoneType.Rarity.EMPTY, blockEntity.getSlotForItem(i), gemstoneApplicable);
                        }
                        tag.putBoolean("hadGemstoneIn" + i, false);
                    }
                }
            }
        }
    }

    int getSlotForItem(int inputSlot) {
        ItemStack applicable = this.itemHandler.getStackInSlot(0);
        IGemstoneApplicable gemstoneApplicable = applicable.getItem() instanceof IGemstoneApplicable applicable1 ? applicable1 : null;
        if (gemstoneApplicable != null) {
            return switch (gemstoneApplicable.getGemstoneSlotAmount()) {
                case 1 -> 0;
                case 2 -> inputSlot == 2 ? 0 : 1;
                case 3 -> inputSlot - 2;
                case 4 -> inputSlot < 3 ? inputSlot : inputSlot - 1;
                case 5 -> inputSlot - 1;
                default -> throw new RuntimeException("there are only 5 gemstone Slots");
            };
        }
        return -1;
    }


    private static ItemStack[] getGemstoneForSlot(ItemStack stack, IGemstoneApplicable applicable) {
        ItemStack[] stacks = new ItemStack[5];
        GemstoneSlot[] slots = applicable.getGemstoneSlots(stack);
        int slotAmount = applicable.getGemstoneSlotAmount();
        boolean[] slotsUnlocked = getSlotUnlocked(slotAmount);
        int j = 0;
        for (int i = 0; i < 5; i++) {
            if (slotsUnlocked[i]) {
                stacks[i] = new ItemStack(GemstoneItem.of(slots[j]));
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


    public class GemstoneGrinderItemStackHandler extends ItemStackHandler {

        public GemstoneGrinderItemStackHandler(int amount) {
            super(amount);
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        public ItemStack getGemstoneApplicable() {
            return this.getStackInSlot(0);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return (slot == 0 && stack.getItem() instanceof IGemstoneApplicable) || (slot >= 1 && slot <= 5 && stack.getItem() instanceof GemstoneItem);
        }

        @Override
        public int getSlotLimit(int slot) {
                return 1;
        }

    }
}