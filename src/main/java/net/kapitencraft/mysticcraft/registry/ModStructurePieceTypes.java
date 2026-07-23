package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.worldgen.structure.StoneCircleStructure;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface ModStructurePieceTypes {
    DeferredRegister<StructurePieceType> REGISTRY = MysticcraftMod.registry(Registries.STRUCTURE_PIECE);

    Holder<StructurePieceType> STONE_CIRCLE = template("stone_circle", StoneCircleStructure.Piece::new);

    private static Holder<StructurePieceType> contextless(String name, StructurePieceType.ContextlessType type) {
        return REGISTRY.register(name, () -> type);
    }

    private static Holder<StructurePieceType> template(String name, StructurePieceType.StructureTemplateType type) {
        return REGISTRY.register(name, () -> type);
    }
}
