package net.kapitencraft.mysticcraft.event.handler;

import net.kapitencraft.kap_lib.event.custom.RegisterBonusProvidersEvent;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.capability.reforging.Reforges;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Events {

    @SubscribeEvent
    public void onRegisterBonusProviders(RegisterBonusProvidersEvent event) {
        event.register(MysticcraftMod.res("reforge"), Reforges::getReforge);
    }

}
