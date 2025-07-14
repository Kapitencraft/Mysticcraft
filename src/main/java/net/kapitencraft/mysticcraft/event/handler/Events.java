package net.kapitencraft.mysticcraft.event.handler;

import net.kapitencraft.kap_lib.event.custom.RegisterBonusProvidersEvent;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.reforging.Reforges;
import net.kapitencraft.mysticcraft.rpg.perks.ServerPerksManager;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Events {

    @SubscribeEvent
    public static void onRegisterBonusProviders(RegisterBonusProvidersEvent event) {
        event.register(MysticcraftMod.res("reforge"), Reforges::getReforgeBonus);
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        ServerPerksManager.clearCache();
    }
}
