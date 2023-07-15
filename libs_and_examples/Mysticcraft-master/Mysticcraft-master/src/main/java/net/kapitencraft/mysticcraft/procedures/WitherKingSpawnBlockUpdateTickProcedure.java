package net.kapitencraft.mysticcraft.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.kapitencraft.mysticcraft.init.MysticcraftModEntities;
import net.kapitencraft.mysticcraft.entity.WitherKingEntity;

import java.util.stream.Collectors;
import java.util.Random;
import java.util.List;
import java.util.Comparator;

public class WitherKingSpawnBlockUpdateTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if ((world instanceof Level _lvl_getRedPow ? _lvl_getRedPow.getSignal(new BlockPos(x, y, z), Direction.getRandom(new Random())) : 0) > 0
				&& WitherKingSpawnBlockParticleSpawningConditionProcedure.execute(world, x, y, z)) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(25 / 2d), e -> true).stream()
						.sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if (entityiterator instanceof Player) {
						if (entityiterator instanceof Player _player && !_player.level.isClientSide())
							_player.displayClientMessage(new TextComponent("YOU ARE GOING TO DIE!"), (false));
					}
				}
			}
			if (world instanceof ServerLevel _level) {
				Entity entityToSpawn = new WitherKingEntity(MysticcraftModEntities.WITHER_KING.get(), _level);
				entityToSpawn.moveTo((x + 0.5), (y + 0.5), (z + 0.5), 0, 0);
				entityToSpawn.setYBodyRot(0);
				entityToSpawn.setYHeadRot(0);
				if (entityToSpawn instanceof Mob _mobToSpawn)
					_mobToSpawn.finalizeSpawn(_level, world.getCurrentDifficultyAt(entityToSpawn.blockPosition()), MobSpawnType.MOB_SUMMONED, null,
							null);
				world.addFreshEntity(entityToSpawn);
			}
			world.setBlock(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState(), 3);
		}
	}
}
