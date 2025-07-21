package net.kapitencraft.mysticcraft.tech.block.entity;

import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.capability.gemstone.IGemstoneItem;
import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.tech.gui.menu.PrismaticGeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PrismaticGeneratorBlockEntity extends GenericFueledGeneratorBlockEntity {
    private static final int MAX_MANA = 10000;

    public PrismaticGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PRISMATIC_GENERATOR.get(), pPos, pBlockState, MAX_MANA);
    }

    @Override
    protected int getRate(ItemStack stack) {
        GemstoneType.Rarity rarity = IGemstoneItem.getGemRarity(stack);
        GemstoneType type = IGemstoneItem.getGemstone(stack);
        return type == GemstoneType.SAPPHIRE ? 15 * (rarity.getLevel() + 1) : (int) type.getBaseBlockStrenght() * (rarity.getLevel() + 1);
    }

    @Override
    protected int getBurnTime(ItemStack stack) {
        return 200;
    }

    @Override
    protected boolean isValidFuel(@NotNull ItemStack stack) {
        return IGemstoneItem.getGemstone(stack) != GemstoneType.EMPTY;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.prismatic_generator");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        return new PrismaticGeneratorMenu(pContainerId, pPlayerInventory, this);
    }
}
