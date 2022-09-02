package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class SpellSlot {
    private Spell spell;
    private SpellModdifier moddifier;
    private double mana_cost;

    public SpellSlot(Spell spell, SpellModdifier moddifier) {
        this.spell = spell;
        this.moddifier = moddifier;
        this.calculateManaCost();
    }

    public Spell getSpell() {
        return this.spell;
    }

    public SpellModdifier getModdifier() {
        return this.moddifier;
    }

    public double getManaCost() {
        return this.mana_cost;
    }

    private void setManaCost(double mana_cost) {
        this.mana_cost = mana_cost;
    }

    public double calculateManaCost() {
        double Mana_Cost = (double) this.spell.MANA_COST;
        double amount = this.moddifier.getModifier().get(ModAttributes.MANA_COST.get()).getAmount();
        if (this.moddifier.getModifier().get(ModAttributes.MANA_COST.get()).getOperation() == AttributeModifier.Operation.ADDITION) {
            Mana_Cost += amount;
        } else if (this.moddifier.getModifier().get(ModAttributes.MANA_COST.get()).getOperation() == AttributeModifier.Operation.MULTIPLY_TOTAL) {
            Mana_Cost *= amount;
        }
        this.mana_cost = Mana_Cost;
        return Mana_Cost;
    }
}
