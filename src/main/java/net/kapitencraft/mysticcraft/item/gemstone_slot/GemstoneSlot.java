package net.kapitencraft.mysticcraft.item.gemstone_slot;

import net.kapitencraft.mysticcraft.item.gemstone_slot.gemstones.AlmandineGemstone;
import net.kapitencraft.mysticcraft.item.gemstone_slot.gemstones.JasperGemstone;
import net.kapitencraft.mysticcraft.item.gemstone_slot.gemstones.RubyGemstone;
import net.kapitencraft.mysticcraft.item.gemstone_slot.gemstones.SapphireGemstone;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;

import javax.annotation.Nullable;

public class GemstoneSlot {

    public static final GemstoneSlot COMBAT = new GemstoneSlot(Type.COMBAT, new Gemstone[]{new JasperGemstone(), new SapphireGemstone(), new RubyGemstone(), new AlmandineGemstone()}, null);
    public static final GemstoneSlot OFFENSIVE = new GemstoneSlot(Type.OFFENCE, new Gemstone[]{new JasperGemstone(), new SapphireGemstone(), new AlmandineGemstone()}, null);
    public static final GemstoneSlot DEFENCE = new GemstoneSlot(Type.DEFENCE, new Gemstone[]{new RubyGemstone()}, null);
    public static final GemstoneSlot MAGIC = new GemstoneSlot(Type.MAGIC, new Gemstone[]{new SapphireGemstone(), new AlmandineGemstone()}, null);
    public static final GemstoneSlot INTELLIGENCE = new GemstoneSlot(Type.INTELLIGENCE, new Gemstone[]{new SapphireGemstone()}, null);
    public static final GemstoneSlot STRENGHT = new GemstoneSlot(Type.STRENGTH, new Gemstone[]{new JasperGemstone()}, null);
    public static final GemstoneSlot HEALTH = new GemstoneSlot(Type.HEALTH, new Gemstone[]{new RubyGemstone()}, null);
    public static final GemstoneSlot ABILITY_DAMAGE = new GemstoneSlot(Type.ABILITY_DAMAGE, new Gemstone[]{new AlmandineGemstone()}, null);

    private Gemstone.Rarity gem_rarity;
    private final Type TYPE;
    private final Gemstone[] applicableGemstones;
    private Gemstone appliedGemstone;
    public GemstoneSlot(Type gem_type, Gemstone[] applicableGemstones, @Nullable Gemstone appliedGemstone) {
        this.appliedGemstone = appliedGemstone;
        this.applicableGemstones = applicableGemstones;
        this.TYPE = gem_type;
        if (appliedGemstone != null) {
            this.gem_rarity = appliedGemstone.getRarity();
        } else {
            this.gem_rarity = Gemstone.Rarity.EMPTY;
        }
    }

    private String getColorForRarity() {
        return this.gem_rarity.COLOUR;
    }

    public Gemstone.Rarity getGemRarity() {
        return this.gem_rarity;
    }

    public Type getType() {
        return this.TYPE;
    }


    private boolean isValidGemstone(Gemstone gemstone) {
        for (Gemstone gemstone1 : applicableGemstones) {
            if (gemstone1.getClass().equals(gemstone.getClass())) {
                return true;
            }
        }
        return false;
    }
    public boolean putGemstone(Gemstone gemstone) {
        if (this.isValidGemstone(gemstone)) {
            this.appliedGemstone = gemstone;
            this.gem_rarity = gemstone.getRarity();
            return true;
        } else {
            return false;
        }
    }

    public @Nullable Gemstone getAppliedGemstone() {
        return this.appliedGemstone;
    }
    public void setGemRarity(Gemstone.Rarity rarity) {
        this.gem_rarity = rarity;
    }

    public String getDisplay() {
        boolean flag = this.appliedGemstone == null;
        return getColorForRarity() + "[" + FormattingCodes.RESET + (flag ? FormattingCodes.GRAY.UNICODE : appliedGemstone.getColour()) + this.TYPE.getUNICODE() + FormattingCodes.RESET + getColorForRarity() + "]";
    }



    public static class Type {
        public static final Type COMBAT = new Type("");
        public static final Type OFFENCE = new Type("");
        public static final Type DEFENCE = new Type("\uF003");
        public static final Type MAGIC = new Type("\uF004");
        public static final Type INTELLIGENCE = new Type("\uF000");
        public static final Type STRENGTH = new Type("\uF002");
        public static final Type HEALTH = new Type("");
        public static final Type ABILITY_DAMAGE = new Type("\uF001");

        public final String UNICODE;

        public Type(String unicode) {
            this.UNICODE = unicode;
        }

        public String getUNICODE() {
            return UNICODE;
        }
    }
}
