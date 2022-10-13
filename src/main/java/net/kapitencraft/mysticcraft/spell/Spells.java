package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.mysticcraft.spell.spells.CrystalWarpSpell;
import net.kapitencraft.mysticcraft.spell.spells.EmptySpell;
import net.kapitencraft.mysticcraft.spell.spells.ExplosiveSightSpell;
import net.kapitencraft.mysticcraft.spell.spells.InstantTransmissionSpell;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class Spells {

    public static final Spell CRYSTAL_WARP = new CrystalWarpSpell();
    public static final Spell INSTANT_TRANSMISSION = new InstantTransmissionSpell();
    public static final Spell EXPLOSIVE_SIGHT = new ExplosiveSightSpell();
    public static final Spell EMPTY_SPELL = new EmptySpell();

    public static final SpellType RELEASE = new SpellType("release");
    public static final SpellType CYCLE = new SpellType("cycle");

    public record SpellType(String name) {
    }
}
