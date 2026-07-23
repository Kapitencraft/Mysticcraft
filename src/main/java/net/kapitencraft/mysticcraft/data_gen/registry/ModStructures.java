package net.kapitencraft.mysticcraft.data_gen.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.worldgen.structure.StoneCircleStructure;
import net.kapitencraft.mysticcraft.worldgen.structure.StoneCircleStructureType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public interface ModStructures {

    private static ResourceKey<Structure> create(String s) {
        return ResourceKey.create(Registries.STRUCTURE, MysticcraftMod.res(s));
    }

    static void bootstrap(BootstrapContext<Structure> context) {

        for (StoneCircleStructureType value : StoneCircleStructureType.values()) {
            context.register(ResourceKey.create(Registries.STRUCTURE, value.getStructure()),
                    new StoneCircleStructure(
                            new Structure.StructureSettings(
                                    context.lookup(Registries.BIOME).getOrThrow(value.getBiomes())
                            ),
                            value
                    )
            );
        }
    }
}
