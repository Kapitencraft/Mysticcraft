package net.kapitencraft.mysticcraft.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ManaSAMLauncherBlock extends Block {
    public ManaSAMLauncherBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.ANVIL));
    }
}
