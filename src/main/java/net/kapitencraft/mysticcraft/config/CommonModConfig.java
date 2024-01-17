package net.kapitencraft.mysticcraft.config;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue RESET_BUILDERS_WAND_POS = BUILDER
            .comment("determines if the saved positions should be reset after using the builder's wand")
            .define("reset_builders_wand", true);

    private static final ForgeConfigSpec.BooleanValue USE_ON_POS_COMPLETE = BUILDER
            .comment("determines if the builder's wand should be used as soon as all positions have been set")
            .define("use_on_complete", false);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean resetBuildersWand = true;
    public static boolean useOnComplete = false;

    @SubscribeEvent
    public static void registerConfig(final ModConfigEvent event) {
        if (SPEC.isLoaded()) {
            MysticcraftMod.LOGGER.info("loading common config...");
            resetBuildersWand = RESET_BUILDERS_WAND_POS.get();
            useOnComplete = USE_ON_POS_COMPLETE.get();
        }
    }
}
