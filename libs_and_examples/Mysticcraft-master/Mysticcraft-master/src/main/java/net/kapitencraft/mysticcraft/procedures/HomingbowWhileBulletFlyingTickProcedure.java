package net.kapitencraft.mysticcraft.procedures;

import net.minecraftforge.server.ServerLifecycleHooks;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.ChatType;
import net.minecraft.Util;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;

public class HomingbowWhileBulletFlyingTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10 / 2d), e -> true).stream()
					.sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator == entity && entityiterator instanceof Arrow && entityiterator instanceof Player)) {
					if (!world.isClientSide()) {
						MinecraftServer _mcserv = ServerLifecycleHooks.getCurrentServer();
						if (_mcserv != null)
							_mcserv.getPlayerList().broadcastMessage(new TextComponent((entityiterator.getDisplayName().getString())),
									ChatType.SYSTEM, Util.NIL_UUID);
					}
					entity.setDeltaMovement(new Vec3((entityiterator.getX() - (entity.getX() + 5)), (entityiterator.getY() - (entity.getY() + 5)),
							(entityiterator.getZ() - (entity.getZ() + 5))));
				}
			}
		}
	}
}
