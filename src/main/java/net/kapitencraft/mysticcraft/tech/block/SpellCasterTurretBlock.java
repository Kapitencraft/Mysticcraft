package net.kapitencraft.mysticcraft.tech.block;

import com.mojang.serialization.MapCodec;
import net.kapitencraft.mysticcraft.block.entity.AbstractMenuBlock;
import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.tech.block.entity.SpellCasterTurretBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SpellCasterTurretBlock extends AbstractMenuBlock {
    private static final MapCodec<SpellCasterTurretBlock> CODEC = MapCodec.unit(SpellCasterTurretBlock::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public SpellCasterTurretBlock() {
        super(Properties.ofFullCopy(Blocks.ANCIENT_DEBRIS));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SpellCasterTurretBlockEntity(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.SPELL_CASTER_TURRET.get(), SpellCasterTurretBlockEntity::tick);
    }
}
