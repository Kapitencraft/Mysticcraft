package net.kapitencraft.mysticcraft.item.gemstone_slot.gemstones;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.gemstone_slot.Gemstone;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;

public class SapphireGemstone extends Gemstone {
    public SapphireGemstone() {
        super("Sapphire", FormattingCodes.BLUE.UNICODE, "sapphire", ModAttributes.INTELLIGENCE.get(), 3.4);
    }
}
