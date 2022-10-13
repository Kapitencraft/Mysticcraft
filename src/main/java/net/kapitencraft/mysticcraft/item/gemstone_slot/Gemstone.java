package net.kapitencraft.mysticcraft.item.gemstone_slot;

import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.world.entity.ai.attributes.Attribute;

import javax.annotation.Nullable;

public class Gemstone {


    public final String NAME;
    private final String COLOUR;
    protected final String registryName;
    private @Nullable Rarity rarity;
    public final Attribute modifiedAttribute;
    public final double BASE_VALUE;

    public Gemstone(String name, String colour, String registryName, Attribute modifiedAttribute, double baseValue) {
        this.registryName = registryName;
        this.NAME = name;
        this.COLOUR = colour;
        this.rarity = null;
        this.BASE_VALUE = baseValue;
        this.modifiedAttribute = modifiedAttribute;
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

        public static final Rarity ROUGH = new Rarity(FormattingCodes.WHITE.UNICODE, 1);
        public static final Rarity FLAWED = new Rarity(FormattingCodes.GREEN.UNICODE, 1.75);
        public static final Rarity FINE = new Rarity(FormattingCodes.BLUE.UNICODE, 2.3);
        public static final Rarity FLAWLESS = new Rarity(FormattingCodes.DARK_PURPLE.UNICODE, 3);
        public static final Rarity PERFECT = new Rarity(FormattingCodes.ORANGE.UNICODE, 4.8);
        public static final Rarity EMPTY = new Rarity(FormattingCodes.GRAY.UNICODE, 0);
        public final String COLOUR;
        public final double modMul;

        public Rarity(String colour, double modMul) {
            this.COLOUR = colour;
            this.modMul = modMul;
        }
    }
}
