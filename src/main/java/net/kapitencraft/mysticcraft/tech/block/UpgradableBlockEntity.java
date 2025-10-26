package net.kapitencraft.mysticcraft.tech.block;

import net.kapitencraft.mysticcraft.tech.IUpgradeable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public abstract class UpgradableBlockEntity extends BlockEntity implements IUpgradeable {
    private final UpgradeContainer upgrades;

    public UpgradableBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);

        upgrades = new UpgradeContainer();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.put("upgrades", upgrades.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        upgrades.deserializeNBT(registries, tag.getCompound("upgrades"));
        this.setChanged();
    }

    public void drops() {
        Containers.dropContents(this.level, this.worldPosition, this.upgrades.getItems());
    }

    @Override
    public ItemStackHandler getUpgrades() {
        return upgrades;
    }

    private class UpgradeContainer extends ItemStackHandler {
        public UpgradeContainer() {
            super(8);
        }

        @Override
        protected void onLoad() {
            this.stacks.forEach(s -> {
                if (!s.isEmpty()) UpgradableBlockEntity.this.upgrade(s);
            });
        }

        public NonNullList<ItemStack> getItems() {
            return stacks;
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            UpgradableBlockEntity.this.setChanged();
        }
    }
}
