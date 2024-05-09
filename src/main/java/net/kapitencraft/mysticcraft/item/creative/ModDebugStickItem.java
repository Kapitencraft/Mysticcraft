package net.kapitencraft.mysticcraft.item.creative;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.ModBlockProperties;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneCrystal;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.worldgen.gemstone.GemstoneGrowth;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class ModDebugStickItem extends Item implements IModItem {
    public ModDebugStickItem() {
        super(new Properties().rarity(Rarity.EPIC));
    }

    private BlockPos start, end;

    @Override
    public boolean isFoil(@NotNull ItemStack p_41453_) {
        return true;
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        grow(context.getLevel(), context.getClickedPos());
        return InteractionResult.SUCCESS;
    }

    private void grow(Level level, BlockPos pos) {
        GemstoneGrowth.growCrystal(level, pos, 1000, GemstoneType.JASPER, GemstoneGrowth.DEFAULT_MAIN_CHANCE, MysticcraftMod.RANDOM_SOURCE);
    }


    private BlockState makeState(BlockState stateIn) {
        Direction facing = stateIn.getValue(BlockStateProperties.FACING);
        GemstoneCrystal.Size size = stateIn.is(Blocks.SMALL_AMETHYST_BUD) ? GemstoneCrystal.Size.SMALL :
                stateIn.is(Blocks.MEDIUM_AMETHYST_BUD) ? GemstoneCrystal.Size.MEDIUM :
                        stateIn.is(Blocks.LARGE_AMETHYST_BUD) ? GemstoneCrystal.Size.LARGE : GemstoneCrystal.Size.CLUSTER;
        return ModBlocks.GEMSTONE_CRYSTAL.getBlock().defaultBlockState().setValue(BlockStateProperties.FACING, facing).setValue(GemstoneCrystal.SIZE, size).setValue(ModBlockProperties.GEMSTONE_TYPE, GemstoneType.EMPTY);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack p_41454_) {
        return 72;
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }
}
