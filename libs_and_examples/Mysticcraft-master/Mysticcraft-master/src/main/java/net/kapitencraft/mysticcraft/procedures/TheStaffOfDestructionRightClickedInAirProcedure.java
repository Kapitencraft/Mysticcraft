package net.kapitencraft.mysticcraft.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;

public class TheStaffOfDestructionRightClickedInAirProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		PositionFromRotationProcedure.execute(10, entity);
		if (world instanceof ServerLevel _level) {
			Entity entityToSpawn = new WitherSkull(EntityType.WITHER_SKULL, _level);
			entityToSpawn.moveTo(x, y, z, entity.getYRot(), entity.getXRot());
			entityToSpawn.setYBodyRot(entity.getYRot());
			entityToSpawn.setYHeadRot(entity.getYRot());
			entityToSpawn.setDeltaMovement(
					(entity.getX() - (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new MysticcraftModVariables.PlayerVariables())).Mana),
					(entity.getY() - (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new MysticcraftModVariables.PlayerVariables())).Mana),
					(entity.getZ() - (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new MysticcraftModVariables.PlayerVariables())).Mana));
			if (entityToSpawn instanceof Mob _mobToSpawn)
				_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
			world.addFreshEntity(entityToSpawn);
		}
	}
}
