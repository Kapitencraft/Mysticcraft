package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.entity.armor.client.WizardHatRenderer;
import net.kapitencraft.mysticcraft.item.WizardHatItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = MysticcraftMod.MOD_ID)
public class ModEventBus {
    @SubscribeEvent
    public static void registerArmorRenderer(final EntityRenderersEvent.AddLayers ignoredEvent) {
        GeoArmorRenderer.registerArmorRenderer(WizardHatItem.class, WizardHatRenderer::new);
    }
}
