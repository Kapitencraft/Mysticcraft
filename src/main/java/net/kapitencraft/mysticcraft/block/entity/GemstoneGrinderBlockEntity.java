package net.kapitencraft.mysticcraft.block.entity;

import net.kapitencraft.mysticcraft.gui.GUISlotBlockItem;
import net.kapitencraft.mysticcraft.gui.gemstone_grinder.GemstoneGrinderMenu;
import net.kapitencraft.mysticcraft.init.ModBlockEntities;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.ChatFormatting;
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
import net.minecraft.world.item.Item;
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

import java.util.List;

public class GemstoneGrinderBlockEntity extends BlockEntity implements MenuProvider {
    public final ModItemStackHandler itemHandler = new ModItemStackHandler(6);

    public static final Item GUI_SLOT_LOCK = ((GUISlotBlockItem) ModItems.GUI_SLOT_BLOCK_ITEM.get()).putTooltip(List.of(Component.literal("This slot is locked.").withStyle(ChatFormatting.RED)));
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
        this.itemHandler.setStackInSlot(1, new ItemStack(GUI_SLOT_LOCK));
        this.itemHandler.setStackInSlot(2, new ItemStack(GUI_SLOT_LOCK));
        this.itemHandler.setStackInSlot(3, new ItemStack(GUI_SLOT_LOCK));
        this.itemHandler.setStackInSlot(4, new ItemStack(GUI_SLOT_LOCK));
        this.itemHandler.setStackInSlot(5, new ItemStack(GUI_SLOT_LOCK));

    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Gem Infusing Station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
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
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = MISCTools.ContainerOf(this.itemHandler);

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }


    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(2).getItem() == stack.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }

    public static <E extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, E e) {

    }

    public class ModItemStackHandler extends ItemStackHandler {

        public ModItemStackHandler(int amount) {
            super(amount);
        }
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

    }
}