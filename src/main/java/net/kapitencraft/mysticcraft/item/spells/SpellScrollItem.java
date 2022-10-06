package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.spell.Spell;
import net.minecraft.world.item.Item;

public class SpellScrollItem extends Item {

    private final Spell spell;

    public SpellScrollItem(Properties p_41383_, Spell assignedSpell) {
        super(p_41383_);
        this.spell = assignedSpell;
    }
}
