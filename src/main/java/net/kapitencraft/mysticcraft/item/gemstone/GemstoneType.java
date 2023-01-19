package net.kapitencraft.mysticcraft.item.gemstone;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Objects;
import java.util.function.Supplier;

public enum GemstoneType {
    ALMANDINE(FormattingCodes.LIGHT_PURPLE, ModAttributes.ABILITY_DAMAGE, 0.3, "almandine"),
    JASPER(FormattingCodes.ORANGE, ModAttributes.STRENGTH, 2, "jasper"),
    RUBY(FormattingCodes.RED, () -> Attributes.MAX_HEALTH, 5, "ruby"),
    SAPPHIRE(FormattingCodes.BLUE, ModAttributes.INTELLIGENCE, 3.4, "sapphire");

    private final String COLOUR;
    public final Supplier<Attribute>  modifiedAttribute;
    public final double BASE_VALUE;
    private final String id;
    GemstoneType(String colour, Supplier<Attribute> modifiedAttribute, double baseValue, String id) {
        this.COLOUR = colour;
        this.BASE_VALUE = baseValue;
        this.modifiedAttribute = modifiedAttribute;
        this.id = id;
    }

    public static GemstoneType getById(String id) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].id.equals(id)) {
                return values()[i];
            }
        }
        return null;
    }


    public String getColour() {
        return this.COLOUR;
    }

    public String getId() {
        return id;
    }

    public enum Rarity {
        ROUGH(FormattingCodes.WHITE, 1, "rough"),
        FLAWED(FormattingCodes.GREEN, 1.75, "flawed"),
        FINE(FormattingCodes.BLUE, 2.3, "fine"),
        FLAWLESS(FormattingCodes.DARK_PURPLE, 3, "flawless"),
        PERFECT(FormattingCodes.ORANGE, 4.8, "perfect"),
        EMPTY(FormattingCodes.GRAY, 0, "empty");
        public final String COLOUR;
        public final double modMul;
        private final String id;

        Rarity(String colour, double modMul, String id) {
            this.COLOUR = colour;
            this.modMul = modMul;
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public static Rarity getById(String id) {
            for (int i = 0; i < values().length; i++) {
                if (Objects.equals(values()[i].id, id)) {
                    return values()[i];
                }
            }
            return Rarity.EMPTY;
        }
    }
}
