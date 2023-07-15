package net.kapitencraft.mysticcraft;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.kapitencraft.mysticcraft.ModItemProperties;
import net.kapitencraft.mysticcraft.procedures.StrenghtPDProcedure;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegister {
	public ModRegister() {
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		ModItemProperties.addCustomItemProperties();
	}
}
