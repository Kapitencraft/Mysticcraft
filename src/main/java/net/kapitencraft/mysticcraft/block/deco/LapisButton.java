package net.kapitencraft.mysticcraft.block.deco;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;

public class LapisButton extends ButtonBlock {
    public LapisButton() {
        super(Properties.copy(Blocks.LAPIS_BLOCK), 2, false, SoundEvents.STONE_BUTTON_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF);
    }
}
