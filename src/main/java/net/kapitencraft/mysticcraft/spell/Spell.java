package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.world.item.Rarity;

public abstract class Spell {
    public enum Type {
        RELEASE("release"),
        CYCLE("cycle");
        private final String string;

        Type(String string) {
            this.string = string;
        }

        public String getString() {
            return string;
        }
    }
    private static final String BLUE = FormattingCodes.BLUE + "\u2726";
    private static final String RED = FormattingCodes.RED + "\u2724";

    public static String getPattern(String s) {
        String replace = s.replace("0", RED);
        return replace.replace("1", BLUE);
    }

    public static String getStringForPattern(String pattern, boolean fromTooltip) {
        if (fromTooltip) {
            String replace1 = pattern.replace("Pattern: [", "");
            pattern = replace1.replace("§r]", "");
        }
        String replace = pattern.replace(RED, "0");
        return replace.replace(BLUE, "1");
    }

    public String getPattern() {
        return getPattern(this.castingType);
    }


    public final int MANA_COST;
    public final String NAME;
    private final String castingType;
    public final Type TYPE;
    public final Rarity RARITY;
    public final String REGISTRY_NAME;

    public Spell(int mana_cost, String name, String castingType, Type type, Rarity RARITY) {
        this.MANA_COST = mana_cost;
        this.NAME = name;
        if (castingType != null && castingType.length() != 7) {
            throw new RuntimeException("Casting pattern should have a size of 7");
        }
        this.castingType = castingType;
        this.TYPE = type;
        this.RARITY = RARITY;
        this.REGISTRY_NAME = name.toLowerCase().replace(" ", "_");
    }
    public String getName() {
        return NAME;
    }
}
