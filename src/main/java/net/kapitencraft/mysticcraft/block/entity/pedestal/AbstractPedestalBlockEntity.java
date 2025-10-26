package net.kapitencraft.mysticcraft.block.entity.pedestal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractPedestalBlockEntity extends BlockEntity {
    private final ItemStackHandler item = new ItemStackHandler(1) {

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            AbstractPedestalBlockEntity.this.setChanged();
        }
    };
    protected float rotation;

    protected AbstractPedestalBlockEntity(BlockEntityType<? extends AbstractPedestalBlockEntity> type, BlockPos pPos, BlockState pBlockState) {
        super(type, pPos, pBlockState);
    }

    public ItemStack getItem() {
        return item.getStackInSlot(0);
    }

    public void setItem(ItemStack stack) {
        this.item.setStackInSlot(0, stack);
    }

    public float getRenderingRotation() {
        return this.level.getGameTime() * (float) Math.PI;
    }

    public void drops() {
        BlockPos pos = this.worldPosition;
        Containers.dropItemStack(this.level, pos.getX(), pos.getY(), pos.getZ(), this.getItem());
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.put("inventory", this.item.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        this.item.deserializeNBT(registries, tag.getCompound("inventory"));
        super.loadAdditional(tag, registries);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    public ItemStack insertItem(ItemStack playerItem) {
        return this.item.insertItem(0, playerItem, false);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (!level.isClientSide()) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }
}
