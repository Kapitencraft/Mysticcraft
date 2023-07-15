
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.kapitencraft.mysticcraft.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;

import net.kapitencraft.mysticcraft.client.renderer.WitherSoldierRenderer;
import net.kapitencraft.mysticcraft.client.renderer.WitherLordRenderer;
import net.kapitencraft.mysticcraft.client.renderer.WitherKingRenderer;
import net.kapitencraft.mysticcraft.client.renderer.GoblinRenderer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MysticcraftModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(MysticcraftModEntities.HOMINGBOW.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(MysticcraftModEntities.GOBLIN.get(), GoblinRenderer::new);
		event.registerEntityRenderer(MysticcraftModEntities.LIGHTNING_STAFF.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(MysticcraftModEntities.EXPLOSIV_STAFF.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(MysticcraftModEntities.SHADOW_WARP_BOW.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(MysticcraftModEntities.WITHER_KING.get(), WitherKingRenderer::new);
		event.registerEntityRenderer(MysticcraftModEntities.WITHER_LORD.get(), WitherLordRenderer::new);
		event.registerEntityRenderer(MysticcraftModEntities.WITHER_SOLDIER.get(), WitherSoldierRenderer::new);
		event.registerEntityRenderer(MysticcraftModEntities.THE_STUFF_OF_DESTRUCTION.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(MysticcraftModEntities.TELEPORT_BOW.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(MysticcraftModEntities.LONG_BOW.get(), ThrownItemRenderer::new);
	}
}
