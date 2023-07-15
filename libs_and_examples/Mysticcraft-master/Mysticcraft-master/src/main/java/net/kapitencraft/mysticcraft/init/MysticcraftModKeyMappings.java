
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.kapitencraft.mysticcraft.init;

import org.lwjgl.glfw.GLFW;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;

import net.kapitencraft.mysticcraft.network.AbilityArmorUseKeyMessage;
import net.kapitencraft.mysticcraft.MysticcraftMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class MysticcraftModKeyMappings {
	public static final KeyMapping ABILITY_ARMOR_USE_KEY = new KeyMapping("key.mysticcraft.ability_armor_use_key", GLFW.GLFW_KEY_R,
			"key.categories.gameplay");

	@SubscribeEvent
	public static void registerKeyBindings(FMLClientSetupEvent event) {
		ClientRegistry.registerKeyBinding(ABILITY_ARMOR_USE_KEY);
	}

	@Mod.EventBusSubscriber({Dist.CLIENT})
	public static class KeyEventListener {
		@SubscribeEvent
		public static void onKeyInput(InputEvent.KeyInputEvent event) {
			if (Minecraft.getInstance().screen == null) {
				if (event.getKey() == ABILITY_ARMOR_USE_KEY.getKey().getValue()) {
					if (event.getAction() == GLFW.GLFW_PRESS) {
						MysticcraftMod.PACKET_HANDLER.sendToServer(new AbilityArmorUseKeyMessage(0, 0));
						AbilityArmorUseKeyMessage.pressAction(Minecraft.getInstance().player, 0, 0);
					}
				}
			}
		}
	}
}
