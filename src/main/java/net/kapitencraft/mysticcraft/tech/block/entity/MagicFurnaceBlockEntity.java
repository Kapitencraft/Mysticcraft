package net.kapitencraft.mysticcraft.tech.block.entity;

import net.kapitencraft.mysticcraft.capability.mana.IManaStorage;
import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.kapitencraft.mysticcraft.tech.block.UpgradableBlockEntity;
import net.kapitencraft.mysticcraft.tech.gui.menu.MagicFurnaceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MagicFurnaceBlockEntity extends UpgradableBlockEntity implements IManaStorage, MenuProvider {
    private static final int MAX_MANA = 1000;

    private final ItemHandler items = new ItemHandler();

    private int mana, cookTime;
    private AbstractCookingRecipe recipe;

    public MagicFurnaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MAGIC_FURNACE.get(), pPos, pBlockState);
    }

    //region persistence

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("mana", this.mana);
        pTag.putInt("cookTime", this.cookTime);
        pTag.put("inventory", this.items.serializeNBT());
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        this.mana = pTag.getInt("mana");
        this.cookTime = pTag.getInt("cookTime");
        this.items.deserializeNBT(pTag.getCompound("inventory"));
        this.updateRecipe();
    }

    //endregion

    @Override
    public int receiveMana(int maxReceive, boolean simulate) {
        int received = Math.min(MAX_MANA, this.mana + maxReceive) - this.mana;
        if (!simulate) mana += received;
        return received;
    }

    @Override
    public int extractMana(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getManaStored() {
        return mana;
    }

    @Override
    public int getMaxManaStored() {
        return MAX_MANA;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return mana < MAX_MANA;
    }

    @Override
    public void setMana(int mana) {
        this.mana = mana;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.magic_furnace");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new MagicFurnaceMenu(pContainerId, pPlayerInventory, this);
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, MagicFurnaceBlockEntity blockEntity) {
        if (blockEntity.canInsertIntoOutput(level) && blockEntity.mana > 10) {
            blockEntity.mana-=10;
            if ((blockEntity.cookTime += (1 + blockEntity.speed)) > blockEntity.recipe.getCookingTime()) {
                ItemStack stack = blockEntity.recipe.getResultItem(level.registryAccess());
                blockEntity.items.insertItem(1, stack.copyWithCount(stack.getCount() * (1 + blockEntity.parallel)), false);
                blockEntity.items.extractItem(0, 1 + blockEntity.parallel, false);
                blockEntity.cookTime = 0;
            }
        } else if (blockEntity.cookTime > 0) blockEntity.cookTime--;
        else return;
        blockEntity.setChanged();
    }

    private boolean canInsertIntoOutput(Level level) {
        if (this.recipe != null) {
            ItemStack result = this.recipe.getResultItem(level.registryAccess());
            ItemStack output = this.items.getStackInSlot(1);
            return output.isEmpty() || (output.is(result.getItem()) && output.getCount() + result.getCount() <= this.items.getSlotLimit(1));
        }
        return false;
    }

    public ItemStackHandler getItems() {
        return items;
    }

    public int getBurnProgress() {
        return this.recipe == null ? 0 : 24 * cookTime / recipe.getCookingTime();
    }

    public void drops() {
        Containers.dropContents(this.level, this.worldPosition, this.items.getItems());
        super.drops();
    }

    //region upgrade
    private int parallel, speed;

    @Override
    public boolean canUpgrade(ItemStack upgradeModule) {
        return true;
    }

    @Override
    public void upgrade(ItemStack stack) {
        if (stack.is(ModItems.PARALLEL_PROCESSING_UPGRADE.get())) parallel++;
        if (stack.is(ModItems.SPEED_UPGRADE.get())) speed++;
    }

    @Override
    public int upgradeSlots() {
        return 4;
    }

    //endregion

    private class ItemHandler extends ItemStackHandler {
        public ItemHandler() {
            super(2);
        }

        @Override
        protected void onContentsChanged(int slot) {
            if (slot == 0) {
                MagicFurnaceBlockEntity.this.updateRecipe();
            }
            super.onContentsChanged(slot);
            MagicFurnaceBlockEntity.this.setChanged();
        }

        public NonNullList<ItemStack> getItems() {
            return this.stacks;
        }
    }

    private void updateRecipe() {
        Level level = MagicFurnaceBlockEntity.this.level;
        if (level != null) {
            ItemStack stack = items.getStackInSlot(0);
            MagicFurnaceBlockEntity.this.recipe = level.getRecipeManager()
                    .getRecipeFor(RecipeType.SMELTING, new SimpleContainer(stack), level).orElse(null);
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("mana", this.mana);
        tag.putInt("cookTime", this.cookTime);
        tag.put("inventory", this.items.serializeNBT());
        return tag;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
