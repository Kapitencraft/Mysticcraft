package net.kapitencraft.mysticcraft.block.entity;

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
        emptyItemHandler(this.itemHandler);
    }

    public static void emptyItemHandler(ItemStackHandler handler) {
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

        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = MiscUtils.containerOf(this.itemHandler);
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static <E extends BlockEntity> void tick(Level ignoredLevel, BlockPos ignoredPos, BlockState ignoredState, E ignoredE) {
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