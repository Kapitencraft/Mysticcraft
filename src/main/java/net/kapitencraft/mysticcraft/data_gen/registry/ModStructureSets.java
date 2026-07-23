package net.kapitencraft.mysticcraft.data_gen.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.worldgen.structure.StoneCircleStructureType;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.ArrayList;
import java.util.List;

public interface ModStructureSets {
    ResourceKey<StructureSet> STONE_CIRCLE = create("stone_circle");

    static ResourceKey<StructureSet> create(String s) {
        return ResourceKey.create(Registries.STRUCTURE_SET, MysticcraftMod.res(s));
    }

    static void bootstrap(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
        List<StructureSet.StructureSelectionEntry> stoneCircleEntries = new ArrayList<>();
        for (StoneCircleStructureType value : StoneCircleStructureType.values()) {
            stoneCircleEntries.add(new StructureSet.StructureSelectionEntry(
                    structures.getOrThrow(ResourceKey.create(Registries.STRUCTURE, value.getStructure())), 1
            ));
        }

        context.register(STONE_CIRCLE, new StructureSet(
                stoneCircleEntries,
                new RandomSpreadStructurePlacement(
                        7, 4, RandomSpreadType.LINEAR, 502671038
                )
        ));
    }
}
