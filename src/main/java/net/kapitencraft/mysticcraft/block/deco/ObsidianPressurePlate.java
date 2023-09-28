package net.kapitencraft.mysticcraft.block.deco;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PressurePlateBlock;

public class ObsidianPressurePlate extends PressurePlateBlock {
    public ObsidianPressurePlate() {
        super(Sensitivity.EVERYTHING, Properties.copy(Blocks.OBSIDIAN).noCollission(), SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON);
    }
}
