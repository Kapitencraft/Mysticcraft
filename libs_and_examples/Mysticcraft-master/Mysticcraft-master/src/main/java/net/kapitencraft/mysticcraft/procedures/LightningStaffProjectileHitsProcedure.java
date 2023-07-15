package net.kapitencraft.mysticcraft.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

public class LightningStaffProjectileHitsProcedure {
	public static void execute(LevelAccessor world, double x, double z) {
		double xLoc = 0;
		double zLoc = 0;
		double rand = 0;
		for (int index0 = 0; index0 < (int) (Math.random() * 10); index0++) {
			rand = Math.random();
			if (rand > 0.75 && rand <= 1) {
				xLoc = x + Math.random() * 6;
				zLoc = z + Math.random() * 6;
			} else if (rand > 0.5 && rand <= 0.75) {
				xLoc = x + Math.random() * 6;
				zLoc = z - Math.random() * 6;
			} else if (rand > 0.25 && rand <= 0.5) {
				xLoc = x - Math.random() * 6;
				zLoc = z + Math.random() * 6;
			} else {
				xLoc = x - Math.random() * 6;
				zLoc = z - Math.random() * 6;
			}
			if (world instanceof ServerLevel _level) {
				LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
				entityToSpawn.moveTo(Vec3
						.atBottomCenterOf(new BlockPos(xLoc, world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) x, (int) z), zLoc)));
				entityToSpawn.setVisualOnly(false);
				_level.addFreshEntity(entityToSpawn);
			}
		}
	}
}
