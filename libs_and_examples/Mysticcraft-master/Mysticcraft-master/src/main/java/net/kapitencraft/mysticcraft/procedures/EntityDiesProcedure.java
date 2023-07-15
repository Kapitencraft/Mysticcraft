package net.kapitencraft.mysticcraft.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.TextComponent;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;
import net.kapitencraft.mysticcraft.init.MysticcraftModItems;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class EntityDiesProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(),
					event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		execute(null, world, x, y, z, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		if (entity instanceof EnderMan) {
			if (Math.random() < 0.01 * ((sourceentity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new MysticcraftModVariables.PlayerVariables())).MagicFind / 100)) {
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(MysticcraftModItems.AOTE.get()));
					entityToSpawn.setPickUpDelay(0);
					_level.addFreshEntity(entityToSpawn);
				}
				if (sourceentity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(new TextComponent(("\u00A7bRare Drop! \u00A73Aspect of the End (+"
							+ (sourceentity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new MysticcraftModVariables.PlayerVariables())).MagicFind
							+ " Magic Find)")), (false));
			}
			if (Math.random() < (1e-8) * ((sourceentity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new MysticcraftModVariables.PlayerVariables())).MagicFind / 100)) {
				if (world instanceof Level _level && !_level.isClientSide()) {
					ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(MysticcraftModItems.PLASMA_CORE.get()));
					entityToSpawn.setPickUpDelay(0);
					_level.addFreshEntity(entityToSpawn);
				}
				if (sourceentity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(new TextComponent(("\u00A7bInsanly Crazy Rare Drop! \u00A73Plasma Core (+"
							+ (sourceentity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new MysticcraftModVariables.PlayerVariables())).MagicFind
							+ " Magic Find)")), (false));
			}
		}
	}
}
