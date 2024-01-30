package net.kapitencraft.mysticcraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public interface ModEntityTags {
    TagKey<EntityType<?>> OG_NETHER_MOBS = createForge("og_nether_mobs");
    TagKey<EntityType<?>> NETHER_MOBS = createForge("nether_mobs");


    private static TagKey<EntityType<?>> createForge(String name) {
        return Tags.makeForgeKey(Registries.ENTITY_TYPE, name);
    }
}