package net.kapitencraft.mysticcraft.misc.damage_source;

import net.kapitencraft.mysticcraft.spell.Spell;

public interface ISpellSource {
    float getScaling();
    Spell getSpell();
}
