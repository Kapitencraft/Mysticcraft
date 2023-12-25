package net.kapitencraft.mysticcraft.item.misc;

import net.minecraft.ChatFormatting;

public enum DropRarity {
    COMMON(0, "common", ChatFormatting.WHITE, "COMMON"),
    UNCOMMON(1, "uncommon", ChatFormatting.GREEN, "UNCOMMON"),
    RARE(2, "rare", ChatFormatting.BLUE, "RARE"),
    LEGENDARY(3, "legendary", ChatFormatting.LIGHT_PURPLE, "CRAZY RARE"),
    PRAY_RNG(4, "pray_rng", ChatFormatting.RED, "PRAY TO RNGESUS"),
    RNG_INCARNATE(5, "rng_incarnate", ChatFormatting.DARK_RED, "RNGESUS INCARNATE");
    private final int Id;
    private final String name;
    private final ChatFormatting color;
    private final String displayName;

    DropRarity(int id, String name, ChatFormatting color, String displayName) {
        this.Id = id;
        this.name = name;
        this.color = color;
        this.displayName = displayName;
    }


    public int getID() {
        return this.Id;
    }

    public ChatFormatting getColor() {
        return color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

}

