package net.kapitencraft.mysticcraft.item.misc;

import net.kapitencraft.mysticcraft.misc.FormattingCodes;

public enum DropRarity {
    COMMON(0, "common", FormattingCodes.WHITE, "COMMON"),
    UNCOMMON(1, "uncommon", FormattingCodes.GREEN, "UNCOMMON"),
    RARE(2, "rare", FormattingCodes.BLUE, "RARE"),
    LEGENDARY(3, "legendary", FormattingCodes.LIGHT_PURPLE, "CRAZY RARE"),
    PRAY_RNG(4, "pray_rng", FormattingCodes.RED, "PRAY TO RNGESUS"),
    RNG_INCARNATE(5, "rng_incarnate", FormattingCodes.DARK_RED, "RNGESUS INCARNATE");
    private final int Id;
    private final String name;
    private final String color;
    private final String displayName;

    DropRarity(int id, String name, String color, String displayName) {
        this.Id = id;
        this.name = name;
        this.color = color;
        this.displayName = displayName;
    }


    public int getID() {
        return this.Id;
    }

    public String getColor() {
        return color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

}

