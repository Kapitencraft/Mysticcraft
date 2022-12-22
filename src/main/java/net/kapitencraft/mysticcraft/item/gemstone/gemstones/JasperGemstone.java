package net.kapitencraft.mysticcraft.item.gemstone.gemstones;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.gemstone.Gemstone;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;

public class JasperGemstone extends Gemstone {
    protected static final String registryName = "jasper";
    public JasperGemstone() {
        super("Jasper", FormattingCodes.ORANGE, ModAttributes.STRENGTH.get(), 2);
    }
    public JasperGemstone(Rarity rarity) {
        this();
        this.rarity = rarity;
    }
    public static String getRegistryName() {
        return "jasper";
    }
}
