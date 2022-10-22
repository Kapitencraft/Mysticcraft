package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.misc.Color;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;

public class DropRarity {
    public static final DropRarity COMMON = create("common", FormattingCodes.WHITE, "COMMON");
    public static final DropRarity UNCOMMON = create("uncommon", FormattingCodes.GREEN, "UNCOMMON");
    public static final DropRarity RARE = create("rare", FormattingCodes.BLUE, "RARE");
    public static final DropRarity LEGENDARY = create("legendary", FormattingCodes.LIGHT_PURPLE, "CRAZY RARE");
    public static final DropRarity PRAY_RNG = create("pray_rng", FormattingCodes.RED, "PRAY TO RNGESUS");
    public static final DropRarity RNG_INCARNATE = create("rng_incarnate", FormattingCodes.DARK_RED, "RNGESUS INCARNATE");
    private final int Id;
    private static int genId;
    private final String name;
    private final Color color;
    private final String displayName;

    public DropRarity(int id, String name, Color color, String displayName) {
        this.Id = id;
        this.name = name;
        this.color = color;
        this.displayName = displayName;
    }


    public int getID() {
        return this.Id;
    }

    public Color getColor() {
        return color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    private static DropRarity create(String name, Color color, String displayName) {
        DropRarity rarity = new DropRarity(genId, name, color, displayName);
        genId++;
        return rarity;
    }
}

