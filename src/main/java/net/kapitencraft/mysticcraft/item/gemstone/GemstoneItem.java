package net.kapitencraft.mysticcraft.item.gemstone;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public  class  GemstoneItem extends Item {
    private final GemstoneType.Rarity RARITY;
    public final String gemstoneName;
    public GemstoneItem(GemstoneType.Rarity rarity, @Nullable Properties properties, String gemstoneName) {
        super((properties != null ? properties : new Properties()));
        this.RARITY = rarity;
        this.gemstoneName = gemstoneName;
    }

    public static GemstoneItem createGemstoneItem(GemstoneType.Rarity rarity, String gemstoneType) {
        net.minecraft.world.item.Rarity rarity1 = net.minecraft.world.item.Rarity.COMMON;
        if (GemstoneType.Rarity.FINE.equals(rarity) || GemstoneType.Rarity.FLAWLESS.equals(rarity)) {
            rarity1 = net.minecraft.world.item.Rarity.UNCOMMON;
        } else if (GemstoneType.Rarity.PERFECT.equals(rarity)) {
            rarity1 = net.minecraft.world.item.Rarity.RARE;
        }
        return new GemstoneItem(rarity, new Item.Properties().rarity(rarity1), gemstoneType);
    }

    public static HashMap<GemstoneType.Rarity, RegistryObject<Item>> registerItems(GemstoneType type, DeferredRegister<Item> registry) {
        HashMap<GemstoneType.Rarity, RegistryObject<Item>> toReturn = new HashMap<>();
        toReturn.put(GemstoneType.Rarity.ROUGH, registry.register("rough_" + type.getId() + "_gemstone", () -> GemstoneItem.createGemstoneItem(GemstoneType.Rarity.ROUGH, type.getId())));
        toReturn.put(GemstoneType.Rarity.FLAWED, registry.register("flawed_" + type.getId() + "_gemstone", () -> GemstoneItem.createGemstoneItem(GemstoneType.Rarity.FLAWED, type.getId())));
        toReturn.put(GemstoneType.Rarity.FINE, registry.register("fine_" + type.getId() + "_gemstone", () -> GemstoneItem.createGemstoneItem(GemstoneType.Rarity.FINE, type.getId())));
        toReturn.put(GemstoneType.Rarity.FLAWLESS, registry.register("flawless_" + type.getId() + "_gemstone", () -> GemstoneItem.createGemstoneItem(GemstoneType.Rarity.FLAWLESS, type.getId())));
        toReturn.put(GemstoneType.Rarity.PERFECT, registry.register("perfect_" + type.getId() + "_gemstone", () -> GemstoneItem.createGemstoneItem(GemstoneType.Rarity.PERFECT, type.getId())));
        return toReturn;
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
