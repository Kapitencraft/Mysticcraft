package net.kapitencraft.mysticcraft.item.gemstone;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.event.ModEventFactory;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;
import java.util.Objects;

public class GemstoneSlot {

    public static final GemstoneSlot COMBAT = new GemstoneSlot(Type.COMBAT, null, GemstoneType.Rarity.EMPTY);
    public static final GemstoneSlot OFFENSIVE = new GemstoneSlot(Type.OFFENCE, null, GemstoneType.Rarity.EMPTY);
    public static final GemstoneSlot DEFENCE = new GemstoneSlot(Type.DEFENCE, null, GemstoneType.Rarity.EMPTY);
    public static final GemstoneSlot MAGIC = new GemstoneSlot(Type.MAGIC, null, GemstoneType.Rarity.EMPTY);
    public static final GemstoneSlot INTELLIGENCE = new GemstoneSlot(Type.INTELLIGENCE, null, GemstoneType.Rarity.EMPTY);
    public static final GemstoneSlot STRENGHT = new GemstoneSlot(Type.STRENGTH, null, GemstoneType.Rarity.EMPTY);
    public static final GemstoneSlot HEALTH = new GemstoneSlot(Type.HEALTH, null, GemstoneType.Rarity.EMPTY);
    public static final GemstoneSlot ABILITY_DAMAGE = new GemstoneSlot(Type.ABILITY_DAMAGE, null, GemstoneType.Rarity.EMPTY);

    private GemstoneType.Rarity gemRarity;
    private final Type TYPE;
    private GemstoneType appliedGemstoneType;
    public GemstoneSlot(Type gem_type, @Nullable GemstoneType appliedGemstoneType, GemstoneType.Rarity rarity) {
        this.appliedGemstoneType = appliedGemstoneType;
        this.TYPE = gem_type;
        this.gemRarity = rarity;
    }

    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("gem_rarity", this.gemRarity.getId());
        tag.putString("type", this.TYPE.id);
        if (this.appliedGemstoneType != null) {
            tag.putString("gem", this.appliedGemstoneType.getId());
        } else {
            tag.putString("gem", "null");
        }
        return tag;
    }

    public static GemstoneSlot fromNBT(CompoundTag tag) {
        return new GemstoneSlot(Type.getById(tag.getString("type")), GemstoneType.getById(tag.getString("gem")), GemstoneType.Rarity.getById(tag.getString("gem_rarity")));
    }

    private String getColorForRarity() {
        return this.gemRarity.COLOUR;
    }

    public GemstoneType.Rarity getGemRarity() {
        return this.gemRarity;
    }

    public Type getType() {
        return this.TYPE;
    }


    private boolean isValidGemstone(GemstoneType gemstoneType) {
        for (GemstoneType gemstoneType1 : this.TYPE.applicable) {
            if (gemstoneType1.equals(gemstoneType)) {
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
            MysticcraftMod.sendInfo("ea");
            return true;
        }
        return false;
    }

    public @Nullable GemstoneType getAppliedGemstone() {
        return this.appliedGemstoneType;
    }
    public void setGemRarity(GemstoneType.Rarity rarity) {
        this.gemRarity = rarity;
    }

    public String getDisplay() {
        boolean flag = this.appliedGemstoneType == null;
        return getColorForRarity() + "[" + FormattingCodes.RESET + (flag ? FormattingCodes.GRAY : appliedGemstoneType.getColour()) + this.TYPE.getUNICODE() + FormattingCodes.RESET + getColorForRarity() + "]";
    }



    public enum Type {
        COMBAT("", "combat", new GemstoneType[]{GemstoneType.JASPER, GemstoneType.SAPPHIRE, GemstoneType.RUBY, GemstoneType.ALMANDINE}),
        OFFENCE("", "offence", new GemstoneType[]{GemstoneType.JASPER, GemstoneType.SAPPHIRE, GemstoneType.ALMANDINE}),
        DEFENCE("\uF003", "defence", new GemstoneType[]{GemstoneType.RUBY}),
        MAGIC("\uF004", "magic", new GemstoneType[]{GemstoneType.SAPPHIRE, GemstoneType.ALMANDINE}),
        INTELLIGENCE("\uF000", "intel", new GemstoneType[]{GemstoneType.SAPPHIRE}),
        STRENGTH("\uF002", "strength", new GemstoneType[]{GemstoneType.JASPER}),
        HEALTH("", "health", new GemstoneType[]{GemstoneType.RUBY}),
        ABILITY_DAMAGE("\uF001", "ability_damage", new GemstoneType[]{GemstoneType.ALMANDINE}),
        EMPTY("", "empty", null);

        public final String UNICODE;
        public final String id;
        public final GemstoneType[] applicable;

        Type(String unicode, String id, GemstoneType[] applicable) {
            this.UNICODE = unicode;
            this.id = id;
            this.applicable = applicable;
        }

        public GemstoneType[] getApplicable() {
            return applicable;
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
}
