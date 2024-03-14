package net.kapitencraft.mysticcraft.config;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.ChatFormatting;
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
            .comment("determines if extra debug log should be shown")
            .define("extra_debug", false);
    private static final ForgeConfigSpec.IntValue RGB_SPEED = BUILDER
            .comment("the speed of rgb text")
            .defineInRange("rgb_speed", 1, 1, 10);
    private static final ForgeConfigSpec.EnumValue<ChatFormatting> PING_COLOR = BUILDER
            .comment("the color in which pings will be displayed")
            .defineEnum("ping_color", ChatFormatting.YELLOW);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int scrollScale = 1;
    public static int rgbSpeed = 1;
    public static boolean extraDebug = false;
    public static ChatFormatting pingColor = ChatFormatting.YELLOW;

    @SubscribeEvent
    public static void registerConfig(final ModConfigEvent event) {
        if (SPEC.isLoaded()) {
            MysticcraftMod.LOGGER.info("loading client config...");
            scrollScale = SCROLL_SCALE.get();
            rgbSpeed = RGB_SPEED.get();
            extraDebug = SHOW_EXTRA_DEBUG.get();
            pingColor = PING_COLOR.get();
        }
    }
}
