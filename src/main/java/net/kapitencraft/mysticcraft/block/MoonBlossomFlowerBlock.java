package net.kapitencraft.mysticcraft.block;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class MoonBlossomFlowerBlock extends FlowerBlock {
    public static final BooleanProperty BLOOMING = BooleanProperty.create("blooming");

    public MoonBlossomFlowerBlock() {
        super(MobEffects.NIGHT_VISION, 20, Properties.ofFullCopy(Blocks.WITHER_ROSE));
        registerDefaultState(defaultBlockState().setValue(BLOOMING, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(BLOOMING);
    }
}
