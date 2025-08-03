package net.kapitencraft.mysticcraft.block.entity.pedestal;

import net.kapitencraft.kap_lib.util.Vec2i;
import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Optional;

public class AltarBlockEntity extends AbstractPedestalBlockEntity {
    private static final List<Vec2i> OFFSETS = List.of(
            new Vec2i(3, 0),
            new Vec2i(2, 2),
            new Vec2i(0, 3),
            new Vec2i(-2, 2),
            new Vec2i(-3, 0),
            new Vec2i(-2, -2),
            new Vec2i(0, -3),
            new Vec2i(2, -2)
    );

    private final BlockPos[] pedestalPositions;

    public AltarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALTAR.get(), pPos, pBlockState);
        BlockPos[] pedestalPositions = new BlockPos[8];
        for (int i = 0; i < OFFSETS.size(); i++) {
            Vec2i offset = OFFSETS.get(i);
            pedestalPositions[i] = pPos.offset(offset.x, 0, offset.y);
        }
        this.pedestalPositions = pedestalPositions;
    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = this.worldPosition;
        return new AABB(pos, pos.offset(1, 1, 1)).inflate(3, 0, 3);
    }

    public BlockPos[] getPedestalPositions() {
        return pedestalPositions;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AltarBlockEntity altarBlockEntity) {
        SimpleContainer container = new SimpleContainer(9);
        PedestalBlockEntity[] pedestals = new PedestalBlockEntity[8];
        container.setItem(0, altarBlockEntity.getItem());
        if (container.getItem(0).isEmpty()) return;
        for (int i = 0; i < altarBlockEntity.pedestalPositions.length; i++) {
            BlockEntity entity = level.getBlockEntity(altarBlockEntity.pedestalPositions[i]);
            if (entity instanceof PedestalBlockEntity entity1) {
                container.setItem(i + 1, entity1.getItem());
                pedestals[i] = entity1;
            } else return; //pedestals have not been set up
        }
        Optional<AltarRecipe> recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.ALTAR.get(), container, level);
        if (recipe.isPresent()) {
            for (PedestalBlockEntity pedestal : pedestals) {
                pedestal.shrinkItem();
            }
            altarBlockEntity.setItem(recipe.get().getResultItem(level.registryAccess()));
            altarBlockEntity.setChanged();
        }
    }
}
