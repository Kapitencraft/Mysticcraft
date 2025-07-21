package net.kapitencraft.mysticcraft.tech.block.entity;

import net.kapitencraft.mysticcraft.capability.mana.IManaStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class GenericFueledGeneratorBlockEntity extends BlockEntity implements IManaStorage, MenuProvider {
    private static final int MAX_MANA = 10000;

    private final int maxMana;
    private int mana;
    private int burnTime;
    private int rate;

    private final ItemStackHandler items = new ItemHandler();

    public GenericFueledGeneratorBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, int maxMana) {
        super(pType, pPos, pBlockState);
        this.maxMana = maxMana;
    }

    //region persistence

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("mana", this.mana);
        pTag.putInt("burnTime", this.burnTime);
        pTag.putInt("rate", this.rate);
        pTag.put("inventory", this.items.serializeNBT());
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.mana = pTag.getInt("mana");
        this.burnTime = pTag.getInt("burnTime");
        this.rate = pTag.getInt("rate");
        this.items.deserializeNBT(pTag.getCompound("inventory"));
    }

    //endregion

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, GenericFueledGeneratorBlockEntity pBlockEntity) {
        if (pBlockEntity.mana > 0) {
            int mana = pBlockEntity.mana;
            for (Direction direction : Direction.values()) {
                BlockEntity entity = pLevel.getBlockEntity(pPos.relative(direction));
                if (entity instanceof IManaStorage manaStorage) {
                    if (manaStorage.canReceive())
                        pBlockEntity.mana -= manaStorage.receiveMana(pBlockEntity.mana, false);
                }
            }
            if (pBlockEntity.mana != mana) pBlockEntity.setChanged();
        }
        if (pBlockEntity.mana < pBlockEntity.maxMana) {
            if (pBlockEntity.burnTime > 0) {
                pBlockEntity.burnTime--;
                pBlockEntity.mana = Math.min(pBlockEntity.maxMana, pBlockEntity.mana + pBlockEntity.rate);
                pBlockEntity.setChanged();
            } else {
                ItemStack stack = pBlockEntity.items.extractItem(0, 1, false);
                if (!stack.isEmpty()) {
                    pBlockEntity.rate = pBlockEntity.getRate(stack);
                    pBlockEntity.burnTime = pBlockEntity.getBurnTime(stack);
                    pBlockEntity.setChanged();
                }
            }
        }
    }

    protected abstract int getRate(ItemStack stack);

    protected abstract int getBurnTime(ItemStack stack);

    @Override
    public int receiveMana(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractMana(int maxExtract, boolean simulate) {
        int pull = Math.min(maxExtract, mana);
        if (!simulate) {
            mana -= pull;
        }
        return pull;
    }

    @Override
    public int getManaStored() {
        return mana;
    }

    @Override
    public int getMaxManaStored() {
        return maxMana;
    }

    @Override
    public boolean canExtract() {
        return mana > 0;
    }

    @Override
    public boolean canReceive() {
        return false;
    }

    @Override
    public void setMana(int mana) {
        this.mana = mana;
    }

    public IItemHandler getItems() {
        return items;
    }

    public boolean isLit() {
        return this.burnTime > 0;
    }

    public int getLitProgress() {
        return 13 * this.burnTime / 200;
    }

    private class ItemHandler extends ItemStackHandler {
        public ItemHandler() {
            super(1);
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            GenericFueledGeneratorBlockEntity.this.setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return GenericFueledGeneratorBlockEntity.this.isValidFuel(stack);
        }
    }

    protected abstract boolean isValidFuel(@NotNull ItemStack stack);

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("mana", this.mana);
        tag.putInt("burnTime", this.burnTime);
        tag.putInt("rate", this.rate);
        return tag;
    }
}
