package net.kapitencraft.mysticcraft.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.entity.Entity;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ManaMainProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player);
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new MysticcraftModVariables.PlayerVariables())).Mana > (entity
						.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new MysticcraftModVariables.PlayerVariables())).MaxMana) {
			{
				double _setval = (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new MysticcraftModVariables.PlayerVariables())).MaxMana;
				entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.Mana = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
		if ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new MysticcraftModVariables.PlayerVariables())).Mana < 0) {
			{
				double _setval = 0;
				entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.Mana = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
		if ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new MysticcraftModVariables.PlayerVariables())).Mana < (entity
						.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new MysticcraftModVariables.PlayerVariables())).MaxMana) {
			{
				double _setval = (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
						.orElse(new MysticcraftModVariables.PlayerVariables())).Mana
						+ (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new MysticcraftModVariables.PlayerVariables())).MaxMana / 500;
				entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.Mana = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
		{
			double _setval = 100 + (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new MysticcraftModVariables.PlayerVariables())).Intelligence;
			entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.MaxMana = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
	}
}
