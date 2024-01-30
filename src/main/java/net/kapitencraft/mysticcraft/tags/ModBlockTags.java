package net.kapitencraft.mysticcraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public interface ModBlockTags {
    TagKey<Block> FARMABLE = createModKey("farmable_blocks");
    TagKey<Block> FORAGEABLE = createModKey("forageable_blocks");
    TagKey<Block> MINEABLE = createModKey("mineable_blocks");

    static TagKey<Block> createModKey(String id) {
        return Tags.makeModKey(Registries.BLOCK, id);
    }
}
