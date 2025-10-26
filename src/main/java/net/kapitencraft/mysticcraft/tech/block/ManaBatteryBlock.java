package net.kapitencraft.mysticcraft.tech.block;

import com.mojang.serialization.MapCodec;
import net.kapitencraft.mysticcraft.block.entity.AbstractMenuBlock;
import net.kapitencraft.mysticcraft.tech.block.entity.ManaBatteryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ManaBatteryBlock extends AbstractMenuBlock {
    public static final MapCodec<ManaBatteryBlock> CODEC = simpleCodec(ManaBatteryBlock::new);

    private ManaBatteryBlock(Properties properties) {
        super(properties);
    }

    public ManaBatteryBlock() {
        this(Properties.ofFullCopy(Blocks.AMETHYST_BLOCK));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ManaBatteryBlockEntity(pPos, pState);
    }
}
