package net.kapitencraft.mysticcraft.item.gemstone;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Supplier;

public enum GemstoneType {
    ALMANDINE(getColorFromChatFormatting(ChatFormatting.LIGHT_PURPLE), ModAttributes.ABILITY_DAMAGE, 0.3, "almandine"),
    JASPER(getColorFromChatFormatting(ChatFormatting.DARK_RED), ModAttributes.STRENGTH, 2, "jasper"),
    RUBY(getColorFromChatFormatting(ChatFormatting.RED), () -> Attributes.MAX_HEALTH, 1.2, "ruby"),
    SAPPHIRE(getColorFromChatFormatting(ChatFormatting.BLUE), ModAttributes.INTELLIGENCE, 2.7, "sapphire"),
    AQUAMARINE(getColorFromChatFormatting(ChatFormatting.AQUA), ModAttributes.FISHING_SPEED, 3.1, "aquamarine"),
    MOON_STONE(getColorFromChatFormatting(ChatFormatting.WHITE), ModAttributes.DRAW_SPEED, 0.5, "moon_stone");

    public static final TabGroup GEMSTONE_GROUP = new TabGroup(TabRegister.TabTypes.SPELL_AND_GEMSTONE);
    private final int COLOR;
    public final Supplier<Attribute>  modifiedAttribute;
    public final double BASE_VALUE;
    private final String id;

    public static int getColorFromChatFormatting(ChatFormatting formatting) {
        MysticcraftMod.sendInfo(String.valueOf(formatting.getColor() == null));
        return formatting.getColor() == null ? -1 : formatting.getColor();
    }


    GemstoneType(int color, Supplier<Attribute> modifiedAttribute, double baseValue, String id) {
        this.COLOR = color;
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

    public static HashMap<GemstoneType, HashMap<Rarity, RegistryObject<GemstoneItem>>> createRegistry() {
        HashMap<GemstoneType, HashMap<Rarity, RegistryObject<GemstoneItem>>> toReturn = new HashMap<>();
        for (GemstoneType type : values()) {
            toReturn.put(type, type.registerItems());
        }
        return toReturn;
    }

    public HashMap<Rarity, RegistryObject<GemstoneItem>> registerItems() {
        HashMap<Rarity, RegistryObject<GemstoneItem>> toReturn = new HashMap<>();
        for (Rarity rarity : Rarity.values()) {
            if (rarity != Rarity.EMPTY) {
                toReturn.put(rarity, ModItems.register(rarity.id + "_" + this.getId() + "_gemstone", () -> new GemstoneItem(rarity, this.getId()), GEMSTONE_GROUP));
            }
        }
        return toReturn;
    }

    public int getColour() {
        return this.COLOR;
    }

    public String getId() {
        return id;
    }

    public enum Rarity {
        ROUGH(getColorFromChatFormatting(ChatFormatting.WHITE), 1, "rough"),
        FLAWED(getColorFromChatFormatting(ChatFormatting.GREEN), 1.75, "flawed"),
        FINE(getColorFromChatFormatting(ChatFormatting.BLUE), 2.3, "fine"),
        FLAWLESS(getColorFromChatFormatting(ChatFormatting.DARK_PURPLE), 3, "flawless"),
        PERFECT(getColorFromChatFormatting(ChatFormatting.GOLD), 4.8, "perfect"),
        EMPTY(getColorFromChatFormatting(ChatFormatting.DARK_GRAY), 0, "empty");
        public final int COLOUR;
        public final double modMul;
        private final String id;

        Rarity(int colour, double modMul, String id) {
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