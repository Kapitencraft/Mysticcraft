package net.kapitencraft.mysticcraft.item.gemstone.gemstones;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.gemstone.Gemstone;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;

public class SapphireGemstone extends Gemstone {
    protected static final String registryName = "sapphire";
    public SapphireGemstone() {
        super("Sapphire", FormattingCodes.BLUE.UNICODE, ModAttributes.INTELLIGENCE.get(), 3.4);
    }
    public SapphireGemstone(Rarity rarity) {
        this();
        this.rarity = rarity;
    }

    public static String getRegistryName() {
        return "sapphire";
    }
}
