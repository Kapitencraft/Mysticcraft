package net.kapitencraft.mysticcraft.item.gemstone;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Objects;

public enum GemstoneType {
    ALMANDINE(FormattingCodes.LIGHT_PURPLE, ModAttributes.ABILITY_DAMAGE.get(), 0.3, "almandine"),
    JASPER(FormattingCodes.ORANGE, ModAttributes.STRENGTH.get(), 2, "jasper"),
    RUBY(FormattingCodes.RED, Attributes.MAX_HEALTH, 5, "ruby"),
    SAPPHIRE(FormattingCodes.BLUE, ModAttributes.INTELLIGENCE.get(), 3.4, "sapphire");

    private final String COLOUR;
    public final Attribute modifiedAttribute;
    public final double BASE_VALUE;
    private final String id;
    GemstoneType(String colour, Attribute modifiedAttribute, double baseValue, String id) {
        this.COLOUR = colour;
        this.BASE_VALUE = baseValue;
        this.modifiedAttribute = modifiedAttribute;
        this.id = id;
    }

    public static GemstoneType getById(String id) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].id.equals(id)) {
                return values()[i];
            }
        }
        return null;
    }


    public String getColour() {
        return this.COLOUR;
    }

    public String getId() {
        return id;
    }

    public static GemstoneItem createGemstoneItem(Rarity rarity, String gemstoneType) {
        net.minecraft.world.item.Rarity rarity1 = net.minecraft.world.item.Rarity.COMMON;
        if (Rarity.FINE.equals(rarity) || Rarity.FLAWLESS.equals(rarity)) {
            rarity1 = net.minecraft.world.item.Rarity.UNCOMMON;
        } else if (Rarity.PERFECT.equals(rarity)) {
            rarity1 = net.minecraft.world.item.Rarity.RARE;
        }
        return new GemstoneItem(rarity, new Item.Properties().rarity(rarity1), gemstoneType);
    }


    public HashMap<Rarity, RegistryObject<Item>> registerItems(DeferredRegister<Item> registry) {
        HashMap<Rarity, RegistryObject<Item>> toReturn = new HashMap<>();
        toReturn.put(Rarity.ROUGH, registry.register("rough_" + getId() + "_gemstone", () -> createGemstoneItem(Rarity.ROUGH, getId())));
        toReturn.put(Rarity.FLAWED, registry.register("flawed_" + getId() + "_gemstone", () -> createGemstoneItem(Rarity.FLAWED, getId())));
        toReturn.put(Rarity.FINE, registry.register("fine_" + getId() + "_gemstone", () -> createGemstoneItem(Rarity.FINE, getId())));
        toReturn.put(Rarity.FLAWLESS, registry.register("flawless_" + getId() + "_gemstone", () -> createGemstoneItem(Rarity.FLAWLESS, getId())));
        toReturn.put(Rarity.PERFECT, registry.register("perfect_" + getId() + "_gemstone", () -> createGemstoneItem(Rarity.PERFECT, getId())));
        return toReturn;
    }

    public enum Rarity {
        ROUGH(FormattingCodes.WHITE, 1, "rough"),
        FLAWED(FormattingCodes.GREEN, 1.75, "flawed"),
        FINE(FormattingCodes.BLUE, 2.3, "fine"),
        FLAWLESS(FormattingCodes.DARK_PURPLE, 3, "flawless"),
        PERFECT(FormattingCodes.ORANGE, 4.8, "perfect"),
        EMPTY(FormattingCodes.GRAY, 0, "empty");
        public final String COLOUR;
        public final double modMul;
        private final String id;

        Rarity(String colour, double modMul, String id) {
            this.COLOUR = colour;
            this.modMul = modMul;
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public static Rarity getById(String id) {
            for (int i = 0; i < values().length; i++) {
                if (Objects.equals(values()[i].id, id)) {
                    return values()[i];
                }
            }
            return Rarity.EMPTY;
        }
    }
}
