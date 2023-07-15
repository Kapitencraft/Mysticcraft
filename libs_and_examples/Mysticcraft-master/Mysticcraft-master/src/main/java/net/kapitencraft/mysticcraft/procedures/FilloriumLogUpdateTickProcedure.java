package net.kapitencraft.mysticcraft.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;

public class FilloriumLogUpdateTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream()
					.sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
			for (Entity entityiterator : _entfound) {
				if (entityiterator instanceof Player) {
					if ((entityiterator.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new MysticcraftModVariables.PlayerVariables())).Mana < (entityiterator
									.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new MysticcraftModVariables.PlayerVariables())).MaxMana) {
						{
							double _setval = (entityiterator.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new MysticcraftModVariables.PlayerVariables())).Mana + 2;
							entityiterator.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
								capability.Mana = _setval;
								capability.syncPlayerVariables(entityiterator);
							});
						}
					}
				}
			}
		}
	}
}
