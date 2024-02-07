package net.kapitencraft.mysticcraft.block.entity;

import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilMenu;
import net.kapitencraft.mysticcraft.helpers.InventoryHelper;
import net.kapitencraft.mysticcraft.init.ModBlockEntities;
import net.kapitencraft.mysticcraft.item.capability.dungeon.IPrestigeAbleItem;
import net.kapitencraft.mysticcraft.item.capability.dungeon.IStarAbleItem;
import net.kapitencraft.mysticcraft.item.capability.reforging.Reforge;
import net.kapitencraft.mysticcraft.item.capability.reforging.Reforges;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReforgeAnvilBlockEntity extends BlockEntity implements MenuProvider {
    public final ReforgeAnvilItemStackHandler handler = new ReforgeAnvilItemStackHandler();
    LazyOptional<ReforgeAnvilItemStackHandler> lazyItemHandler = LazyOptional.empty();

    public ReforgeAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REFORGING_ANVIL.get(), pos, state);
    }

    public static <E extends ReforgeAnvilBlockEntity> void tick(Level ignoredLevel, BlockPos ignoredPos, BlockState ignoredState, E blockEntity) {
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", handler.serializeNBT());
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        handler.deserializeNBT(nbt.getCompound("inventory"));
        super.load(nbt);
    }

    public String updateButtonPress(Player player) {
        ItemStack stack = getStack(false);
        if (InventoryHelper.hasInInventory(List.of(new ItemStack(Items.EMERALD, 5)), player) && Reforge.reforgeAble(stack)) {
            InventoryHelper.removeFromInventory(new ItemStack(Items.EMERALD, 5), player);
            return Reforges.applyRandom(false, stack).getRegistryName();
        }
        return null;
    }

    public ItemStack getStack(boolean upgrade) {
        return this.handler.getStackInSlot(upgrade ? 1 : 0);
    }

    public void updateButtonPress(String s) {
        ItemStack stack = this.handler.getStackInSlot(0);
        if (stack.getItem() instanceof TieredItem || stack.getItem() instanceof ArmorItem) {
            Reforge reforge = Reforges.getByName(s);
            reforge.saveToStack(stack);
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.mysticcraft.reforge_anvil");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player p_39956_) {
        return new ReforgeAnvilMenu(id, inventory, this);
    }

    private static class ReforgeAnvilItemStackHandler extends ItemStackHandler {

        public ReforgeAnvilItemStackHandler() {super(2);}

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return slot == 1 ? stack.getItem() instanceof IPrestigeAbleItem || stack.getItem() instanceof IStarAbleItem :
                    stack.getItem() instanceof TieredItem || stack.getItem() instanceof ArmorItem;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 2;
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }



    @Override
    public void onLoad() {
        super.onLoad();
        this.lazyItemHandler = LazyOptional.of(()-> handler);
    }
}