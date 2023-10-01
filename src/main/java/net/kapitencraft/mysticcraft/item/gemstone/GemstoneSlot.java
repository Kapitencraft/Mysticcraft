package net.kapitencraft.mysticcraft.item.gemstone;

import net.kapitencraft.mysticcraft.event.ModEventFactory;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

import javax.annotation.Nullable;
import java.util.Objects;

public class GemstoneSlot {
    public static final GemstoneSlot BLOCKED = new GemstoneSlot(Type.EMPTY, GemstoneType.ALMANDINE, GemstoneType.Rarity.EMPTY);

    private static final String TYPE_ID = "type";
    private static final String GEMSTONE_RARITY_ID = "gem_rarity";
    private static final String GEMSTONE_TYPE_ID = "gem";

    private GemstoneType.Rarity gemRarity;
    private final Type type;
    @Nullable
    private GemstoneType appliedGemstoneType;
    GemstoneSlot(Type gem_type, @Nullable GemstoneType appliedGemstoneType, GemstoneType.Rarity rarity) {
        this.appliedGemstoneType = appliedGemstoneType;
        this.type = gem_type;
        this.gemRarity = rarity;
    }

    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString(GEMSTONE_RARITY_ID, this.gemRarity.getId());
        tag.putString(TYPE_ID, this.type.id);
        tag.putString(GEMSTONE_TYPE_ID, this.appliedGemstoneType != null ? this.appliedGemstoneType.getId() : "null");
        return tag;
    }

    public static GemstoneSlot fromNBT(CompoundTag tag) {
        if (tag == null) {
            return null;
        }
        return getByType(Type.getById(tag.getString(TYPE_ID))).setGemstone(GemstoneType.getById(tag.getString(GEMSTONE_TYPE_ID)), GemstoneType.Rarity.getById(tag.getString(GEMSTONE_RARITY_ID)));
    }

    private GemstoneSlot setGemstone(GemstoneType type, GemstoneType.Rarity rarity) {
        this.putGemstone(type, rarity);
        return this;
    }
    private static GemstoneSlot getByType(Type type) {
        return new GemstoneSlot(type, null, GemstoneType.Rarity.EMPTY);
    }
    private int getColorForRarity() {
        return this.gemRarity.COLOUR;
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

    public enum Type {
        COMBAT("\u2694", "combat", GemstoneType.JASPER, GemstoneType.SAPPHIRE, GemstoneType.RUBY, GemstoneType.ALMANDINE),
        OFFENCE("\uF005", "offence", GemstoneType.JASPER, GemstoneType.SAPPHIRE, GemstoneType.ALMANDINE),
        DEFENCE("\uF002", "defence", GemstoneType.RUBY),
        MAGIC("\uF003", "magic", GemstoneType.SAPPHIRE, GemstoneType.ALMANDINE),
        INTELLIGENCE("\uF000", "intel", GemstoneType.SAPPHIRE),
        STRENGTH("\u2741", "strength", GemstoneType.JASPER),
        HEALTH("\u2764", "health", GemstoneType.RUBY),
        ABILITY_DAMAGE("\uF001", "ability_damage", GemstoneType.ALMANDINE),
        EMPTY("", "empty", (GemstoneType) null),
        FISHING_SPEED("\uF006", "fishing_speed", GemstoneType.AQUAMARINE),
        DRAW_SPEED("\uF004", "draw_speed", GemstoneType.MOON_STONE);

        public final String UNICODE;
        public final String id;
        public final GemstoneType[] applicable;

        Type(String unicode, String id, GemstoneType... applicable) {
            this.UNICODE = unicode;
            this.id = id;
            this.applicable = applicable;
        }

        public static Type getById(String id) {
            for (int i = 0; i < values().length; i++) {
                if (Objects.equals(values()[i].id, id)) {
                    return values()[i];
                }
            }
            return Type.EMPTY;
        }


        public String getUNICODE() {
            return UNICODE;
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