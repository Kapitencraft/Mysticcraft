package net.kapitencraft.mysticcraft.item.gemstone;

import net.kapitencraft.mysticcraft.item.gemstone.gemstones.AlmandineGemstone;
import net.kapitencraft.mysticcraft.item.gemstone.gemstones.JasperGemstone;
import net.kapitencraft.mysticcraft.item.gemstone.gemstones.RubyGemstone;
import net.kapitencraft.mysticcraft.item.gemstone.gemstones.SapphireGemstone;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public  class  GemstoneItem extends Item {
    private final Gemstone.Rarity RARITY;
    public final String gemstoneName;
    public GemstoneItem(Gemstone.Rarity rarity, @Nullable Properties properties, String gemstoneName) {
        super((properties != null ? properties : new Properties()));
        this.RARITY = rarity;
        this.gemstoneName = gemstoneName;
    }

    public Gemstone.Rarity getRarity() {
        return this.RARITY;
    }

    public Gemstone toGemstone() {
        return switch (this.gemstoneName) {
            case "almandine" -> new AlmandineGemstone(this.RARITY);
            case "jasper" -> new JasperGemstone(this.RARITY);
            case "ruby" -> new RubyGemstone(this.RARITY);
            case "sapphire" -> new SapphireGemstone(this.RARITY);
            default -> throw new IllegalStateException("There are no other Gemstones");
        };
    }

}
