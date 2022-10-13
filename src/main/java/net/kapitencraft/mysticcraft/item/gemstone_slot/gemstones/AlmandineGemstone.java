package net.kapitencraft.mysticcraft.item.gemstone_slot.gemstones;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.gemstone_slot.Gemstone;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;

public class AlmandineGemstone extends Gemstone {
    public AlmandineGemstone() {
        super("Almandine", FormattingCodes.LIGHT_PURPLE.UNICODE, "almandine", ModAttributes.ABILITY_DAMAGE.get(), 0.3);
    }
}
