package net.kapitencraft.mysticcraft.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerModConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue ENABLE_SOCIAL_COMMANDS = BUILDER
            .comment("determines if social commands (/show etc.) should be available")
            .define("enable_social", true);
    private static final ForgeConfigSpec.IntValue MAX_ITERATION_BROKEN_BLOCKS = BUILDER
            .comment("determines how many blocks per tick should be broken by the multi-break enchantments")
            .defineInRange("iter_max_broken", 20, 1, 200);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean areSocialCommandsEnabled() {
        return ENABLE_SOCIAL_COMMANDS.get();
    }

    public static int getMaxBrokenBlocks() {
        return MAX_ITERATION_BROKEN_BLOCKS.get();
    }
}
