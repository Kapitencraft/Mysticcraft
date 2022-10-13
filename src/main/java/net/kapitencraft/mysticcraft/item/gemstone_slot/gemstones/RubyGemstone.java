package net.kapitencraft.mysticcraft.item.gemstone_slot.gemstones;

import net.kapitencraft.mysticcraft.item.gemstone_slot.Gemstone;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class RubyGemstone extends Gemstone {
    public RubyGemstone() {
        super("Ruby", FormattingCodes.RED.UNICODE, "ruby", Attributes.MAX_HEALTH, 5);
    }
}
