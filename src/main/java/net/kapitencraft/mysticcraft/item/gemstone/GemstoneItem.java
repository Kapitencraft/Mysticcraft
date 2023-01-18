package net.kapitencraft.mysticcraft.item.gemstone;

import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public  class  GemstoneItem extends Item {
    private final GemstoneType.Rarity RARITY;
    public final String gemstoneName;
    public GemstoneItem(GemstoneType.Rarity rarity, @Nullable Properties properties, String gemstoneName) {
        super((properties != null ? properties : new Properties()));
        this.RARITY = rarity;
        this.gemstoneName = gemstoneName;
    }

    public GemstoneType.Rarity getRarity() {
        return this.RARITY;
    }

    public GemstoneType getGemstone() {
        return switch (this.gemstoneName) {
            case "almandine" -> GemstoneType.ALMANDINE;
            case "jasper" -> GemstoneType.JASPER;
            case "ruby" -> GemstoneType.RUBY;
            case "sapphire" -> GemstoneType.SAPPHIRE;
            default -> throw new IllegalStateException("There are no other Gemstones");
        };
    }

}
