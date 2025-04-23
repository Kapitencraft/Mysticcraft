package net.kapitencraft.mysticcraft.block.deco;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class ObsidianPressurePlate extends PressurePlateBlock {
    public ObsidianPressurePlate() {
        super(Sensitivity.EVERYTHING, Properties.copy(Blocks.OBSIDIAN).noCollission(), BlockSetType.STONE);
    }
}
