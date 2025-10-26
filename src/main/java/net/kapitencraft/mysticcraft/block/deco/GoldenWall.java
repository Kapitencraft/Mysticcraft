package net.kapitencraft.mysticcraft.block.deco;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;

public class GoldenWall extends WallBlock {
    public static final MapCodec<GoldenWall> CODEC = MapCodec.unit(GoldenWall::new);

    public GoldenWall() {
        super(Properties.ofFullCopy(Blocks.GOLD_BLOCK));
    }
}
