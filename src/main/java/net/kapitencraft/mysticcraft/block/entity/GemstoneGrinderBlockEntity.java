package net.kapitencraft.mysticcraft.block.entity;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.render.ItemStackQueue;
import net.kapitencraft.mysticcraft.gui.GUISlotBlockItem;
import net.kapitencraft.mysticcraft.gui.gemstone_grinder.GemstoneGrinderMenu;
import net.kapitencraft.mysticcraft.init.ModBlockEntities;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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
        MysticcraftMod.sendInfo("init");
        this.emptyItemHandler(this.itemHandler);
    }

    public void emptyItemHandler(ItemStackHandler handler) {
        if (handler.getStackInSlot(0).getItem() instanceof IGemstoneApplicable || this.level instanceof ServerLevel) return;
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
        return new GemstoneGrinderMenu(id, inventory, this, this.data);
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
        SimpleContainer inventory = MiscUtils.containerOf(this.itemHandler);
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static <E extends GemstoneGrinderBlockEntity> void tick(Level ignoredLevel, BlockPos ignoredPos, BlockState ignoredState, E blockEntity) {
        GemstoneGrinderBlockEntity.GemstoneGrinderItemStackHandler handler = blockEntity.itemHandler;
        Level level = blockEntity.level;
        if (level != null && level.isClientSide()) {
            // security measure to prevent arbitrary chunk generation
            if (!(level.hasChunkAt(blockEntity.getBlockPos()))) return;
            ItemStack gemstoneApplicableSlot = handler.getStackInSlot(0);
            boolean flag = gemstoneApplicableSlot != ItemStack.EMPTY;
            IGemstoneApplicable gemstoneApplicable = flag && gemstoneApplicableSlot.getItem() instanceof IGemstoneApplicable iGemstoneApplicable ? iGemstoneApplicable : null; //Making the gemstoneApplicable
            blockEntity.updateSlots(gemstoneApplicable, handler);
            for (int slotId = 1; slotId < 6; slotId++) {
                ItemStack slot = handler.getStackInSlot(slotId);
                if (slot.getItem() instanceof GemstoneItem gemstoneItem && gemstoneApplicable != null) {
                    if (!gemstoneApplicable.putGemstone(gemstoneItem.getGemstone(), gemstoneItem.getRarity(), blockEntity.getSlotForItem(slotId), slot)) {
                        blockEntity.queue.add(slot);
                        handler.setStackInSlot(slotId, ItemStack.EMPTY);
                    }
                } else if (slot != ItemStack.EMPTY && !handler.getStackInSlot(0).isEmpty()) {
                    if (!(slot.getItem() instanceof GUISlotBlockItem || slot.getItem() instanceof GemstoneItem)) {
                        blockEntity.queue.add(slot);
                        handler.setStackInSlot(slotId, ItemStack.EMPTY);
                    }
                }
            }
        }
    }

    void updateSlots(IGemstoneApplicable applicable, ItemStackHandler handler) {
        ItemStack applicableSlot = handler.getStackInSlot(0);
        CompoundTag tag = this.getPersistentData();
        if (applicable != null) {
            if (!tag.getBoolean("hasApplicable")) {
                applicable.loadData(applicableSlot);
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
                        handler.setStackInSlot(i + 1, new ItemStack(gemstoneItems[getSlotForItem(i)]));
                    } else {
                        if (slot != ItemStack.EMPTY && !(slot.getItem() instanceof GUISlotBlockItem)) {
                            if (!this.queue.contains(slot)) this.queue.add(slot);
                        }
                        handler.setStackInSlot(i + 1, new ItemStack(ModItems.MISSING_GEMSTONE_SLOT.get()));
                    }
                }
            }
        } else if (tag.getBoolean("hasApplicable")) {
            tag.putBoolean("hasApplicable", false);
            this.emptyItemHandler(handler);
        }
    }

    int getSlotForItem(int inputSlot) {
        ItemStack applicable = this.itemHandler.getStackInSlot(0);
        IGemstoneApplicable gemstoneApplicable = applicable.getItem() instanceof IGemstoneApplicable applicable1 ? applicable1 : null;
        if (gemstoneApplicable != null) {
            return switch (gemstoneApplicable.getGemstoneSlotAmount()) {
                case 1 -> 0;
                case 2 -> inputSlot == 1 ? 0 : 1;
                case 3 -> inputSlot - 1;
                case 4 -> inputSlot < 2 ? inputSlot : inputSlot - 1;
                case 5 -> inputSlot;
                default -> throw new RuntimeException("there are only 5 gemstone Slots");
            };
        }
        return -1;
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