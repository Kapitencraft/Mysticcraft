package net.kapitencraft.mysticcraft.worldgen.structure;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.registry.ModStructurePieceTypes;
import net.kapitencraft.mysticcraft.registry.ModStructureTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class StoneCircleStructure extends Structure {
    public static final MapCodec<StoneCircleStructure> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            settingsCodec(i),
            StoneCircleStructureType.CODEC.fieldOf("occurrence_type").forGetter(s -> s.type)
    ).apply(i, StoneCircleStructure::new));


    private final StoneCircleStructureType type;

    public StoneCircleStructure(StructureSettings settings, StoneCircleStructureType type) {
        super(settings);
        this.type = type;
    }

    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(@NotNull GenerationContext context) {
        return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, p_227598_ -> this.generatePieces(p_227598_, context));
    }

    private void generatePieces(StructurePiecesBuilder builder, GenerationContext context) {
        int middleX = context.chunkPos().getMiddleBlockX();
        int middleZ = context.chunkPos().getMiddleBlockZ();
        int y = context.chunkGenerator().getFirstOccupiedHeight(middleX, middleZ, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        builder.addPiece(new StoneCircleStructure.Piece(context.structureTemplateManager(), this.type.getStructure(), new BlockPos(middleX, y, middleZ)));
    }

    @Override
    public @NotNull StructureType<?> type() {
        return ModStructureTypes.STONE_CIRCLE.get();
    }

    public static class Piece extends TemplateStructurePiece {

        public Piece(StructureTemplateManager structureTemplateManager, ResourceLocation location, BlockPos startPos) {
            super(
                    ModStructurePieceTypes.STONE_CIRCLE.value(),
                    0,
                    structureTemplateManager,
                    location,
                    location.toString(),
                    makeSettings(location),
                    startPos
            );
        }

        public Piece(StructureTemplateManager structureTemplateManager, CompoundTag tag) {
            super(ModStructurePieceTypes.STONE_CIRCLE.value(), tag, structureTemplateManager, Piece::makeSettings);
        }

        private static StructurePlaceSettings makeSettings(ResourceLocation location) {
            return new StructurePlaceSettings()
                    .setRotation(Rotation.NONE)
                    .setMirror(Mirror.NONE)
                    .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK)
                    .addProcessor(new GravityProcessor(Heightmap.Types.WORLD_SURFACE_WG, -1))
                    .setLiquidSettings(LiquidSettings.IGNORE_WATERLOGGING);
        }

        @Override
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {}
    }
}
