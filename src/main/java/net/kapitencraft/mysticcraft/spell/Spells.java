package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.mysticcraft.spell.spells.CrystalWarpSpell;
import net.kapitencraft.mysticcraft.spell.spells.ExplosiveSightSpell;
import net.kapitencraft.mysticcraft.spell.spells.InstantTransmissionSpell;

public class Spells {

    public static final Spell CRYSTAL_WARP = new CrystalWarpSpell();
    public static final Spell INSTANT_TRANSMISSION = new InstantTransmissionSpell();
    public static final Spell EXPLOSIVE_SIGHT = new ExplosiveSightSpell();

    public static final SpellType RELEASE = new SpellType("release");
    public static final SpellType CYCLE = new SpellType("cycle");
    public static class SpellType {
        public final String name;

        public SpellType(String name) {
            this.name = name;
        }
    }
}
