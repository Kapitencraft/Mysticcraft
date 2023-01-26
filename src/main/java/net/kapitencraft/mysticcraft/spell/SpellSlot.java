package net.kapitencraft.mysticcraft.spell;


public class SpellSlot {
    private Spell spell;
    private double mana_cost;

    public SpellSlot(Spell spell) {
        this.spell = spell;
        this.mana_cost = this.getManaCost();
    }

    public Spell getSpell() {
        return this.spell;
    }
    public double getManaCost() {
        return this.spell.MANA_COST;
    }

    private void setManaCost(double mana_cost) {
        this.mana_cost = mana_cost;
    }

}
