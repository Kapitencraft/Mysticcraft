package net.kapitencraft.mysticcraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ModEntityTags {
    public static final TagKey<EntityType<?>> OG_NETHER_MOBS = create("forge:og_nether_mobs");
    public static final TagKey<EntityType<?>> NETHER_MOBS = create("forge:nether_mobs");


    private static TagKey<EntityType<?>> create(String name) {
        return Tags.makeKey(Registries.ENTITY_TYPE, name);
    }
}