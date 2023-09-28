package net.kapitencraft.mysticcraft.block.deco;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;

public class GoldenWall extends WallBlock {
    public GoldenWall() {
        super(Properties.copy(Blocks.GOLD_BLOCK));
    }
}
