package net.kapitencraft.mysticcraft.data_gen.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public interface ModStructurePools {

    private static ResourceKey<StructureTemplatePool> create(String s) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, MysticcraftMod.res(s));
    }

    static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);
    }
}
