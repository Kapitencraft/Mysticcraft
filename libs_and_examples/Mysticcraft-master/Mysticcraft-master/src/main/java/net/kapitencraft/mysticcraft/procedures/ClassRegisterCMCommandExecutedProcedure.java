package net.kapitencraft.mysticcraft.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.TextComponent;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;

import java.util.HashMap;

public class ClassRegisterCMCommandExecutedProcedure {
	public static void execute(Entity entity, HashMap cmdparams) {
		if (entity == null || cmdparams == null)
			return;
		if ((entity.getPersistentData().getString("class")).equals("")) {
			if ((cmdparams.containsKey("0") ? cmdparams.get("0").toString() : "").equals("warrior")) {
				entity.getPersistentData().putString("class", "warrior");
				{
					double _setval = 10;
					entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.BaseStrength = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				if (entity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(new TextComponent("You`re Class is now: warrior"), (false));
			} else if ((cmdparams.containsKey("0") ? cmdparams.get("0").toString() : "").equals("shaman")) {
				entity.getPersistentData().putString("class", "shaman");
				if (entity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(new TextComponent("You`re Class is now: shaman"), (false));
			} else if ((cmdparams.containsKey("0") ? cmdparams.get("0").toString() : "").equals("mage")) {
				entity.getPersistentData().putString("class", "mage");
				{
					double _setval = 50;
					entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.BaseInt = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
				if (entity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(new TextComponent("You`re Class is now: mage"), (false));
			} else {
				if (entity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(new TextComponent("\uFFFD4Wrong Imput: Imput must "), (false));
				if (entity instanceof Player _player && !_player.level.isClientSide())
					_player.displayClientMessage(new TextComponent("\uFFFD4be: warior, shaman or mage."), (false));
			}
		} else {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(new TextComponent("\uFFFD4You can not change you class after"), (false));
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(new TextComponent("\uFFFD4you had already selected it."), (false));
		}
	}
}
