package net.kapitencraft.mysticcraft.item.gemstone_slot;

import net.kapitencraft.mysticcraft.misc.FormattingCodes;

import javax.annotation.Nullable;

public class Gemstone {


    public final String NAME;
    private final String COLOUR;
    protected final String registryName;
    private @Nullable Rarity rarity;

    public Gemstone(String name, String colour, String registryName) {
        this.registryName = registryName;
        this.NAME = name;
        this.COLOUR = colour;
        this.rarity = null;
    }

    public Rarity getRarity() {
        if (this != null) {
            return this.rarity;
        } else {
            return Rarity.EMPTY;
        }
    }

    public String getColour() {
        return this.COLOUR;
    }

    public static class Rarity {

        public static final Rarity ROUGH = new Rarity(FormattingCodes.WHITE.UNICODE);
        public static final Rarity FLAWED = new Rarity(FormattingCodes.GREEN.UNICODE);
        public static final Rarity FINE = new Rarity(FormattingCodes.BLUE.UNICODE);
        public static final Rarity FLAWLESS = new Rarity(FormattingCodes.DARK_PURPLE.UNICODE);
        public static final Rarity PERFECT = new Rarity(FormattingCodes.ORANGE.UNICODE);
        public static final Rarity EMPTY = new Rarity(FormattingCodes.GRAY.UNICODE);
        public final String COLOUR;

        public Rarity(String colour) {
            this.COLOUR = colour;
        }
    }
}
