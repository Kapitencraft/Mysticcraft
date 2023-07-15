package net.kapitencraft.mysticcraft.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.entity.player.Player;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;

import javax.annotation.Nullable;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraft.world.entity.Entity;

@Mod.EventBusSubscriber
public class CriticalHitDamageCalculationProcedure {
	@SubscribeEvent
	public static void onCriticalHit(CriticalHitEvent event) {
		Entity entity = event.getEntity();
		float critdamage = (float) entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new MysticcraftModVariables.PlayerVariables()).CritDamage;
		event.setDamageModifier(1.5f+critdamage/100);
	}
}
