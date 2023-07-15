package net.kapitencraft.mysticcraft.procedures;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;

public class AmethistPickaxeBreakBlockProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new MysticcraftModVariables.PlayerVariables())).Mana >= 45) {
			{
				BlockPos _pos = new BlockPos(x + 1, y, z + 1);
				Block.dropResources(world.getBlockState(_pos), world, new BlockPos(x, y, z), null);
				world.destroyBlock(_pos, false);
			}
			{
				BlockPos _pos = new BlockPos(x + 1, y, z);
				Block.dropResources(world.getBlockState(_pos), world, new BlockPos(x, y, z), null);
				world.destroyBlock(_pos, false);
			}
			{
				BlockPos _pos = new BlockPos(x + 1, y, z - 1);
				Block.dropResources(world.getBlockState(_pos), world, new BlockPos(x, y, z), null);
				world.destroyBlock(_pos, false);
			}
			{
				BlockPos _pos = new BlockPos(x, y, z + 1);
				Block.dropResources(world.getBlockState(_pos), world, new BlockPos(x, y, z), null);
				world.destroyBlock(_pos, false);
			}
			{
				BlockPos _pos = new BlockPos(x, y, z - 1);
				Block.dropResources(world.getBlockState(_pos), world, new BlockPos(x, y, z), null);
				world.destroyBlock(_pos, false);
			}
			{
				BlockPos _pos = new BlockPos(x - 1, y, z + 1);
				Block.dropResources(world.getBlockState(_pos), world, new BlockPos(x, y, z), null);
				world.destroyBlock(_pos, false);
			}
			{
				BlockPos _pos = new BlockPos(x - 1, y, z);
				Block.dropResources(world.getBlockState(_pos), world, new BlockPos(x, y, z), null);
				world.destroyBlock(_pos, false);
			}
			{
				BlockPos _pos = new BlockPos(x - 1, y, z - 1);
				Block.dropResources(world.getBlockState(_pos), world, new BlockPos(x, y, z), null);
				world.destroyBlock(_pos, false);
			}
		}
	}
}
