package net.kapitencraft.mysticcraft.block.deco;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class LapisButton extends ButtonBlock {
    public LapisButton() {//TODO add block set type?
        super(Properties.copy(Blocks.LAPIS_BLOCK), BlockSetType.STONE, 2, false);
    }
}
