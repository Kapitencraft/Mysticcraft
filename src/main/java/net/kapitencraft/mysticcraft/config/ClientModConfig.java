package net.kapitencraft.mysticcraft.config;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue SCROLL_SCALE = BUILDER
                .comment("the scale of how quick tooltips are scrolled with")
            .defineInRange("scroll_scale", 5, 1, 20);
    private static final ForgeConfigSpec.BooleanValue SHOW_EXTRA_DEBUG = BUILDER
            .comment("determines if extra debug should be shown")
            .define("extra_debug", false);
    private static final ForgeConfigSpec.IntValue RGB_SPEED = BUILDER
            .comment("the speed of rgb text")
            .defineInRange("rgb_speed", 1, 1, 10);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int scrollScale = 1;
    public static int rgbSpeed = 1;
    public static boolean extraDebug = false;

    @SubscribeEvent
    public static void registerConfig(final ModConfigEvent event) {
        MysticcraftMod.sendInfo("loading client config...");
        scrollScale = SCROLL_SCALE.get();
        rgbSpeed = RGB_SPEED.get();
        extraDebug = SHOW_EXTRA_DEBUG.get();
    }
}
