package net.kapitencraft.mysticcraft.item.gemstone.gemstones;

import net.kapitencraft.mysticcraft.item.gemstone.Gemstone;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class RubyGemstone extends Gemstone {
    protected static final String registryName = "ruby";
    public RubyGemstone() {
        super("Ruby", FormattingCodes.RED.UNICODE, Attributes.MAX_HEALTH, 5);
    }
    public RubyGemstone(Rarity rarity) {
        this();
        this.rarity = rarity;
    }
    public static String getRegistryName() {
        return "ruby";
    }
}
