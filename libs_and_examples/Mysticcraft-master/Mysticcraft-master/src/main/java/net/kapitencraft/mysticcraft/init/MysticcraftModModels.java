
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.kapitencraft.mysticcraft.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.kapitencraft.mysticcraft.client.model.Modelvillager_v2;
import net.kapitencraft.mysticcraft.client.model.Modelsteve;
import net.kapitencraft.mysticcraft.client.model.Modelenderdragon;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class MysticcraftModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(Modelsteve.LAYER_LOCATION, Modelsteve::createBodyLayer);
		event.registerLayerDefinition(Modelvillager_v2.LAYER_LOCATION, Modelvillager_v2::createBodyLayer);
		event.registerLayerDefinition(Modelenderdragon.LAYER_LOCATION, Modelenderdragon::createBodyLayer);
	}
}
