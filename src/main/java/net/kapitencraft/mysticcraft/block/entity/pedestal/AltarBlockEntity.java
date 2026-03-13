package net.kapitencraft.mysticcraft.block.entity.pedestal;

import net.kapitencraft.kap_lib.core.util.Vec2i;
import net.kapitencraft.mysticcraft.registry.ModBlockEntities;
import net.kapitencraft.mysticcraft.registry.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public class AltarBlockEntity extends AbstractPedestalBlockEntity {
    private int craftingProgress = 0;

    public static final List<Vec2i> OFFSETS = List.of(
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

    public BlockPos[] getPedestalPositions() {
        return pedestalPositions;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AltarBlockEntity altarBlockEntity) {
        AltarRecipeInput input = new AltarRecipeInput();
        PedestalBlockEntity[] pedestals = new PedestalBlockEntity[8];
        input.setAltarItem(altarBlockEntity.getItem());
        if (input.getItem(0).isEmpty()) return;
        for (int i = 0; i < altarBlockEntity.pedestalPositions.length; i++) {
            BlockEntity entity = level.getBlockEntity(altarBlockEntity.pedestalPositions[i]);
            if (entity instanceof PedestalBlockEntity entity1 && !entity1.getItem().isEmpty()) {
                input.setPedestalItem(i, entity1.getItem());
                pedestals[i] = entity1;
            } else {
                altarBlockEntity.craftingProgress = 0;
                return;
            } //pedestals have not been set up
        }
        Optional<RecipeHolder<AltarRecipe>> recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.ALTAR.get(), input, level);
        if (recipe.isPresent()) {
            if (altarBlockEntity.craftingProgress++ > 160 && (altarBlockEntity.craftingProgress & 7) == 0) {
                EntityType.LIGHTNING_BOLT.spawn((ServerLevel) level, pos, MobSpawnType.TRIGGERED).setVisualOnly(true);
            }
            if (altarBlockEntity.craftingProgress > 200) craft(level, altarBlockEntity, pedestals, recipe.get().value());
            altarBlockEntity.setChanged();
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, AltarBlockEntity altarBlockEntity) {
        if ((altarBlockEntity.craftingProgress & 1) == 1) {
            PedestalBlockEntity[] pedestals = new PedestalBlockEntity[8];
            for (int i = 0; i < altarBlockEntity.pedestalPositions.length; i++) {
                BlockEntity entity = level.getBlockEntity(altarBlockEntity.pedestalPositions[i]);
                if (entity instanceof PedestalBlockEntity entity1 && !entity1.getItem().isEmpty()) {
                    pedestals[i] = entity1;
                } else {
                    altarBlockEntity.craftingProgress = 0;
                    return;
                } //pedestals have not been set up
            }
            for (PedestalBlockEntity pedestal : pedestals) {
                BlockPos origin = pedestal.getBlockPos();
                BlockPos direction = pos.subtract(origin);
                level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, pedestal.getItem()), origin.getX() + .5, origin.getY() + 1.2, origin.getZ() + .5, direction.getX() * .25, 0, direction.getZ() * .25);
            }
        }
    }

    private static void craft(Level level, AltarBlockEntity altarBlockEntity, PedestalBlockEntity[] pedestals, AltarRecipe recipe) {
        for (PedestalBlockEntity pedestal : pedestals) {
            pedestal.shrinkItem();
        }
        altarBlockEntity.setItem(recipe.getResultItem(level.registryAccess()));
        altarBlockEntity.craftingProgress = 0;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("craftingProgress", this.craftingProgress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        this.craftingProgress = tag.getInt("craftingProgress");
    }
}
