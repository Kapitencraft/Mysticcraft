
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.kapitencraft.mysticcraft.init;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.gui.screens.MenuScreens;

import net.kapitencraft.mysticcraft.client.gui.ReforgeTableGUIScreen;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MysticcraftModScreens {
	@SubscribeEvent
	public static void clientLoad(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(MysticcraftModMenus.REFORGE_TABLE_GUI, ReforgeTableGUIScreen::new);
		});
	}
}
