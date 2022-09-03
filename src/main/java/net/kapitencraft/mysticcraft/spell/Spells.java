package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.mysticcraft.spell.spells.CrystalWarpSpell;

public class Spells {

    public static final Spell CRYSTALWARP = new CrystalWarpSpell();

    public static final SpellType RELEASE = new SpellType("release");
    public static final SpellType CYCLE = new SpellType("cycle");
    static class SpellType {
        public final String name;

        public SpellType(String name) {
            this.name = name;
        }
    }
}
