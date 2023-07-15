package net.kapitencraft.mysticcraft.procedures;

import net.minecraft.world.entity.Entity;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;

public class AmethistArmorHelmetTickEventProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			double _setval = (entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new MysticcraftModVariables.PlayerVariables())).Intelligence + 25;
			entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.Intelligence = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
	}
}
