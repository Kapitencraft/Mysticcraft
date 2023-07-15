
package net.kapitencraft.mysticcraft.block;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;

public class VoidGloomBlockBlock extends Block {
	public VoidGloomBlockBlock() {
		super(BlockBehaviour.Properties.of(Material.EGG).sound(SoundType.FUNGUS).strength(10f, 200f).lightLevel(s -> 10).requiresCorrectToolForDrops()
				.friction(1f).speedFactor(7f).jumpFactor(5f));
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 5;
	}

	@Override
	public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
		if (player.getInventory().getSelected().getItem() instanceof TieredItem tieredItem)
			return tieredItem.getTier().getLevel() >= 4;
		return false;
	}
}
