package net.kapitencraft.mysticcraft.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.entity.Entity;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class DebugPDProcedure {
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
		{
			double _setval = entity.getDeltaMovement().x();
			entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.xvec = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
		{
			double _setval = entity.getDeltaMovement().y();
			entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.yvec = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
		{
			double _setval = entity.getDeltaMovement().z();
			entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.zvec = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
	}
}
