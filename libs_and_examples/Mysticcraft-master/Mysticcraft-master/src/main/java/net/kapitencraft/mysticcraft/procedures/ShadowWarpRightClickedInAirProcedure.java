package net.kapitencraft.mysticcraft.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;

public class ShadowWarpRightClickedInAirProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new MysticcraftModVariables.PlayerVariables())).Mana >= 300) {
			{
				double _setval = (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new MysticcraftModVariables.PlayerVariables())).Mana - 300;
				entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.Mana = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if (world instanceof ServerLevel _level)
				_level.getServer().getCommands().performCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4,
						"", new TextComponent(""), _level.getServer(), null).withSuppressedOutput(), "execute as @p at @s run teleport @s ^ ^ ^10");
			entity.fallDistance = 0;
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.ASH, (entity.getX()), (entity.getY()), (entity.getZ()), 50, 4, 4, 3, 0.2);
			{
				final Vec3 _center = new Vec3((entity.getX()), (entity.getY()), (entity.getZ()));
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream()
						.sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
				for (Entity entityiterator : _entfound) {
					if (!(entityiterator == entity)) {
						entityiterator.hurt(DamageSource.FLY_INTO_WALL, 15);
						entityiterator.setDeltaMovement(new Vec3((7 - (entityiterator.getX() - entity.getX())),
								(7 - (entityiterator.getY() - entity.getY())), (7 - (entityiterator.getZ() - entity.getZ()))));
					}
				}
			}
		}
	}
}
