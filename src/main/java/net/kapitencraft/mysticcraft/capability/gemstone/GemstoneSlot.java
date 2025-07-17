package net.kapitencraft.mysticcraft.capability.gemstone;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.gui.gemstone_grinder.GemstoneGrinderMenu;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GemstoneSlot {

    public static final Codec<GemstoneSlot> CODEC = RecordCodecBuilder.create(gemstoneSlotInstance ->
            gemstoneSlotInstance.group(
                    Type.CODEC.fieldOf("Type").forGetter(GemstoneSlot::getType),
                    GemstoneType.CODEC.optionalFieldOf("Gem", GemstoneType.EMPTY).forGetter(GemstoneSlot::getAppliedGemstone),
                    GemstoneType.Rarity.CODEC.optionalFieldOf("GemRarity", GemstoneType.Rarity.EMPTY).forGetter(GemstoneSlot::getGemRarity)
            ).apply(gemstoneSlotInstance, GemstoneSlot::new)
    );
    public static final GemstoneSlot BLOCKED = new GemstoneSlot(Type.EMPTY, GemstoneType.EMPTY, GemstoneType.Rarity.EMPTY);


    private GemstoneType.Rarity gemRarity;
    private final Type type;
    @NotNull
    private GemstoneType appliedGemstoneType;
    GemstoneSlot(Type gemType, GemstoneType appliedGemstoneType, GemstoneType.Rarity rarity) {
        this.type = gemType;
        this.appliedGemstoneType = Objects.requireNonNull(appliedGemstoneType);
        this.gemRarity = rarity;
    }

    public static void toNw(FriendlyByteBuf buf, GemstoneSlot slot) {
        buf.writeEnum(slot.type);
        buf.writeEnum(slot.appliedGemstoneType);
        buf.writeEnum(slot.gemRarity);
    }

    public static GemstoneSlot fromNw(FriendlyByteBuf buf) {
        return new GemstoneSlot(buf.readEnum(Type.class), buf.readEnum(GemstoneType.class), buf.readEnum(GemstoneType.Rarity.class));
    }

    private int getColorForRarity() {
        return this.gemRarity.colour;
    }

    public GemstoneType.Rarity getGemRarity() {
        return this.gemRarity;
    }

    public Type getType() {
        return this.type;
    }


    public GemstoneSlot empty() {
        return new GemstoneSlot(this.type, GemstoneType.EMPTY, GemstoneType.Rarity.EMPTY);
    }

    private boolean isValidGemstone(GemstoneType gemstoneType) {
        for (GemstoneType gemstoneType1 : this.type.applicable) {
            if (gemstoneType1 != null && gemstoneType1.equals(gemstoneType) || gemstoneType == GemstoneType.EMPTY) {
                return true;
            }
        }
        return false;
    }
    public boolean putGemstone(GemstoneType gemstoneType, GemstoneType.Rarity rarity) {
        if (this.isValidGemstone(gemstoneType)) {
            this.appliedGemstoneType = Objects.requireNonNull(gemstoneType);
            this.gemRarity = rarity;
            return true;
        }
        return false;
    }

    public ItemStack toItem() {
        if (this.gemRarity == GemstoneType.Rarity.EMPTY || this.appliedGemstoneType == GemstoneType.EMPTY || this == BLOCKED) {
            return ItemStack.EMPTY;
        }
        return IGemstoneItem.createData(this.gemRarity, this.appliedGemstoneType, ModItems.GEMSTONE);
    }

    public GemstoneType getAppliedGemstone() {
        return this.appliedGemstoneType;
    }

    @SuppressWarnings("ALL")
    public MutableComponent getDisplay() {
        boolean flag = this.appliedGemstoneType == GemstoneType.EMPTY;
        Style rarityColorStyle = Style.EMPTY.withColor(getColorForRarity());
        Style gemstoneColorStyle = Style.EMPTY.withColor((flag ? ChatFormatting.GRAY.getColor() : appliedGemstoneType.getColour()));
        return Component.literal("[").withStyle(rarityColorStyle).append(Component.literal(this.type.getUNICODE()).withStyle(gemstoneColorStyle)).append(Component.literal("]").withStyle(rarityColorStyle));
    }


    public static GemstoneSlot[] of(Type... types) {
        return new Builder(types).build();
    }

    @Override
    public String toString() {
        return "GemstoneSlot{Rarity: " + this.getGemRarity().getId() + ", applied GemstoneType: " + this.appliedGemstoneType.getId() + "}";
    }

    public List<? extends FormattedCharSequence> createPossibleList() {
        List<Component> components = new ArrayList<>();
        components.add(Component.translatable("gemstone_applicable_title").withStyle(ChatFormatting.GREEN));
        for (GemstoneType gemstoneType : this.type.applicable) {
            components.add(Component.translatable("gem_type." + gemstoneType.getSerializedName()).withStyle(style -> style.withColor(TextColor.fromRgb(gemstoneType.getColour()))));
        }
        return components.stream().map(Component::getVisualOrderText).toList();
    }

    public enum Type implements StringRepresentable {
        INTELLIGENCE("✎", "intel", GemstoneType.SAPPHIRE),
        STRENGTH("❁", "strength", GemstoneType.JASPER),
        HEALTH("❤", "health", GemstoneType.RUBY),
        ABILITY_DAMAGE("☄", "ability_damage", GemstoneType.ALMANDINE),
        EMPTY("", "empty", (GemstoneType) null),
        FISHING_SPEED("\uD83C\uDFA3", "fishing_speed", GemstoneType.AQUAMARINE),
        DRAW_SPEED("\uD83C\uDFF9", "draw_speed", GemstoneType.MOONSTONE),
        MOVE_SPEED("⇒", "move_speed", GemstoneType.CELESTINE),
        ENTITY_REACH("\uD83D\uDDE1", "entity_reach", GemstoneType.PERIDOT),
        ARMOR("⛉", "armor", GemstoneType.AMETHYST),
        OFFENCE("☠", "offence", GemstoneType.JASPER, GemstoneType.SAPPHIRE, GemstoneType.ALMANDINE, GemstoneType.MOONSTONE, GemstoneType.CELESTINE, GemstoneType.PERIDOT),
        DEFENCE("\ud83d\udee1", "defence", GemstoneType.RUBY, GemstoneType.AMETHYST, GemstoneType.CELESTINE),
        MAGIC("⚡", "magic", GemstoneType.SAPPHIRE, GemstoneType.ALMANDINE),
        COMBAT("⚔", "combat", GemstoneType.JASPER, GemstoneType.SAPPHIRE, GemstoneType.ALMANDINE, GemstoneType.MOONSTONE, GemstoneType.RUBY, GemstoneType.AMETHYST, GemstoneType.CELESTINE, GemstoneType.PERIDOT);

        public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

        public final String UNICODE;
        public final String id;
        public final GemstoneType[] applicable;

        Type(String unicode, String id, GemstoneType... applicable) {
            this.UNICODE = unicode;
            this.id = id;
            this.applicable = applicable;
        }


        public String getUNICODE() {
            return UNICODE;
        }

        @Override
        public @NotNull String getSerializedName() {
            return id;
        }
    }

    public static class Builder {
        private final Type[] types;

        public Builder(Type... types) {
            this.types = types;
        }

        public GemstoneSlot[] build() {
            if (types.length > GemstoneGrinderMenu.MAX_GEMSTONE_SLOTS) throw new IllegalStateException("detected Gemstone builder exceeding size limit (found: " + types.length + ", max: " + GemstoneGrinderMenu.MAX_GEMSTONE_SLOTS + ")");
            GemstoneSlot[] slots = new GemstoneSlot[types.length];
            for (int i = 0; i < slots.length; i++) {
                slots[i] = new GemstoneSlot(types[i], GemstoneType.EMPTY, GemstoneType.Rarity.EMPTY);
            }
            return slots;
        }
    }
}