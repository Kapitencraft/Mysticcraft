package net.kapitencraft.mysticcraft.tech.block;

import net.kapitencraft.mysticcraft.tech.IUpgradeable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public abstract class UpgradableBlockEntity extends BlockEntity implements IUpgradeable {
    private final UpgradeContainer upgrades;

    public UpgradableBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);

        upgrades = new UpgradeContainer();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.put("upgrades", upgrades.serializeNBT());
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        upgrades.deserializeNBT(pTag.getCompound("upgrades"));
        this.setChanged();
    }

    public void drops() {
        Containers.dropContents(this.level, this.worldPosition, this.upgrades.getItems());
    }

    @Override
    public ItemStackHandler getUpgrades() {
        return upgrades;
    }

    private static class UpgradeContainer extends ItemStackHandler {
        public UpgradeContainer() {
            super(8);
        }

        public NonNullList<ItemStack> getItems() {
            return stacks;
        }
    }
}
