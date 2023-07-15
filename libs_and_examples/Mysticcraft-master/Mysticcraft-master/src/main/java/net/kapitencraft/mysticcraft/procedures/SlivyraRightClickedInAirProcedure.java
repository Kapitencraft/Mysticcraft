package net.kapitencraft.mysticcraft.procedures;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.TextComponent;

import net.kapitencraft.mysticcraft.init.MysticcraftModEntities;
import net.kapitencraft.mysticcraft.entity.LightningStaffEntity;

public class SlivyraRightClickedInAirProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		String spelltorun = "";
		if (entity.getPersistentData().getDouble("spellslot") == 1) {
			spelltorun = entity.getPersistentData().getString("slivyraslot1");
		} else if (entity.getPersistentData().getDouble("spellslot") == 2) {
			spelltorun = entity.getPersistentData().getString("slivyraslot2");
		} else if (entity.getPersistentData().getDouble("spellslot") == 3) {
			spelltorun = entity.getPersistentData().getString("slivyraslot3");
		} else {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(new TextComponent("You have not used a spell since yet!"), (false));
		}
		if (!(spelltorun).equals("")) {
			if ((spelltorun).equals("lightningrush")) {
				{
					Entity _shootFrom = entity;
					Level projectileLevel = _shootFrom.level;
					if (!projectileLevel.isClientSide()) {
						Projectile _entityToSpawn = new Object() {
							public Projectile getArrow(Level level, Entity shooter, float damage, int knockback) {
								AbstractArrow entityToSpawn = new LightningStaffEntity(MysticcraftModEntities.LIGHTNING_STAFF.get(), level);
								entityToSpawn.setOwner(shooter);
								entityToSpawn.setBaseDamage(damage);
								entityToSpawn.setKnockback(knockback);
								entityToSpawn.setSilent(true);
								return entityToSpawn;
							}
						}.getArrow(projectileLevel, entity, 5, 5);
						_entityToSpawn.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
						_entityToSpawn.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 0);
						projectileLevel.addFreshEntity(_entityToSpawn);
					}
				}
			} else if ((spelltorun).equals("beammeup")) {
				{
					Entity _ent = entity;
					_ent.teleportTo(x, (world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) x, (int) z)), z);
					if (_ent instanceof ServerPlayer _serverPlayer)
						_serverPlayer.connection.teleport(x, (world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) x, (int) z)), z,
								_ent.getYRot(), _ent.getXRot());
				}
			} else if ((spelltorun).equals("shadowwarp")) {
				ShadowWarpRightClickedInAirProcedure.execute(world, x, y, z, entity);
			}
		}
	}
}
