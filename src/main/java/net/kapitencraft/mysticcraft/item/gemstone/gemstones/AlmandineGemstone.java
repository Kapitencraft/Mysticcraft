package net.kapitencraft.mysticcraft.item.gemstone.gemstones;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.gemstone.Gemstone;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;

public class AlmandineGemstone extends Gemstone {
    public AlmandineGemstone() {
        super("Almandine", FormattingCodes.LIGHT_PURPLE.UNICODE, ModAttributes.ABILITY_DAMAGE.get(), 0.3);
    }

    public AlmandineGemstone(Rarity rarity) {
        this();
        this.rarity = rarity;
    }

    public static String getRegistryName() {
        return "almandine";
    }

}
