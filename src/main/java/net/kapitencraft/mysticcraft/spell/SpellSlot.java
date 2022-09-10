package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class SpellSlot {
    private Spell spell;
    private double mana_cost;

    public SpellSlot(Spell spell, SpellModdifier moddifier) {
        this.spell = spell;
        this.calculateManaCost();
    }

    public Spell getSpell() {
        return this.spell;
    }
    public double getManaCost() {
        return this.mana_cost;
    }

    private void setManaCost(double mana_cost) {
        this.mana_cost = mana_cost;
    }

    public double calculateManaCost() {
        this.mana_cost = this.spell.MANA_COST;
        return this.spell.MANA_COST;
    }
}
