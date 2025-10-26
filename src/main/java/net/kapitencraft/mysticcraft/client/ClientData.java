package net.kapitencraft.mysticcraft.client;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientData {
    private static int time = 0;

    @SubscribeEvent
    public static void tickEvent(PlayerTickEvent.Post event) {
        if (event.getEntity() == Minecraft.getInstance().player) {
            time++;
        }
    }

    public static int getTime() {
        return time;
    }
}
