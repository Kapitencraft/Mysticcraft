package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.mysticcraft.spell.spells.*;

public class Spells {

    public static final SpellType RELEASE = new SpellType("release");
    public static final SpellType CYCLE = new SpellType("cycle");
    public static final Spell CRYSTAL_WARP = new CrystalWarpSpell();
    public static final Spell INSTANT_TRANSMISSION = new InstantTransmissionSpell();
    public static final Spell EXPLOSIVE_SIGHT = new ExplosiveSightSpell();
    public static final Spell EMPTY_SPELL = new EmptySpell();
    public static final Spell FIRE_BOLD_1 = new FireBoldSpell(1, false);
    public static final Spell FIRE_BOLD_2 = new FireBoldSpell(1.4, false);
    public static final Spell FIRE_BOLD_3 = new FireBoldSpell(2.8, true);
    public static final Spell FIRE_BOLD_4 = new FireBoldSpell(5.2, true);
    public static final Spell FIRE_LANCE = new FireLanceSpell();

    public record SpellType(String name) {
    }
}
