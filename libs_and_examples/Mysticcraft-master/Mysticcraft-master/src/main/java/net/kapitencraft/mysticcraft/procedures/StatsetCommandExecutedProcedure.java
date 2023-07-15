package net.kapitencraft.mysticcraft.procedures;

import org.checkerframework.checker.units.qual.s;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.TextComponent;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;

import java.util.HashMap;

public class StatsetCommandExecutedProcedure {
	public static void execute(Entity entity, HashMap cmdparams) {
		if (entity == null || cmdparams == null)
			return;
		if ((cmdparams.containsKey("0") ? cmdparams.get("0").toString() : "").equals("strenght")) {
			{
				double _setval = new Object() {
					double convert(String s) {
						try {
							return Double.parseDouble(s.trim());
						} catch (Exception e) {
						}
						return 0;
					}
				}.convert(cmdparams.containsKey("1") ? cmdparams.get("1").toString() : "");
				entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.BaseStrength = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		} else if ((cmdparams.containsKey("0") ? cmdparams.get("0").toString() : "").equals("intelligence")) {
			{
				double _setval = new Object() {
					double convert(String s) {
						try {
							return Double.parseDouble(s.trim());
						} catch (Exception e) {
						}
						return 0;
					}
				}.convert(cmdparams.containsKey("1") ? cmdparams.get("1").toString() : "");
				entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.BaseInt = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		} else if ((cmdparams.containsKey("0") ? cmdparams.get("0").toString() : "").equals("crit_damage")) {
			{
				double _setval = new Object() {
					double convert(String s) {
						try {
							return Double.parseDouble(s.trim());
						} catch (Exception e) {
						}
						return 0;
					}
				}.convert(cmdparams.containsKey("1") ? cmdparams.get("1").toString() : "");
				entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.CritDamage = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		} else if ((cmdparams.containsKey("0") ? cmdparams.get("0").toString() : "").equals("magic_find")) {
			{
				double _setval = new Object() {
					double convert(String s) {
						try {
							return Double.parseDouble(s.trim());
						} catch (Exception e) {
						}
						return 0;
					}
				}.convert(cmdparams.containsKey("1") ? cmdparams.get("1").toString() : "");
				entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.MagicFind = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
		if (entity instanceof Player _player && !_player.level.isClientSide())
			_player.displayClientMessage(
					new TextComponent(("Set " + (cmdparams.containsKey("0") ? cmdparams.get("0").toString() : "") + " to " + new Object() {
						double convert(String s) {
							try {
								return Double.parseDouble(s.trim());
							} catch (Exception e) {
							}
							return 0;
						}
					}.convert(cmdparams.containsKey("1") ? cmdparams.get("1").toString() : ""))), (false));
	}
}
