package net.kapitencraft.mysticcraft.dungeon.generation;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class DungeonGenerator extends Block {
    public DungeonGenerator() {
        super(Properties.copy(Blocks.STRUCTURE_BLOCK));
    }
}
