package net.kapitencraft.mysticcraft.config;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.client.rainbow.ChromaOrigin;
import net.kapitencraft.mysticcraft.client.rainbow.ChromaType;
import net.minecraft.ChatFormatting;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();


    static {
        PING_COLOR = BUILDER
                .comment("determines the color which indicates pings")
                .defineEnum("ping_color", ChatFormatting.YELLOW);

        BUILDER.comment("data to determine how chroma text should be rendered [WIP]").push("chroma");
        CHROMA_SPEED = BUILDER
                .comment("the speed of chroma")
                .defineInRange("speed", 4., 1., 50.);
        CHROMA_TYPE = BUILDER.comment("the type of chroma ")
                .defineEnum("type", ChromaType.LINEAR);
        CHROMA_SPACING = BUILDER.comment("how wide each color should be rendered\nwith large values = less spread")
                .defineInRange("spacing", 10., 0.5, 50.);
        CHROMA_ORIGIN = BUILDER.comment("where the origin of the chroma (eg it's rotation and animation direction) should be")
                .defineEnum("origin", ChromaOrigin.BOTTOM_RIGHT);
    }

    private static final ForgeConfigSpec.DoubleValue CHROMA_SPEED;
    private static final ForgeConfigSpec.EnumValue<ChromaType> CHROMA_TYPE;
    private static final ForgeConfigSpec.DoubleValue CHROMA_SPACING;
    private static final ForgeConfigSpec.EnumValue<ChromaOrigin> CHROMA_ORIGIN;
    private static final ForgeConfigSpec.EnumValue<ChatFormatting> PING_COLOR;

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static ChatFormatting getPingColor() {
        return PING_COLOR.get();
    }

    public static ChromaType getChromaType() {
        return CHROMA_TYPE.get();
    }

    public static float getChromaSpeed() {
        return (float) (double) CHROMA_SPEED.get();
    }

    public static float getChromaSpacing() {
        return (float) (double) CHROMA_SPACING.get();
    }

    public static ChromaOrigin getChromaOrigin() {
        return CHROMA_ORIGIN.get();
    }
}