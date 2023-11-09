package net.kapitencraft.mysticcraft.item.gemstone;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.event.ModEventFactory;
import net.kapitencraft.mysticcraft.helpers.TagHelper;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

public class GemstoneSlot {

    public static final Codec<GemstoneSlot> CODEC = RecordCodecBuilder.create(gemstoneSlotInstance ->
            gemstoneSlotInstance.group(
                    Type.CODEC.fieldOf("type").forGetter(GemstoneSlot::getType),
                    GemstoneType.CODEC.optionalFieldOf("gem", GemstoneType.EMPTY).forGetter(GemstoneSlot::getAppliedGemstone),
                    GemstoneType.Rarity.CODEC.optionalFieldOf("gem_rarity", GemstoneType.Rarity.EMPTY).forGetter(GemstoneSlot::getGemRarity)
            ).apply(gemstoneSlotInstance, GemstoneSlot::new)
    );
    public static final GemstoneSlot BLOCKED = new GemstoneSlot(Type.EMPTY, GemstoneType.EMPTY, GemstoneType.Rarity.EMPTY);


    private GemstoneType.Rarity gemRarity;
    private final Type type;
    @NotNull
    private GemstoneType appliedGemstoneType;
    GemstoneSlot(Type gem_type, GemstoneType appliedGemstoneType, GemstoneType.Rarity rarity) {
        this.appliedGemstoneType = Objects.requireNonNull(appliedGemstoneType);
        this.type = gem_type;
        this.gemRarity = rarity;
    }

    public Tag toNBT() {
        return TagHelper.getOrLog(CODEC.encodeStart(NbtOps.INSTANCE, this), new CompoundTag());
    }

    public static GemstoneSlot fromNBT(CompoundTag tag) {
        if (tag == null) {
            return null;
        }
        return TagHelper.getOrLog(CODEC.parse(NbtOps.INSTANCE, tag), BLOCKED);
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
            ModEventFactory.onGemstoneApplied(gemstoneType, this);
            return true;
        }
        return false;
    }

    public @Nullable GemstoneItem toItem() {
        if (this.gemRarity == GemstoneType.Rarity.EMPTY || this.appliedGemstoneType == GemstoneType.EMPTY || this == BLOCKED) {
            return null;
        }
        return ModItems.GEMSTONES.get(this.appliedGemstoneType).get(this.gemRarity).get();
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

    public enum Type implements StringRepresentable {
        INTELLIGENCE("\uF000", "intel", GemstoneType.SAPPHIRE),
        STRENGTH("\u2741", "strength", GemstoneType.JASPER),
        HEALTH("\u2764", "health", GemstoneType.RUBY),
        ABILITY_DAMAGE("\uF001", "ability_damage", GemstoneType.ALMANDINE),
        EMPTY("", "empty", (GemstoneType) null),
        FISHING_SPEED("\uF006", "fishing_speed", GemstoneType.AQUAMARINE),
        DRAW_SPEED("\uF004", "draw_speed", GemstoneType.MOONSTONE),
        MOVE_SPEED("\uF000", "move_speed", GemstoneType.CELESTINE),
        ARMOR("\uF000", "armor", GemstoneType.AMETHYST),
        OFFENCE("\uF005", "offence", GemstoneType.JASPER, GemstoneType.SAPPHIRE, GemstoneType.ALMANDINE, GemstoneType.MOONSTONE, GemstoneType.CELESTINE),
        DEFENCE("\uF002", "defence", GemstoneType.RUBY, GemstoneType.AMETHYST, GemstoneType.CELESTINE),
        MAGIC("\uF003", "magic", GemstoneType.SAPPHIRE, GemstoneType.ALMANDINE),
        COMBAT("\u2694", "combat", GemstoneType.JASPER, GemstoneType.SAPPHIRE, GemstoneType.ALMANDINE, GemstoneType.MOONSTONE, GemstoneType.RUBY, GemstoneType.AMETHYST, GemstoneType.CELESTINE);

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
            GemstoneSlot[] slots = new GemstoneSlot[types.length];
            for (int i = 0; i < slots.length; i++) {
                slots[i] = new GemstoneSlot(types[i], GemstoneType.EMPTY, GemstoneType.Rarity.EMPTY);
            }
            return slots;
        }
    }
}