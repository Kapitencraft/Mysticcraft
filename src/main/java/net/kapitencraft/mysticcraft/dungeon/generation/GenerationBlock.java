package net.kapitencraft.mysticcraft.dungeon.generation;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class GenerationBlock extends Block {
    public GenerationBlock() {
        super(Properties.copy(Blocks.STRUCTURE_BLOCK));
    }
}
