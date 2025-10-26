package net.kapitencraft.mysticcraft.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientModConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue SKILL_LEVEL_ROMAN = BUILDER
            .comment("whether to show skill level numbers in roman (true) or arabian (false) literals")
            .define("skill_level_roman", true);

    public static final ModConfigSpec SPEC = BUILDER.build();


    public static boolean showSkillLevelsAsRoman() {
        return SKILL_LEVEL_ROMAN.get();
    }
}