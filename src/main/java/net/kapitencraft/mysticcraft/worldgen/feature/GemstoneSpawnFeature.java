package net.kapitencraft.mysticcraft.worldgen.feature;


import com.mojang.serialization.Codec;
import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneSeedBlock;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.config.CommonModConfig;
import net.kapitencraft.mysticcraft.inst.MysticcraftServer;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.kapitencraft.mysticcraft.worldgen.gemstone.GemstoneGrowth;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;

/**
 * Minecraft Didn't think so<br>
 * so: f*** mc
 */
public class GemstoneSpawnFeature extends Feature<GemstoneSpawnFeature.Config> {
    public GemstoneSpawnFeature() {
        super(Config.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<Config> pContext) {
        if (!pContext.level().getBlockState(pContext.origin()).is(Blocks.AIR)) return false;
        spawnCrystals(pContext.level(), pContext.origin(), pContext.random());
        return true;
    }

    private static void spawnCrystals(WorldGenLevel pLevel, BlockPos pPos, RandomSource source) {
        int amount = Mth.nextInt(source, CommonModConfig.getMinSeedSpawns(), CommonModConfig.getMaxSeedSpawns());
        Holder<Biome> biomeHolder = pLevel.getBiome(pPos);
        for (int i = 0; i < amount; i++) { //spawn a random amount of gemstone seeds
            GemstoneType gemstoneType = MysticcraftServer.getInstance().decorator.makeType(biomeHolder); //make gemstone type
            if (gemstoneType == null) continue; //ensure gemstone type != null
            int xRot = Mth.nextInt(source, 0, 180);
            int yRot = Mth.nextInt(source, 0, 359); //create a random rotation to spawn gemstone seed in
            Vec3 viewVec = MathHelper.calculateViewVector(90 - xRot, yRot);
            Vec3 max = viewVec.scale(30); //scale rotation vec to a max distance of 30 blocks
            BlockHitResult result = pLevel.clip(new ClipContext(viewVec.add(pPos.getCenter()), max.add(pPos.getCenter()), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, null)); //create hit result
            BlockPos current = result.getBlockPos();
            if (!pLevel.hasChunkAt(current)) continue;
            Block block = pLevel.getBlockState(current).getBlock(); //get block from hit result
            MysticcraftMod.LOGGER.debug(Markers.GEMSTONE_BUILDER, "spawning gemstone seed at {}", current);
            Arrays.stream(GemstoneSeedBlock.MaterialType.values()).filter(materialType -> materialType.getBlock() == block).findFirst().ifPresent(type -> {
                GemstoneGrowth.trySpawnAndGrowSeed(pLevel, current, Mth.nextInt(source, CommonModConfig.getMinGemstoneIterations(), CommonModConfig.getMaxGemstoneIterations()), gemstoneType, type, GemstoneGrowth.DEFAULT_MAIN_CHANCE, source); //let seed grow
            });
        }
    }

    public static class Config implements FeatureConfiguration {
        static final Codec<Config> CODEC = Codec.unit(Config::new);
    }
}
