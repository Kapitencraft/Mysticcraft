package net.kapitencraft.mysticcraft.item.data.gemstone;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.GemstoneBlock;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.SaveAbleEnum;
import net.minecraft.ChatFormatting;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public enum GemstoneType implements StringRepresentable {
    EMPTY(0, ()-> null, 0, "empty", 0),
    ALMANDINE(getColorFromChatFormatting(ChatFormatting.LIGHT_PURPLE), ModAttributes.ABILITY_DAMAGE, 0.3, "almandine", GemstoneBlock.HIGH_MEDIUM_STRENGHT),
    JASPER(getColorFromChatFormatting(ChatFormatting.DARK_RED), ModAttributes.STRENGTH, 2, "jasper", GemstoneBlock.VERY_HIGH_STRENGHT),
    RUBY(getColorFromChatFormatting(ChatFormatting.RED), () -> Attributes.MAX_HEALTH, 0.5, "ruby", GemstoneBlock.LOW_STRENGHT),
    AMETHYST(getColorFromChatFormatting(ChatFormatting.DARK_PURPLE), () -> Attributes.ARMOR, 2.3, "amethyst", GemstoneBlock.LOW_MEDIUM_STRENGHT),
    SAPPHIRE(getColorFromChatFormatting(ChatFormatting.BLUE), ModAttributes.INTELLIGENCE, 2.7, "sapphire", GemstoneBlock.MEDIUM_STRENGHT),
    AQUAMARINE(getColorFromChatFormatting(ChatFormatting.AQUA), ForgeMod.SWIM_SPEED, 0.1, "aquamarine", GemstoneBlock.VERY_LOW_STRENGHT),
    TURQUOISE(getColorFromChatFormatting(ChatFormatting.DARK_AQUA), ModAttributes.FISHING_SPEED, 2.9, "turquoise", GemstoneBlock.LOW_MEDIUM_STRENGHT),
    MOONSTONE(getColorFromChatFormatting(ChatFormatting.BLACK), ModAttributes.DRAW_SPEED, 0.5, "moonstone", GemstoneBlock.HIGH_STRENGHT),
    CELESTINE(getColorFromChatFormatting(ChatFormatting.WHITE), ()-> Attributes.MOVEMENT_SPEED, 0.07, "celestine", GemstoneBlock.LOW_STRENGHT);

    public static Codec<GemstoneType> CODEC = StringRepresentable.fromEnum(GemstoneType::values);
    public static final TabGroup GEMSTONE_GROUP = new TabGroup(TabRegister.TabTypes.SPELL_AND_GEMSTONE);
    private final int COLOR;
    public final Supplier<Attribute>  modifiedAttribute;
    public final double BASE_VALUE;
    private final String id;
    private final double blockStrength;

    public static int getColorFromChatFormatting(ChatFormatting formatting) {
        MysticcraftMod.sendInfo(String.valueOf(formatting.getColor() == null));
        return formatting.getColor() == null ? -1 : formatting.getColor();
    }

    GemstoneType(int color, Supplier<Attribute> modifiedAttribute, double baseValue, String id, double blockStrength) {
        this.COLOR = color;
        this.BASE_VALUE = baseValue;
        this.modifiedAttribute = modifiedAttribute;
        this.id = id;
        this.blockStrength = blockStrength;
    }

    public static GemstoneType getById(String id) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].id.equals(id)) {
                return values()[i];
            }
        }
        return EMPTY;
    }

    public Supplier<Attribute> getModifiedAttribute() {
        return modifiedAttribute;
    }

    public double getBlockStrength() {
        return blockStrength * blockStrength * 2;
    }

    public static HashMap<GemstoneType, HashMap<Rarity, RegistryObject<GemstoneItem>>> createRegistry() {
        HashMap<GemstoneType, HashMap<Rarity, RegistryObject<GemstoneItem>>> toReturn = new HashMap<>();
        for (GemstoneType type : TYPES_TO_USE) {
            toReturn.put(type, type.registerItems());
        }
        return toReturn;
    }

    private static final List<GemstoneType> TYPES_TO_USE = CollectionHelper.remove(GemstoneType.values(), GemstoneType.EMPTY);
    private static final List<Rarity> RARITIES_TO_USE = CollectionHelper.remove(Rarity.values(), Rarity.EMPTY);

    public HashMap<Rarity, RegistryObject<GemstoneItem>> registerItems() {
        HashMap<Rarity, RegistryObject<GemstoneItem>> toReturn = new HashMap<>();
        for (Rarity rarity : RARITIES_TO_USE) {
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

    @Override
    public @NotNull String getSerializedName() {
        return id;
    }

    public enum Rarity implements SaveAbleEnum, StringRepresentable {
        ROUGH(1, getColorFromChatFormatting(ChatFormatting.WHITE), 1, "rough"),
        FLAWED(2, getColorFromChatFormatting(ChatFormatting.GREEN), 1.75, "flawed"),
        FINE(3, getColorFromChatFormatting(ChatFormatting.BLUE), 2.3, "fine"),
        FLAWLESS(4, getColorFromChatFormatting(ChatFormatting.DARK_PURPLE), 3, "flawless"),
        PERFECT(5, getColorFromChatFormatting(ChatFormatting.GOLD), 4.8, "perfect"),
        EMPTY(0, getColorFromChatFormatting(ChatFormatting.DARK_GRAY), 0, "empty");
        public static final Codec<Rarity> CODEC = StringRepresentable.fromEnum(Rarity::values);
        public final int colour, level;
        public final double modMul;
        private final String id;

        Rarity(int level, int colour, double modMul, String id) {
            this.level = level;
            this.colour = colour;
            this.modMul = modMul;
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public int getLevel() {
            return level;
        }

        public static Rarity getById(String id) {
            return SaveAbleEnum.getValue(EMPTY, id, values());
        }

        @Override
        public String getName() {
            return id;
        }

        public static Rarity byLevel(int level) {
            return MiscHelper.getValue(Rarity::getLevel, EMPTY, level, values());
        }

        @Override
        public @NotNull String getSerializedName() {
            return id;
        }
    }
}