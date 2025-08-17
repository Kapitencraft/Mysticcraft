package net.kapitencraft.mysticcraft.tech.block.entity;

import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.tech.gui.menu.VulcanicGeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VulcanicGeneratorBlockEntity extends GenericFueledGeneratorBlockEntity {

    public VulcanicGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.VULCANIC_GENERATOR.get(), pPos, pBlockState, 10000);
    }

    @Override
    protected int getRate(ItemStack stack) {
        return 10;
    }

    @Override
    protected int getBurnTime(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, null);
    }

    @Override
    public boolean isValidFuel(@NotNull ItemStack stack) {
        return getBurnTime(stack) > 0;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.vulcanic_generator");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new VulcanicGeneratorMenu(pContainerId, pPlayerInventory, this);
    }

    @Override
    public int upgradeSlots() {
        return 2;
    }
}
