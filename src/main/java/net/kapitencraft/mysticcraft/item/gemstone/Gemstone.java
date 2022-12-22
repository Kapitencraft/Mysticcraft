package net.kapitencraft.mysticcraft.item.gemstone;

import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.HashMap;

public abstract class Gemstone {


    public final String NAME;
    private final String COLOUR;
    protected @Nullable Rarity rarity;
    public final Attribute modifiedAttribute;
    public final double BASE_VALUE;
    public Gemstone(String name, String colour, Attribute modifiedAttribute, double baseValue) {
        this.NAME = name;
        this.COLOUR = colour;
        this.rarity = Rarity.EMPTY;
        this.BASE_VALUE = baseValue;
        this.modifiedAttribute = modifiedAttribute;
    }

    public Gemstone(String name, String colour, Attribute modifiedAttribute, double baseValue, Rarity rarity) {
        this(name, colour, modifiedAttribute, baseValue);
        this.rarity = rarity;
    }

    public Rarity getRarity() {
        if (this != null) {
            return this.rarity;
        } else {
            return Rarity.EMPTY;
        }
    }


    public String getColour() {
        return this.COLOUR;
    }

    public static GemstoneItem createGemstoneItem(Rarity rarity, String gemstoneType) {
        net.minecraft.world.item.Rarity rarity1 = net.minecraft.world.item.Rarity.COMMON;
        if (Rarity.ROUGH.equals(rarity) || Rarity.FLAWED.equals(rarity)) {
            rarity1 = net.minecraft.world.item.Rarity.COMMON;
        } else if (Rarity.FINE.equals(rarity) || Rarity.FLAWLESS.equals(rarity)) {
            rarity1 = net.minecraft.world.item.Rarity.UNCOMMON;
        } else if (Rarity.PERFECT.equals(rarity)) {
            rarity1 = net.minecraft.world.item.Rarity.RARE;
        }
        GemstoneItem gemstoneItem = new GemstoneItem(rarity, new Item.Properties().rarity(rarity1), gemstoneType);
        return gemstoneItem;
    }


    public static HashMap<Rarity, RegistryObject<Item>> registerItems(DeferredRegister<Item> registry, String registryName) {
        HashMap<Rarity, RegistryObject<Item>> toReturn = new HashMap<>();
        toReturn.put(Rarity.ROUGH, registry.register("rough_" + registryName + "_gemstone", () -> createGemstoneItem(Rarity.ROUGH, registryName)));
        toReturn.put(Rarity.FLAWED, registry.register("flawed_" + registryName + "_gemstone", () -> createGemstoneItem(Rarity.FLAWED, registryName)));
        toReturn.put(Rarity.FINE, registry.register("fine_" + registryName + "_gemstone", () -> createGemstoneItem(Rarity.FINE, registryName)));
        toReturn.put(Rarity.FLAWLESS, registry.register("flawless_" + registryName + "_gemstone", () -> createGemstoneItem(Rarity.FLAWLESS, registryName)));
        toReturn.put(Rarity.PERFECT, registry.register("perfect_" + registryName + "_gemstone", () -> createGemstoneItem(Rarity.PERFECT, registryName)));
        return toReturn;
    }

    public static class Rarity {

        public static final Rarity ROUGH = new Rarity(FormattingCodes.WHITE, 1);
        public static final Rarity FLAWED = new Rarity(FormattingCodes.GREEN, 1.75);
        public static final Rarity FINE = new Rarity(FormattingCodes.BLUE, 2.3);
        public static final Rarity FLAWLESS = new Rarity(FormattingCodes.DARK_PURPLE, 3);
        public static final Rarity PERFECT = new Rarity(FormattingCodes.ORANGE, 4.8);
        public static final Rarity EMPTY = new Rarity(FormattingCodes.GRAY, 0);
        public final String COLOUR;
        public final double modMul;

        public Rarity(String colour, double modMul) {
            this.COLOUR = colour;
            this.modMul = modMul;
        }
    }
}
