
package net.kapitencraft.mysticcraft.client.gui;

import org.checkerframework.checker.units.qual.h;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.Minecraft;

import net.kapitencraft.mysticcraft.network.MysticcraftModVariables;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class DebugOverlay {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGameOverlayEvent.Pre event) {
		if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
			int w = event.getWindow().getGuiScaledWidth();
			int h = event.getWindow().getGuiScaledHeight();
			int posX = w / 2;
			int posY = h / 2;
			Level _world = null;
			double _x = 0;
			double _y = 0;
			double _z = 0;
			Player entity = Minecraft.getInstance().player;
			if (entity != null) {
				_world = entity.level;
				_x = entity.getX();
				_y = entity.getY();
				_z = entity.getZ();
			}
			Level world = _world;
			double x = _x;
			double y = _y;
			double z = _z;
			if (true) {
				Minecraft.getInstance().font.draw(event.getMatrixStack(),
						"" + (int) ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new MysticcraftModVariables.PlayerVariables())).MaxMana) + "",
						posX + -212, posY + 104, -16724788);
				Minecraft.getInstance().font.draw(event.getMatrixStack(),
						"" + (int) ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new MysticcraftModVariables.PlayerVariables())).Mana) + "",
						posX + -211, posY + 89, -16751002);
				Minecraft.getInstance().font.draw(event.getMatrixStack(),
						"" + (int) ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new MysticcraftModVariables.PlayerVariables())).Intelligence) + "",
						posX + 78, posY + -120, -3407668);
				Minecraft.getInstance().font.draw(event.getMatrixStack(),
						"" + ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new MysticcraftModVariables.PlayerVariables())).xvec) + "",
						posX + 160, posY + 50, -65536);
				Minecraft.getInstance().font.draw(event.getMatrixStack(),
						"" + ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new MysticcraftModVariables.PlayerVariables())).yvec) + "",
						posX + 160, posY + 60, -65536);
				Minecraft.getInstance().font.draw(event.getMatrixStack(),
						"" + ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new MysticcraftModVariables.PlayerVariables())).zvec) + "",
						posX + 160, posY + 70, -65536);
				Minecraft.getInstance().font.draw(event.getMatrixStack(),
						"" + ((entity.getCapability(MysticcraftModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new MysticcraftModVariables.PlayerVariables())).manaused) + "",
						posX + -195, posY + 71, -1);
			}
		}
	}
}
