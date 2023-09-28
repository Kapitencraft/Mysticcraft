package net.kapitencraft.mysticcraft.block.deco;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;

public class SimpleStairBlock extends StairBlock {
    public SimpleStairBlock(Block block) {
        super(block.defaultBlockState(), Properties.copy(block));
    }
}
