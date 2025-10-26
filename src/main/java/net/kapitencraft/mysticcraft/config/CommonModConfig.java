package net.kapitencraft.mysticcraft.config;


import net.neoforged.neoforge.common.ModConfigSpec;

public class CommonModConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    static {
        RESET_BUILDERS_WAND_POS = BUILDER
                .comment("determines if the saved positions should be reset after using the builder's wand")
                .define("reset_builders_wand", true);
        USE_ON_POS_COMPLETE = BUILDER
                .comment("determines if the builder's wand should be used as soon as all positions have been set")
                .define("use_on_complete", false);

        BUILDER.comment("gemstone world-gen config").push("gemstone");
        MIN_SEED_SPAWNS = BUILDER
                .comment("determines how many gemstone-seeds should be at least attempted to be placed per batch")
                .defineInRange("min_seed_spawns", 3, 1, 10);
        MAX_SEED_SPAWNS = BUILDER
                .comment("determines how many gemstone-seeds should be at max attempted to be placed per batch")
                .defineInRange("max_seed_spawns", 7, 5, 20);
        MIN_GEMSTONE_ITERATIONS = BUILDER
                .comment("determines how many gemstones are at least attempted to be spawned per seed")
                .defineInRange("min_gemstone_iteration", 50, 10, 200);
        MAX_GEMSTONE_ITERATIONS = BUILDER
                .comment("determines how many gemstones are at max attempted to be spawned per seed")
                .defineInRange("max_gemstone_iteration", 350, 150, 750);
    }

    private static final ModConfigSpec.BooleanValue RESET_BUILDERS_WAND_POS, USE_ON_POS_COMPLETE;
    private static final ModConfigSpec.IntValue MAX_GEMSTONE_ITERATIONS, MIN_GEMSTONE_ITERATIONS, MIN_SEED_SPAWNS, MAX_SEED_SPAWNS;

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean shouldResetBuilderWandPos() {
        return RESET_BUILDERS_WAND_POS.get();
    }

    public static boolean shouldUseOnComplete() {
        return USE_ON_POS_COMPLETE.get();
    }

    public static int getMinSeedSpawns() {
        return MIN_SEED_SPAWNS.get();
    }

    public static int getMaxSeedSpawns() {
        return MAX_SEED_SPAWNS.get();
    }

    public static int getMinGemstoneIterations() {
        return MIN_GEMSTONE_ITERATIONS.get();
    }

    public static int getMaxGemstoneIterations() {
        return MAX_GEMSTONE_ITERATIONS.get();
    }
}
