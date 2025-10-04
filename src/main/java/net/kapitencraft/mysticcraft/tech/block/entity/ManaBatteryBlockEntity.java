package net.kapitencraft.mysticcraft.tech.block.entity;

import net.kapitencraft.mysticcraft.capability.mana.IManaStorage;
import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.tech.gui.menu.ManaBatteryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ManaBatteryBlockEntity extends BlockEntity implements IManaStorage, MenuProvider {
    private static final int MAX_MANA = 10000000;

    private int storedMana;

    public ManaBatteryBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MANA_BATTERY.get(), pPos, pBlockState);
    }

    @Override
    public int receiveMana(int maxReceive, boolean simulate) {
        int received = Math.min(MAX_MANA, this.storedMana + maxReceive) - this.storedMana;
        if (!simulate) storedMana += received;
        return received;
    }

    @Override
    public int extractMana(int maxExtract, boolean simulate) {
        int pull = Math.min(maxExtract, this.storedMana);
        if (!simulate) {
            this.storedMana -= pull;
        }
        return pull;
    }

    @Override
    public int getManaStored() {
        return storedMana;
    }

    @Override
    public int getMaxManaStored() {
        return MAX_MANA;
    }

    @Override
    public boolean canExtract() {
        return this.storedMana > 0;
    }

    @Override
    public boolean canReceive() {
        return this.storedMana < MAX_MANA;
    }

    @Override
    public void setMana(int mana) {
        this.storedMana = mana;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.mana_battery");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ManaBatteryMenu(pContainerId, pPlayerInventory, this);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("StoredMana", this.storedMana);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.storedMana = pTag.getInt("StoredMana");
    }
}
