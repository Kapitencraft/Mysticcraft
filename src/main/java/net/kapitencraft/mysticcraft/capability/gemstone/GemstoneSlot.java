package net.kapitencraft.mysticcraft.capability.gemstone;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.kapitencraft.kap_lib.core.helpers.ExtraStreamCodecs;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public record GemstoneSlot(Type type, GemstoneType gemstoneType, GemstoneType.Rarity rarity) {

    public static final Codec<GemstoneSlot> CODEC = RecordCodecBuilder.create(gemstoneSlotInstance ->
            gemstoneSlotInstance.group(
                    Type.CODEC.fieldOf("type").forGetter(GemstoneSlot::type),
                    GemstoneType.CODEC.optionalFieldOf("gem", GemstoneType.EMPTY).forGetter(GemstoneSlot::gemstoneType),
                    GemstoneType.Rarity.CODEC.optionalFieldOf("rarity", GemstoneType.Rarity.EMPTY).forGetter(GemstoneSlot::rarity)
            ).apply(gemstoneSlotInstance, GemstoneSlot::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, GemstoneSlot> STREAM_CODEC = StreamCodec.composite(
            Type.STREAM_CODEC, GemstoneSlot::type,
            GemstoneType.STREAM_CODEC, GemstoneSlot::gemstoneType,
            GemstoneType.Rarity.STREAM_CODEC, GemstoneSlot::rarity,
            GemstoneSlot::new
    );

    public static final GemstoneSlot BLOCKED = new GemstoneSlot(Type.EMPTY, GemstoneType.EMPTY, GemstoneType.Rarity.EMPTY);


    private int getColorForRarity() {
        return this.rarity.color;
    }

    public GemstoneSlot setApplied(GemstoneType type, GemstoneType.Rarity rarity) {
        return new GemstoneSlot(this.type, type, rarity);
    }

    public GemstoneSlot empty() {
        return new GemstoneSlot(this.type, GemstoneType.EMPTY, GemstoneType.Rarity.EMPTY);
    }

    public boolean isValidGemstone(GemstoneType gemstoneType) {
        for (GemstoneType gemstoneType1 : this.type.applicable) {
            if (gemstoneType1 != null && gemstoneType1.equals(gemstoneType) || gemstoneType == GemstoneType.EMPTY) {
                return true;
            }
        }
        return false;
    }

    public ItemStack toItem() {
        if (this.rarity == GemstoneType.Rarity.EMPTY || this.gemstoneType == GemstoneType.EMPTY || this == BLOCKED) {
            return ItemStack.EMPTY;
        }
        return IGemstoneItem.createData(this.rarity, this.gemstoneType, ModItems.GEMSTONE);
    }

    @SuppressWarnings("ALL")
    public MutableComponent getDisplay() {
        boolean flag = this.gemstoneType == GemstoneType.EMPTY;
        Style rarityColorStyle = Style.EMPTY.withColor(getColorForRarity());
        Style gemstoneColorStyle = Style.EMPTY.withColor((flag ? ChatFormatting.GRAY.getColor() : gemstoneType.getColor()));
        return Component.literal("[").withStyle(rarityColorStyle).append(Component.literal(this.type.getUNICODE()).withStyle(gemstoneColorStyle)).append(Component.literal("]").withStyle(rarityColorStyle));
    }

    @Override
    public @NotNull String toString() {
        return "GemstoneSlot{Rarity: " + this.rarity.getId() + ", applied GemstoneType: " + this.gemstoneType.getId() + "}";
    }

    public List<? extends FormattedCharSequence> createPossibleList() {
        List<Component> components = new ArrayList<>();
        components.add(Component.translatable("gemstone_applicable_title").withStyle(ChatFormatting.GREEN));
        for (GemstoneType gemstoneType : this.type.applicable) {
            components.add(Component.translatable("gem_type." + gemstoneType.getSerializedName()).withStyle(style -> style.withColor(TextColor.fromRgb(gemstoneType.getColor()))));
        }
        return components.stream().map(Component::getVisualOrderText).toList();
    }

    public enum Type implements StringRepresentable {
        EMPTY("", "empty", (GemstoneType) null),
        ABILITY_DAMAGE("☄", "ability_damage", GemstoneType.ALMANDINE),
        STRENGTH("❁", "strength", GemstoneType.JASPER),
        HEALTH("❤", "health", GemstoneType.RUBY),
        ARMOR("⛉", "armor", GemstoneType.AMETHYST),
        INTELLIGENCE("✎", "intel", GemstoneType.SAPPHIRE),
        SWIM_SPEED("\ud83c\udf0a", "swim_speed", GemstoneType.AQUAMARINE),
        FISHING_SPEED("\uD83C\uDFA3", "fishing_speed", GemstoneType.TURQUOISE),
        DRAW_SPEED("\uD83C\uDFF9", "draw_speed", GemstoneType.MOONSTONE),
        MOVE_SPEED("⇒", "move_speed", GemstoneType.CELESTINE),
        ENTITY_REACH("\uD83D\uDDE1", "entity_reach", GemstoneType.PERIDOT),
        MOBILITY("⚐", "mobility", GemstoneType.CELESTINE, GemstoneType.AQUAMARINE),
        OFFENCE("☠", "offence", GemstoneType.JASPER, GemstoneType.SAPPHIRE, GemstoneType.ALMANDINE, GemstoneType.MOONSTONE, GemstoneType.CELESTINE, GemstoneType.PERIDOT),
        DEFENCE("\ud83d\udee1", "defence", GemstoneType.RUBY, GemstoneType.AMETHYST, GemstoneType.CELESTINE),
        MAGIC("⚡", "magic", GemstoneType.SAPPHIRE, GemstoneType.ALMANDINE),
        COMBAT("⚔", "combat", GemstoneType.JASPER, GemstoneType.SAPPHIRE, GemstoneType.ALMANDINE, GemstoneType.MOONSTONE, GemstoneType.RUBY, GemstoneType.AMETHYST, GemstoneType.CELESTINE, GemstoneType.PERIDOT),
        UNIVERSAL("☆", "universal", GemstoneType.WITHOUT_EMPTY); //exclude empty

        public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
        public static final StreamCodec<ByteBuf, Type> STREAM_CODEC = ExtraStreamCodecs.enumCodec(Type.values());

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
}