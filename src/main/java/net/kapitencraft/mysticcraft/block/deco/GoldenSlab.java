package net.kapitencraft.mysticcraft.block.deco;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;

public class GoldenSlab extends SlabBlock {
    public static final MapCodec<GoldenSlab> CODEC = MapCodec.unit(GoldenSlab::new);

    public GoldenSlab() {
        super(Properties.ofFullCopy(Blocks.GOLD_BLOCK));
    }
}
