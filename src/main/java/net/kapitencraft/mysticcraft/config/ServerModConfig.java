package net.kapitencraft.mysticcraft.config;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerModConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue ENABLE_SOCIAL_COMMANDS = BUILDER
            .comment("determines if social commands (/show etc.) should be available")
            .define("enable_social", true);

    private static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean enableSocial = true;

    @SubscribeEvent
    public static void registerConfig(final ModConfigEvent event) {
        if (SPEC.isLoaded()) {
            enableSocial = ENABLE_SOCIAL_COMMANDS.get();
        }
    }
}
