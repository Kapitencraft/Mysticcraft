package net.kapitencraft.mysticcraft.item.capability.gemstone;

import net.kapitencraft.kap_lib.collection.DoubleMap;
import net.kapitencraft.kap_lib.helpers.CollectorHelper;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneBlock;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneCrystal;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneSeedBlock;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.ModItems;
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
    EMPTY(ChatFormatting.WHITE, ()-> null, 0, "empty", 0),
    ALMANDINE(ChatFormatting.LIGHT_PURPLE, ExtraAttributes.ABILITY_DAMAGE, 0.3, "almandine", GemstoneBlock.HIGH_MEDIUM_STRENGHT),
    JASPER(ChatFormatting.DARK_RED, ExtraAttributes.STRENGTH, 2, "jasper", GemstoneBlock.VERY_HIGH_STRENGHT),
    RUBY(ChatFormatting.RED, () -> Attributes.MAX_HEALTH, 0.5, "ruby", GemstoneBlock.LOW_STRENGHT),
    AMETHYST(ChatFormatting.DARK_PURPLE, () -> Attributes.ARMOR, 2.3, "amethyst", GemstoneBlock.LOW_MEDIUM_STRENGHT),
    SAPPHIRE((ChatFormatting.BLUE), ExtraAttributes.INTELLIGENCE, 2.7, "sapphire", GemstoneBlock.MEDIUM_STRENGHT),
    AQUAMARINE(ChatFormatting.AQUA, ForgeMod.SWIM_SPEED, 0.1, "aquamarine", GemstoneBlock.VERY_LOW_STRENGHT),
    TURQUOISE(ChatFormatting.DARK_AQUA, ExtraAttributes.FISHING_SPEED, 2.9, "turquoise", GemstoneBlock.LOW_MEDIUM_STRENGHT),
    MOONSTONE(0x0A0A0A, ExtraAttributes.DRAW_SPEED, 0.5, "moonstone", GemstoneBlock.HIGH_STRENGHT),
    CELESTINE(ChatFormatting.WHITE, ()-> Attributes.MOVEMENT_SPEED, 0.04, "celestine", GemstoneBlock.LOW_STRENGHT);

    public static EnumCodec<GemstoneType> CODEC = StringRepresentable.fromEnum(GemstoneType::values);
    private final int COLOR;
    public final Supplier<Attribute>  modifiedAttribute;
    public final double BASE_VALUE;
    private final String id;
    private final double blockStrength;

    public static int colorFromCFormat(ChatFormatting formatting) {
        if (formatting.getColor() == null) throw new IllegalStateException("formatting has no color");
        return formatting.getColor();
    }

    GemstoneType(ChatFormatting formatting, Supplier<Attribute> modifiedAttribute, double baseValue, String id, double blockStrength) {
        this(colorFromCFormat(formatting), modifiedAttribute, baseValue, id, blockStrength);
    }

    GemstoneType(int color, Supplier<Attribute> modifiedAttribute, double baseValue, String id, double blockStrength) {
        this.COLOR = color;
        this.BASE_VALUE = baseValue;
        this.modifiedAttribute = modifiedAttribute;
        this.id = id;
        this.blockStrength = blockStrength;
    }

    public static GemstoneType getById(String id) {
        return CODEC.byName(id, EMPTY);
    }

    public Supplier<Attribute> getModifiedAttribute() {
        return modifiedAttribute;
    }

    public float getBlockStrength() {
        return (float) (blockStrength * blockStrength * 2);
    }

    //creative content

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

    public static DoubleMap<GemstoneType, GemstoneSeedBlock.MaterialType, ItemStack> allSeeds() {
        return DoubleMap.of(Arrays.stream(TYPES_TO_USE).collect(CollectorHelper.createMap(GemstoneType::seeds)));
    }

    public static Map<GemstoneType, ItemStack> allBlocks() {
        return Arrays.stream(TYPES_TO_USE).collect(CollectorHelper.createMap(GemstoneType::registerBlock));
    }

    public static DoubleMap<GemstoneType, GemstoneCrystal.Size, ItemStack> allCrystals() {
        return DoubleMap.of(Arrays.stream(TYPES_TO_USE).collect(CollectorHelper.createMap(GemstoneType::crystals)));
    }

    public ItemStack registerBlock() {
        return IGemstoneItem.createData(Rarity.ROUGH, this, ModBlocks.GEMSTONE_BLOCK::getItem);
    }

    public ItemStack registerCrystal() {
        return IGemstoneItem.createData(Rarity.ROUGH, this, ModBlocks.GEMSTONE_CRYSTAL::getItem);
    }


    public Map<GemstoneCrystal.Size, ItemStack> crystals() {
        ItemStack stack = registerCrystal();
        return Arrays.stream(GemstoneCrystal.Size.values()).collect(CollectorHelper.createMap(size -> {
            ItemStack copy = stack.copy();
            copy.getOrCreateTag().putString("Size", size.getSerializedName());
            return copy;
        }));
    }

    private static Map<GemstoneSeedBlock.MaterialType, ItemStack> seeds(GemstoneType t) {
        HashMap<GemstoneSeedBlock.MaterialType, ItemStack> map = new HashMap<>();
        for (GemstoneSeedBlock.MaterialType type : GemstoneSeedBlock.MaterialType.values()) {
            ItemStack stack = GemstoneSeedBlock.Item.createData(t, type);
            map.put(type, stack);
        }
        return map;
    }

    public static final GemstoneType[] TYPES_TO_USE =  createTypesToUse();
    public static final Rarity[] RARITIES_TO_USE = createRaritiesToUse();

    private static Rarity[] createRaritiesToUse() {
        Rarity[] rarities = new Rarity[5];
        System.arraycopy(Rarity.values(), 1, rarities, 0, 5);
        return rarities;
    }

    private static GemstoneType[] createTypesToUse() {
        GemstoneType[] types = new GemstoneType[9];
        System.arraycopy(GemstoneType.values(), 1, types, 0, 9);
        return types;
    }

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

    //creative content end

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
        EMPTY(0, colorFromCFormat(ChatFormatting.DARK_GRAY), 0, "empty"),
        ROUGH(1, colorFromCFormat(ChatFormatting.WHITE), 1, "rough"),
        FLAWED(2, colorFromCFormat(ChatFormatting.GREEN), 1.75, "flawed"),
        FINE(3, colorFromCFormat(ChatFormatting.BLUE), 2.3, "fine"),
        FLAWLESS(4, colorFromCFormat(ChatFormatting.DARK_PURPLE), 3, "flawless"),
        PERFECT(5, colorFromCFormat(ChatFormatting.GOLD), 4.8, "perfect");
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