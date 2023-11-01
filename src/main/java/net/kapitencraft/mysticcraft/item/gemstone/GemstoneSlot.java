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

import javax.annotation.Nullable;

public class GemstoneSlot {
    private static final String TYPE_ID = "type";
    private static final String GEMSTONE_RARITY_ID = "gem_rarity";
    private static final String GEMSTONE_TYPE_ID = "gem";

    public static final Codec<GemstoneSlot> CODEC = RecordCodecBuilder.create(gemstoneSlotInstance ->
            gemstoneSlotInstance.group(
                    Type.CODEC.fieldOf(TYPE_ID).forGetter(GemstoneSlot::getType),
                    GemstoneType.CODEC.fieldOf(GEMSTONE_TYPE_ID).forGetter(GemstoneSlot::getAppliedGemstone),
                    GemstoneType.Rarity.CODEC.fieldOf(GEMSTONE_RARITY_ID).forGetter(GemstoneSlot::getGemRarity)
            ).apply(gemstoneSlotInstance, GemstoneSlot::new)
    );
    public static final GemstoneSlot BLOCKED = new GemstoneSlot(Type.EMPTY, GemstoneType.ALMANDINE, GemstoneType.Rarity.EMPTY);


    private GemstoneType.Rarity gemRarity;
    private final Type type;
    @Nullable
    private GemstoneType appliedGemstoneType;
    GemstoneSlot(Type gem_type, @Nullable GemstoneType appliedGemstoneType, GemstoneType.Rarity rarity) {
        this.appliedGemstoneType = appliedGemstoneType;
        this.type = gem_type;
        this.gemRarity = rarity;
    }

    public Tag toNBT() {
        return TagHelper.getOrDefault(CODEC.encodeStart(NbtOps.INSTANCE, this), new CompoundTag());
    }

    public static GemstoneSlot fromNBT(CompoundTag tag) {
        if (tag == null) {
            return null;
        }
        return TagHelper.getOrDefault(CODEC.parse(NbtOps.INSTANCE, tag), BLOCKED);
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


    private boolean isValidGemstone(GemstoneType gemstoneType) {
        for (GemstoneType gemstoneType1 : this.type.applicable) {
            if (gemstoneType1 != null && gemstoneType1.equals(gemstoneType) || gemstoneType == null) {
                return true;
            }
        }
        return false;
    }
    public boolean putGemstone(GemstoneType gemstoneType, GemstoneType.Rarity rarity) {
        if (this.isValidGemstone(gemstoneType)) {
            this.appliedGemstoneType = gemstoneType;
            this.gemRarity = rarity;
            ModEventFactory.onGemstoneApplied(gemstoneType, this);
            return true;
        }
        return false;
    }

    public @Nullable GemstoneItem toItem() {
        if (this.appliedGemstoneType == null || this.gemRarity == null) {
            return null;
        }
        return ModItems.GEMSTONES.get(this.appliedGemstoneType).get(this.gemRarity).get();
    }

    public @Nullable GemstoneType getAppliedGemstone() {
        return this.appliedGemstoneType;
    }

    public MutableComponent getDisplay() {
        boolean flag = this.appliedGemstoneType == null;
        Style rarityColorStyle = Style.EMPTY.withColor(getColorForRarity());
        Style gemstoneColorStyle = Style.EMPTY.withColor((flag ? ChatFormatting.GRAY.getColor() : appliedGemstoneType.getColour()));
        return Component.literal("[").withStyle(rarityColorStyle).append(Component.literal(this.type.getUNICODE()).withStyle(gemstoneColorStyle)).append(Component.literal("]").withStyle(rarityColorStyle));
    }


    @Override
    public String toString() {
        return "GemstoneSlot{Rarity: " + this.getGemRarity().getId() + ", applied GemstoneType: " + (this.appliedGemstoneType == null ? "null" : this.appliedGemstoneType.getId()) + "}";
    }

    public enum Type implements StringRepresentable {
        INTELLIGENCE("\uF000", "intel", GemstoneType.SAPPHIRE),
        STRENGTH("\u2741", "strength", GemstoneType.JASPER),
        HEALTH("\u2764", "health", GemstoneType.RUBY),
        ABILITY_DAMAGE("\uF001", "ability_damage", GemstoneType.ALMANDINE),
        EMPTY("", "empty", (GemstoneType) null),
        FISHING_SPEED("\uF006", "fishing_speed", GemstoneType.AQUAMARINE),
        DRAW_SPEED("\uF004", "draw_speed", GemstoneType.MOON_STONE),
        ARMOR("\uF000", "armor", GemstoneType.AMETHYST),
        OFFENCE("\uF005", "offence", GemstoneType.JASPER, GemstoneType.SAPPHIRE, GemstoneType.ALMANDINE, GemstoneType.MOON_STONE),
        DEFENCE("\uF002", "defence", GemstoneType.RUBY, GemstoneType.AMETHYST),
        MAGIC("\uF003", "magic", GemstoneType.SAPPHIRE, GemstoneType.ALMANDINE),
        COMBAT("\u2694", "combat", GemstoneType.JASPER, GemstoneType.SAPPHIRE, GemstoneType.ALMANDINE, GemstoneType.MOON_STONE, GemstoneType.RUBY, GemstoneType.AMETHYST);

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
        public String getSerializedName() {
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
                slots[i] = new GemstoneSlot(types[i], null, GemstoneType.Rarity.EMPTY);
            }
            return slots;
        }
    }
}