package net.kapitencraft.mysticcraft.misc;


import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class MiscEvents {
    @Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key ignoredEvent) {
        }
    }

    @Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.NEXT_SPELL_KEY);
            event.register(KeyBinding.PREVIOUS_SPELL_KEY);
        }
    }

}
