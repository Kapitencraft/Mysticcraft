package net.kapitencraft.mysticcraft.worldgen.gemstone;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.ModBlockProperties;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneCrystal;
import net.kapitencraft.mysticcraft.block.special.GemstoneSeedBlock;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GemstoneGrowth {
    private static final Block GEMSTONE = ModBlocks.GEMSTONE_BLOCK.getBlock();
    private static final Block GEMSTONE_CRYSTAL = ModBlocks.GEMSTONE_CRYSTAL.getBlock();
    private static final BlockState GEMSTONE_BLOCK = GEMSTONE.defaultBlockState();
    private static final BlockState GEMSTONE_CRYSTAL_STATE = GEMSTONE_CRYSTAL.defaultBlockState();
    public static final float DEFAULT_MAIN_CHANCE = 0.2f;

    public static void trySpawnAndGrowSeed(LevelAccessor level, BlockPos pos, int amount, GemstoneType type, GemstoneSeedBlock.MaterialType matType, float baseMainChance, RandomSource source) {
        List<Direction> directions = scanPossibleDirections(pos, level, type, List.of());
        Direction main;
        if (directions.contains(Direction.UP)) main = Direction.UP;
        else if (directions.contains(Direction.DOWN)) main = Direction.DOWN;
        else main = MathHelper.pickRandom(directions);
        if (main == null) return;
        level.setBlock(pos, ModBlocks.GEMSTONE_SEED.getBlock().defaultBlockState()
                .setValue(ModBlockProperties.STONE_TYPE, matType)
                .setValue(ModBlockProperties.GEMSTONE_TYPE, type)
                .setValue(BlockStateProperties.FACING, main), 3); //spawn gemstone seed
        growCrystal(level, pos, amount, baseMainChance, source);
    }

    public static void growCrystal(LevelAccessor level, BlockPos pos, int amount, float baseMainChance, RandomSource source) {
        BlockPos origin = pos.immutable();
        Direction main;
        GemstoneType type;
        try {
            BlockState seedState = level.getBlockState(pos);
            main = seedState.getValue(BlockStateProperties.FACING);
            type = seedState.getValue(ModBlockProperties.GEMSTONE_TYPE);
        } catch (Exception e) {
            MysticcraftMod.LOGGER.warn(Markers.GEMSTONE_BUILDER, "unable to grow crystal at {}: {}", pos, e.getMessage());
            return;
        }
        for (int i = 0; i < amount; i++) {
            pos = origin;
            float mainChance = baseMainChance;
            List<BlockPos> visited = new ArrayList<>();
            while (true) {
                Direction direction = random(level, pos, main, mainChance, type, source, visited);
                if (direction == null) {
                    break;
                }
                pos = pos.relative(direction);
                BlockState state = level.getBlockState(pos);
                visited.add(pos);
                if (state.is(Blocks.AIR) && level.hasChunkAt(pos)) {
                    level.setBlock(pos, GEMSTONE_CRYSTAL_STATE
                            .setValue(ModBlockProperties.GEMSTONE_TYPE, type)
                            .setValue(GemstoneCrystal.SIZE, GemstoneCrystal.Size.SMALL)
                            .setValue(BlockStateProperties.FACING, direction), 3
                    );
                    break;
                } else if (state.is(GEMSTONE_CRYSTAL)) {
                    GemstoneCrystal.Size size = state.getValue(GemstoneCrystal.SIZE);
                    if (size != GemstoneCrystal.Size.CLUSTER) {
                        level.setBlock(pos, state.setValue(GemstoneCrystal.SIZE, size.next()), 3);
                    } else {
                        level.setBlock(pos, GEMSTONE_BLOCK
                                .setValue(ModBlockProperties.GEMSTONE_TYPE, type), 3
                        );
                    }
                    break;
                } else if (state.is(GEMSTONE)) {
                    mainChance = Math.min(0.8f, mainChance + 0.05f);
                }
            }
        }
    }

    private static List<Direction> scanPossibleDirections(BlockPos pos, LevelAccessor level, GemstoneType type, List<BlockPos> visited) {
        List<Direction> list = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            BlockPos pos1 = pos.relative(direction);
            BlockState state = level.getBlockState(pos1);
            if (!visited.contains(pos1) && level.hasChunkAt(pos) && (state.is(Blocks.AIR) || ((state.is(GEMSTONE) || state.is(GEMSTONE_CRYSTAL) && state.getValue(ModBlockProperties.GEMSTONE_TYPE) == type)))) {
                list.add(direction);
            }
        }
        return list;
    }

    @Nullable
    private static Direction random(LevelAccessor level, BlockPos pos, Direction main, float mainChance, GemstoneType type, RandomSource source, List<BlockPos> visited) {
        List<Direction> possible = scanPossibleDirections(pos, level, type, visited);
        if (Math.random() < mainChance && main != null && possible.contains(main)) {
            return main;
        } else {
            return MathHelper.pickRandom(possible, source);
        }
    }
}
