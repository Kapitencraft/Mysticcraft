package net.kapitencraft.mysticcraft.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import net.kapitencraft.mysticcraft.init.MysticcraftModEntities;
import net.kapitencraft.mysticcraft.entity.WitherSoldierEntity;
import net.kapitencraft.mysticcraft.entity.WitherLordEntity;

public class WitherKingEntityIsHurtProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < 768) {
			if (Math.random() >= 0.9) {
				for (int index0 = 0; index0 < (int) (10); index0++) {
					if (world instanceof ServerLevel _level) {
						Entity entityToSpawn = new WitherLordEntity(MysticcraftModEntities.WITHER_LORD.get(), _level);
						entityToSpawn.moveTo(x, y, z, 0, 0);
						entityToSpawn.setYBodyRot(0);
						entityToSpawn.setYHeadRot(0);
						if (entityToSpawn instanceof Mob _mobToSpawn)
							_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED,
									null, null);
						world.addFreshEntity(entityToSpawn);
					}
					for (int index1 = 0; index1 < (int) (Math.random() * 10); index1++) {
						if (world instanceof ServerLevel _level) {
							Entity entityToSpawn = new WitherSoldierEntity(MysticcraftModEntities.WITHER_SOLDIER.get(), _level);
							entityToSpawn.moveTo(x, y, z, 0, 0);
							entityToSpawn.setYBodyRot(0);
							entityToSpawn.setYHeadRot(0);
							if (entityToSpawn instanceof Mob _mobToSpawn)
								_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()),
										MobSpawnType.MOB_SUMMONED, null, null);
							world.addFreshEntity(entityToSpawn);
						}
					}
				}
			}
		}
	}
}
