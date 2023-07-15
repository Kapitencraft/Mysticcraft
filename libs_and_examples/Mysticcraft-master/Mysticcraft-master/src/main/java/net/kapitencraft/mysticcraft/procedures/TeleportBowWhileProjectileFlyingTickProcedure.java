package net.kapitencraft.mysticcraft.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;

public class TeleportBowWhileProjectileFlyingTickProcedure {
	public static void execute(Entity entity, Entity immediatesourceentity) {
		if (entity == null || immediatesourceentity == null)
			return;
		if (entity.getPersistentData().getDouble("offset") == 0) {
			{
				Entity _ent = entity;
				_ent.teleportTo((entity.getX()), (-100), (entity.getZ()));
				if (_ent instanceof ServerPlayer _serverPlayer)
					_serverPlayer.connection.teleport((entity.getX()), (-100), (entity.getZ()), _ent.getYRot(), _ent.getXRot());
			}
			entity.getPersistentData().putDouble("offset", ((-100) - immediatesourceentity.getY()));
		} else {
			{
				Entity _ent = immediatesourceentity;
				_ent.teleportTo((entity.getX()), (entity.getY() + entity.getPersistentData().getDouble("offset")), (entity.getZ()));
				if (_ent instanceof ServerPlayer _serverPlayer)
					_serverPlayer.connection.teleport((entity.getX()), (entity.getY() + entity.getPersistentData().getDouble("offset")),
							(entity.getZ()), _ent.getYRot(), _ent.getXRot());
			}
			entity.clearFire();
		}
	}
}
