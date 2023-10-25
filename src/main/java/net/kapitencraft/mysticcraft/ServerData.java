package net.kapitencraft.mysticcraft;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ServerData {
    private static int time = 0;

    @SubscribeEvent
    public static void ClientTickEvent(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.END) time++;
    }

    public static int getTime() {
        return time;
    }
}
