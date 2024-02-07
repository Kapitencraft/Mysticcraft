package net.kapitencraft.mysticcraft.block.entity;

import net.kapitencraft.mysticcraft.api.Reference;
import net.kapitencraft.mysticcraft.gui.gemstone_grinder.GemstoneGrinderMenu;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModBlockEntities;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneHelper;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneItem;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GemstoneGrinderBlockEntity extends BlockEntity implements MenuProvider {
    public final GemstoneGrinderItemStackHandler itemHandler = new GemstoneGrinderItemStackHandler(6);

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

    public void emptyItemHandler(boolean checkHasApplicable) {
        if (this.level != null && this.level.isClientSide()) return;
        GemstoneGrinderItemStackHandler handler = this.itemHandler;
        if (checkHasApplicable && GemstoneHelper.hasCapability(handler.getStackInSlot(0))) return;
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
        nbt.put("queue", toReturnTag);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = MiscHelper.containerOf(this.itemHandler);
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public int getSlotForItem(int slotId) {
        ItemStack applicable = this.itemHandler.getStackInSlot(0);
        return getSlotForItem(slotId, applicable);
    }

    public static int getSlotForItem(int slotId, ItemStack applicable) {
        Reference<Integer> reference = Reference.of(0);
        GemstoneHelper.getCapability(applicable, iGemstoneHandler -> {
            reference.setValue(switch (iGemstoneHandler.getSlotAmount()) {
                case 1 -> 0;
                case 2 -> slotId == 2 ? 0 : 1;
                case 3 -> slotId - 2;
                case 4 -> slotId < 3 ? slotId : slotId - 1;
                case 5 -> slotId - 1;
                default -> throw new RuntimeException("there are only 5 gemstone Slots");
            });
        });
        return reference.getIntValue();
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
            return (slot == 0 && GemstoneHelper.hasCapability(stack)) || (slot >= 1 && slot <= 5 && (stack.getItem() instanceof GemstoneItem || stack.getItem() == ModItems.MISSING_GEMSTONE_SLOT.get()));
        }

        @Override
        public int getSlotLimit(int slot) {
                return 1;
        }

    }
}