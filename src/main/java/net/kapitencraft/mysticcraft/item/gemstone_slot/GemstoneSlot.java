package net.kapitencraft.mysticcraft.item.gemstone_slot;

import net.kapitencraft.mysticcraft.misc.FormattingCodes;

public class GemstoneSlot {
    private Rarity gem_rarity;
    private String gem_type;

    public GemstoneSlot(Rarity gem_rarity, String gem_type) {
        this.gem_rarity = gem_rarity;
        this.gem_type = gem_type;
    }

    public Rarity getGemRarity() {
        return this.gem_rarity;
    }

    public String getGemType() {
        return this.gem_type;
    }

    public void setGemRarity(Rarity rarity) {
        this.gem_rarity = rarity;
    }

    public void setGemType(String type) {
        this.gem_type = type;
    }

    public String getDisplay() {
        return "[" + "" + "]";
    }

    public static class Rarity {
        public final String COLOUR;

        public Rarity(String colour) {
            this.COLOUR = colour;
        }
    }
}
