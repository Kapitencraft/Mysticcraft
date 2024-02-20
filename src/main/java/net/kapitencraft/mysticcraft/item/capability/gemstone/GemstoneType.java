package net.kapitencraft.mysticcraft.item.capability.gemstone;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.api.DoubleMap;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneBlock;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneCrystal;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.helpers.CollectorHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public enum GemstoneType implements StringRepresentable {
    EMPTY(colorFromCFormat(ChatFormatting.WHITE), ()-> null, 0, "empty", 0),
    ALMANDINE(colorFromCFormat(ChatFormatting.LIGHT_PURPLE), ModAttributes.ABILITY_DAMAGE, 0.3, "almandine", GemstoneBlock.HIGH_MEDIUM_STRENGHT),
    JASPER(colorFromCFormat(ChatFormatting.DARK_RED), ModAttributes.STRENGTH, 2, "jasper", GemstoneBlock.VERY_HIGH_STRENGHT),
    RUBY(colorFromCFormat(ChatFormatting.RED), () -> Attributes.MAX_HEALTH, 0.5, "ruby", GemstoneBlock.LOW_STRENGHT),
    AMETHYST(colorFromCFormat(ChatFormatting.DARK_PURPLE), () -> Attributes.ARMOR, 2.3, "amethyst", GemstoneBlock.LOW_MEDIUM_STRENGHT),
    SAPPHIRE(colorFromCFormat(ChatFormatting.BLUE), ModAttributes.INTELLIGENCE, 2.7, "sapphire", GemstoneBlock.MEDIUM_STRENGHT),
    AQUAMARINE(colorFromCFormat(ChatFormatting.AQUA), ForgeMod.SWIM_SPEED, 0.1, "aquamarine", GemstoneBlock.VERY_LOW_STRENGHT),
    TURQUOISE(colorFromCFormat(ChatFormatting.DARK_AQUA), ModAttributes.FISHING_SPEED, 2.9, "turquoise", GemstoneBlock.LOW_MEDIUM_STRENGHT),
    MOONSTONE(MathHelper.RGBtoInt(10, 10, 10), ModAttributes.DRAW_SPEED, 0.5, "moonstone", GemstoneBlock.HIGH_STRENGHT),
    CELESTINE(colorFromCFormat(ChatFormatting.WHITE), ()-> Attributes.MOVEMENT_SPEED, 0.07, "celestine", GemstoneBlock.LOW_STRENGHT);

    public static Codec<GemstoneType> CODEC = StringRepresentable.fromEnum(GemstoneType::values);
    private final int COLOR;
    public final Supplier<Attribute>  modifiedAttribute;
    public final double BASE_VALUE;
    private final String id;
    private final double blockStrength;

    public static int colorFromCFormat(ChatFormatting formatting) {
        if (formatting.getColor() == null) throw new IllegalStateException("formatting has no color");
        return formatting.getColor();
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

    public float getBlockStrength() {
        return (float) (blockStrength * blockStrength * 2);
    }
    private static final DoubleMap<GemstoneType, Rarity, ItemStack> ALL_ITEMS = new DoubleMap<>();

    public static DoubleMap<GemstoneType, Rarity, ItemStack> allItems() {
        if (ALL_ITEMS.isEmpty()) {
            for (GemstoneType type : TYPES_TO_USE) {
                ALL_ITEMS.put(type, type.registerItems());
            }
            ALL_ITEMS.immutable();
        }
        return ALL_ITEMS;
    }

    public static Map<GemstoneType, ItemStack> allBlocks() {
        return Arrays.stream(values()).collect(CollectorHelper.createMap(GemstoneType::registerBlock));
    }

    public ItemStack registerBlock() {
        return IGemstoneItem.createData(Rarity.ROUGH, this, ModBlocks.GEMSTONE_BLOCK::getItem);
    }

    public ItemStack registerCrystal() {
        return IGemstoneItem.createData(Rarity.ROUGH, this, ModBlocks.GEMSTONE_CRYSTAL::getItem);
    }

    public static DoubleMap<GemstoneType, GemstoneCrystal.Size, ItemStack> allCrystals() {
        return DoubleMap.of(Arrays.stream(values()).collect(CollectorHelper.createMap(GemstoneType::crystals)));
    }


    public Map<GemstoneCrystal.Size, ItemStack> crystals() {
        ItemStack stack = registerCrystal();
        return Arrays.stream(GemstoneCrystal.Size.values()).collect(CollectorHelper.createMap(size -> {
            ItemStack copy = stack.copy();
            copy.getOrCreateTag().putString("Size", size.getSerializedName());
            return copy;
        }));
    }

    public static final List<GemstoneType> TYPES_TO_USE = CollectionHelper.remove(GemstoneType.values(), GemstoneType.EMPTY);
    public static final List<Rarity> RARITIES_TO_USE = CollectionHelper.remove(Rarity.values(), Rarity.EMPTY);

    public static List<ItemStack> allForRarity(Rarity rarity) {
        return allItems().values().stream().map(map -> map.get(rarity)).toList();
    }

    public HashMap<Rarity, ItemStack> registerItems() {
        HashMap<Rarity, ItemStack> toReturn = new HashMap<>();
        for (Rarity rarity : RARITIES_TO_USE) {
            if (rarity != Rarity.EMPTY) {
                toReturn.put(rarity, IGemstoneItem.createData(rarity, this, ModItems.GEMSTONE));
            }
        }
        return toReturn;
    }



    public MutableComponent getDispName() {
        return Component.translatable("gem_type." + this.getSerializedName());
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

    public enum Rarity implements StringRepresentable {
        ROUGH(1, colorFromCFormat(ChatFormatting.WHITE), 1, "rough"),
        FLAWED(2, colorFromCFormat(ChatFormatting.GREEN), 1.75, "flawed"),
        FINE(3, colorFromCFormat(ChatFormatting.BLUE), 2.3, "fine"),
        FLAWLESS(4, colorFromCFormat(ChatFormatting.DARK_PURPLE), 3, "flawless"),
        PERFECT(5, colorFromCFormat(ChatFormatting.GOLD), 4.8, "perfect"),
        EMPTY(0, colorFromCFormat(ChatFormatting.DARK_GRAY), 0, "empty");
        public static final EnumCodec<Rarity> CODEC = StringRepresentable.fromEnum(Rarity::values);
        public final int colour, level;
        public final double modMul;
        private final String id;

        Rarity(int level, int colour, double modMul, String id) {
            this.level = level;
            this.colour = colour;
            this.modMul = modMul;
            this.id = id;
        }

        public Rarity next() {
            if (this == PERFECT) {
                throw new IllegalArgumentException("perfect is perfect!");
            }
            return switch (this) {
                case ROUGH -> FLAWED;
                case FLAWED -> FINE;
                case FINE -> FLAWLESS;
                case FLAWLESS -> PERFECT;
                default -> ROUGH;
            };
        }

        public String getId() {
            return id;
        }

        public int getLevel() {
            return level;
        }

        public static Rarity getById(String id) {
            return CODEC.byName(id, EMPTY);
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